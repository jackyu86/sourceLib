package com.caej.site.order.ajax;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import com.caej.api.pay.QueryPayRequest;
import com.caej.api.pay.QueryPayResponse;
import com.caej.cart.api.CartWebService;
import com.caej.cart.api.cart.BatchGetCartItemRequest;
import com.caej.cart.api.cart.BatchGetCartItemResponse;
import com.caej.cart.api.cart.CartResponse;
import com.caej.cart.api.cart.DeleteCartRequest;
import com.caej.order.OrderWebService;
import com.caej.order.PaymentWebService;
import com.caej.order.order.CheckoutRequest;
import com.caej.order.order.CheckoutResponse;
import com.caej.order.order.CustomerView;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.order.UpdateOrderPaymentRequest;
import com.caej.order.payment.ChargePaymentRequest;
import com.caej.order.payment.OfflinePaymentRequest;
import com.caej.order.payment.PaymentMethodView;
import com.caej.order.payment.PaymentStatusView;
import com.caej.order.payment.PaymentView;
import com.caej.order.payment.UpdatePaymentRequest;
import com.caej.product.api.ProductConvertWebService;
import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.orderview.OrderViewBuildRequest;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.ProductStatusType;
import com.caej.site.checkout.CheckoutSessionRequest;
import com.caej.site.customer.service.CustomerService;
import com.caej.site.order.PayService;
import com.caej.site.user.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.AuthenticationRequest;
import app.dealer.api.dealer.DealerUserResponse;
import io.sited.StandardException;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.web.User;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class OrderAJAXController {
    @Inject
    OrderWebService orderWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    PayService payService;
    @Inject
    PaymentWebService paymentWebService;
    @Inject
    CartWebService cartWebService;
    @Inject
    ProductConvertWebService productConvertWebService;
    @Inject
    ProductWebService productWebService;
    @Inject
    CustomerService customerService;
    @Inject
    ProductFormWebService productFormWebService;
    @Inject
    UserService userService;

    @Path("/ajax/order")
    @POST
    @SuppressWarnings("unchecked")
    public PaymentResponse checkout(Request request) {
        String id = request.queryParam("id").orElseThrow(() -> new NotFoundException("missing checkoutId"));
        Optional<String> checkoutSessionRequest = request.session().get(checkoutId(id));
        if (!checkoutSessionRequest.isPresent()) {
            throw new NotFoundException("missing checkout request");
        }

        CheckoutSessionRequest sessionRequest = JSON.fromJSON(checkoutSessionRequest.get(), CheckoutSessionRequest.class);
        ProductResponse product = productWebService.get(sessionRequest.productId);
        if (product.status != ProductStatusType.ACTIVE) {
            throw new BadRequestException(Lists.newArrayList(BadRequestException.InvalidField.of("product", null, "产品已过期")));
        }

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        OrderViewBuildRequest orderViewBuildRequest = new OrderViewBuildRequest();
        orderViewBuildRequest.productId = sessionRequest.productId;
        orderViewBuildRequest.value = sessionRequest.value;
        OrderView orderView = productConvertWebService.buildOrderView(orderViewBuildRequest);
        checkoutRequest.orders = Lists.newArrayList(orderView);

        User user = request.require(User.class, null);
        if (user == null) {
            user = userService.getCommonAnonym();
        } else {
            customerService.autoFillUserInfo(user, (Map<String, Object>) sessionRequest.value.get("policy-holder"));
        }
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(user.id);
        checkoutRequest.customer = customerView(user, dealerUser.isPresent() ? dealerUser.get() : null);

        checkoutRequest.customerIPAddress = request.client().ip();

        CheckoutResponse checkoutResponse = orderWebService.checkout(checkoutRequest);
        if (checkoutResponse.error != null) {
            throw new StandardException(checkoutResponse.error.errorMessage);
        }
        request.session().remove(checkoutId(sessionRequest.checkoutId));

        PendingPaymentRequest pendingPaymentRequest = new PendingPaymentRequest();
        pendingPaymentRequest.paymentId = sessionRequest.checkoutId;
        pendingPaymentRequest.title = orderView.productDisplayName;
        pendingPaymentRequest.description = orderView.productDisplayName;
        pendingPaymentRequest.total = orderView.total;
        pendingPaymentRequest.orders = checkoutRequest.orders;
        request.session().set(paymentId(pendingPaymentRequest.paymentId), JSON.toJSON(pendingPaymentRequest));
        PaymentResponse response = new PaymentResponse();
        response.paymentId = sessionRequest.checkoutId;
        return response;
    }

    private String checkoutId(String id) {
        return "checkout-request:" + id;
    }

    private String paymentId(String id) {
        return "payment-request:" + id;
    }

    private CustomerView customerView(User user, DealerUserResponse dealerUser) {
        CustomerView customerView = new CustomerView();
        customerView.id = user.id;
        customerView.customerName = user.fullName;
        customerView.phone = user.phone;
        if (dealerUser != null) {
            customerView.dealerId = dealerUser.dealerId;
        }
        return customerView;
    }

    @Path("/ajax/order/continue")
    @POST
    public PaymentResponse continueOrder(Request request) {
        User user = request.require(User.class, null);
        if (user == null) throw new UnauthenticatedException("user not login");
        OrderContinueRequest orderContinueRequest = request.body(OrderContinueRequest.class);
        StringBuilder title = new StringBuilder();
        StringBuilder description = new StringBuilder();
        Double total = 0D;
        PendingPaymentRequest pendingPaymentRequest = new PendingPaymentRequest();
        pendingPaymentRequest.paymentId = UUID.randomUUID().toString();
        pendingPaymentRequest.orders = Lists.newArrayList();
        for (String id : orderContinueRequest.ids) {
            OrderView orderView = orderWebService.get(id);
            if (!orderView.customerId.equals(user.id)) throw new UnauthenticatedException("operation forbidden");
            title.append(orderView.productDisplayName).append(',');
            description.append(orderView.productDisplayName).append(',');
            total += orderView.total;
            pendingPaymentRequest.orders.add(orderView);
        }
        title.deleteCharAt(title.length() - 1);
        description.deleteCharAt(description.length() - 1);
        pendingPaymentRequest.total = total;
        pendingPaymentRequest.title = title.toString();
        pendingPaymentRequest.description = description.toString();
        request.session().set(paymentId(pendingPaymentRequest.paymentId), JSON.toJSON(pendingPaymentRequest));
        PaymentResponse response = new PaymentResponse();
        response.paymentId = pendingPaymentRequest.paymentId;
        return response;
    }

    @Path("/ajax/order/from-cart")
    @POST
    public PaymentResponse checkoutFromCart(Request request) {
        User user = request.require(User.class);
        OrderCheckoutRequest orderCheckoutRequest = request.body(OrderCheckoutRequest.class);
        BatchGetCartItemRequest batchGetCartItemRequest = new BatchGetCartItemRequest();
        batchGetCartItemRequest.carItemIds = orderCheckoutRequest.cartItemIds;
        BatchGetCartItemResponse cartItems = cartWebService.batchGet(batchGetCartItemRequest);

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.orders = Lists.newArrayList();

        StringBuilder title = new StringBuilder();
        StringBuilder description = new StringBuilder();
        Double total = 0D;
        for (CartResponse.CartItemView cartItemView : cartItems.cartItemViews) {
            try {
                productFormWebService.validate(cartItemView.productId, cartItemView.form);
            } catch (BadRequestException e) {
                throw new BadRequestException("name", cartItemView.productName, null, "表单错误");
            }

            title.append(cartItemView.productName).append(',');
            description.append(cartItemView.productName).append(',');
            OrderViewBuildRequest orderViewBuildRequest = new OrderViewBuildRequest();
            orderViewBuildRequest.productId = cartItemView.productId;
            orderViewBuildRequest.value = cartItemView.form;
            OrderView orderView = productConvertWebService.buildOrderView(orderViewBuildRequest);
            checkoutRequest.orders.add(orderView);
            total += orderView.total;
        }
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(user.id);
        checkoutRequest.customer = customerView(user, dealerUser.isPresent() ? dealerUser.get() : null);
        checkoutRequest.customerIPAddress = request.client().ip();
        title.deleteCharAt(title.length() - 1);
        description.deleteCharAt(description.length() - 1);

        CheckoutResponse checkoutResponse = orderWebService.checkout(checkoutRequest);
        if (checkoutResponse.error != null) {
            throw new StandardException(checkoutResponse.error.errorMessage);
        }
        PendingPaymentRequest pendingPaymentRequest = new PendingPaymentRequest();
        pendingPaymentRequest.paymentId = UUID.randomUUID().toString();
        pendingPaymentRequest.orders = checkoutRequest.orders;
        pendingPaymentRequest.title = title.toString();
        pendingPaymentRequest.description = description.toString();
        pendingPaymentRequest.total = total;
        DeleteCartRequest deleteCartRequest = new DeleteCartRequest();
        deleteCartRequest.itemIds = orderCheckoutRequest.cartItemIds;
        deleteCartRequest.requestedBy = user.id;
        cartWebService.delete(user.id, deleteCartRequest);
        request.session().set(paymentId(pendingPaymentRequest.paymentId), JSON.toJSON(pendingPaymentRequest));
        PaymentResponse response = new PaymentResponse();
        response.paymentId = pendingPaymentRequest.paymentId;
        return response;
    }

    @Path("/ajax/order/toPay")
    @POST
    public OrderToPayResponse toPay(Request request) {
        String paymentId = request.queryParam("id").orElseThrow(() -> new NotFoundException("missing payment id"));
        User user = request.require(User.class, null);
        if (user == null) user = userService.getCommonAnonym();
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(user.id);
        CustomerView customerView = customerView(user, dealerUser.isPresent() ? dealerUser.get() : null);
        OrderToPayRequest orderToPayRequest = request.body(OrderToPayRequest.class);
        Optional<String> prepareRequestOptional = request.session().get(paymentId(paymentId));
        if (!prepareRequestOptional.isPresent()) {
            throw new NotFoundException("missing payment");
        }
        PendingPaymentRequest pendingPaymentRequest = JSON.fromJSON(prepareRequestOptional.get(), PendingPaymentRequest.class);
        OrderView checkOrder = orderWebService.get(pendingPaymentRequest.orders.get(0).id);
        if (checkOrder.paymentId != null) {
            PaymentView check = paymentWebService.get(checkOrder.paymentId);
            if (!PaymentStatusView.PENDING_PAYMENT.equals(check.status) || PaymentMethodView.OFFLINE.equals(check.method)) {
                OrderToPayResponse orderToPayResponse = new OrderToPayResponse();
                orderToPayResponse.status = -1;
                return orderToPayResponse;
            }
        }
        ChargePaymentRequest chargePaymentRequest = new ChargePaymentRequest();
        chargePaymentRequest.customerView = customerView;
        PaymentView payment = new PaymentView();
        payment.id = UUID.randomUUID().toString();
        payment.status = PaymentStatusView.PENDING_PAYMENT;
        payment.paymentDate = LocalDateTime.now();
        payment.customerId = customerView.id;
        payment.title = pendingPaymentRequest.title;
        payment.description = pendingPaymentRequest.description;
        payment.total = pendingPaymentRequest.total;
        payment.latestEffectTime = payService.latestEffectTime(pendingPaymentRequest);
        chargePaymentRequest.paymentView = payment;
        PaymentView paymentView = paymentWebService.charge(chargePaymentRequest);
        OrderToPayResponse orderToPayResponse = payService.toPay(orderToPayRequest, user, paymentView);
        if (0 == orderToPayResponse.status) {
            paymentView.method = orderToPayRequest.method;
            updateOrderPayment(pendingPaymentRequest, paymentView);
            orderToPayResponse.paymentId = paymentView.id;
        }
        return orderToPayResponse;
    }

    private void updateOrderPayment(PendingPaymentRequest pendingPaymentRequest, PaymentView paymentView) {
        List<String> ids = Lists.newArrayList();
        pendingPaymentRequest.orders.forEach(order -> {
            ids.add(order.id);
        });
        UpdateOrderPaymentRequest updateOrderPaymentRequest = new UpdateOrderPaymentRequest();
        updateOrderPaymentRequest.ids = ids;
        updateOrderPaymentRequest.status = OrderStatusView.PAYMENT_PENDING;
        updateOrderPaymentRequest.paymentId = paymentView.id;
        updateOrderPaymentRequest.paymentMethod = paymentView.method;
        updateOrderPaymentRequest.outTradeNo = paymentView.outTradeNo;
        orderWebService.updateOrderPayment(updateOrderPaymentRequest);
    }

    @Path("/ajax/order/offline")
    @POST
    public OrderToPayResponse offline(Request request) {
        String paymentId = request.queryParam("id").orElseThrow(() -> new NotFoundException("missing payment id"));
        User user = request.require(User.class, null);
        DealerUserResponse dealerUser = null;
        if (user != null) {
            dealerUser = dealerUserWebService.get(user.id).orElse(null);
        } else {
            user = userService.getCommonAnonym();
        }
        CustomerView customerView = customerView(user, dealerUser);
        OfflineRequest offlineRequest = request.body(OfflineRequest.class);
        Optional<String> prepareRequestOptional = request.session().get(paymentId(paymentId));
        if (!prepareRequestOptional.isPresent()) {
            throw new NotFoundException("missing payment");
        }
        PendingPaymentRequest pendingPaymentRequest = JSON.fromJSON(prepareRequestOptional.get(), PendingPaymentRequest.class);
        OrderView checkOrder = orderWebService.get(pendingPaymentRequest.orders.get(0).id);
        if (checkOrder.paymentId != null) {
            PaymentView check = paymentWebService.get(checkOrder.paymentId);
            if (!PaymentStatusView.PENDING_PAYMENT.equals(check.status) || PaymentMethodView.OFFLINE.equals(check.method)) {
                OrderToPayResponse orderToPayResponse = new OrderToPayResponse();
                orderToPayResponse.status = -1;
                return orderToPayResponse;
            }
        }
        PaymentView payment = new PaymentView();
        payment.id = UUID.randomUUID().toString();
        payment.status = PaymentStatusView.PENDING_PAYMENT;
        payment.paymentDate = LocalDateTime.now();
        payment.customerId = customerView.id;
        payment.title = pendingPaymentRequest.title;
        payment.description = pendingPaymentRequest.description;
        payment.total = pendingPaymentRequest.total;
        payment.method = PaymentMethodView.OFFLINE;
        LocalDateTime now = LocalDateTime.now();
        payment.outTradeNo = "offline" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        OfflinePaymentRequest offlinePaymentRequest = new OfflinePaymentRequest();
        offlinePaymentRequest.paymentView = payment;
        offlinePaymentRequest.fullName = offlineRequest.fullName;
        offlinePaymentRequest.phone = offlineRequest.phone;
        PaymentView paymentView = paymentWebService.offline(offlinePaymentRequest);

        updateOrderPayment(pendingPaymentRequest, paymentView);
        return payService.offlineResponse(paymentView);
    }

    @Path("/ajax/order/pay/credit")
    @POST
    public CreditPayResponse creditPay(Request request) {
        String paymentId = request.queryParam("id").orElseThrow(() -> new NotFoundException("missing payment id"));
        User user = request.require(User.class, null);
        if (user == null) throw new UnauthenticatedException("user not login");
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(user.id);
        CustomerView customerView = customerView(user, dealerUser.isPresent() ? dealerUser.get() : null);
        CreditPayRequest creditPayRequest = request.body(CreditPayRequest.class);
        Optional<String> prepareRequestOptional = request.session().get(paymentId(paymentId));
        if (!prepareRequestOptional.isPresent()) {
            throw new NotFoundException("missing payment");
        }
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.payPassword = creditPayRequest.password;
        DealerUserResponse dealerUserResponse = dealerUserWebService.payPasswordAuthenticate(user.id, authenticationRequest);
        ChargePaymentRequest chargePaymentRequest = new ChargePaymentRequest();
        chargePaymentRequest.customerView = customerView;
        PendingPaymentRequest pendingPaymentRequest = JSON.fromJSON(prepareRequestOptional.get(), PendingPaymentRequest.class);
        OrderView checkOrder = orderWebService.get(pendingPaymentRequest.orders.get(0).id);
        if (checkOrder.paymentId != null) {
            PaymentView check = paymentWebService.get(checkOrder.paymentId);
            if (!PaymentStatusView.PENDING_PAYMENT.equals(check.status) || PaymentMethodView.OFFLINE.equals(check.method)) {
                CreditPayResponse creditPayResponse = new CreditPayResponse();
                creditPayResponse.result = "fail";
                creditPayResponse.message = "请勿重复支付";
                return creditPayResponse;
            }
        }
        PaymentView payment = new PaymentView();
        payment.id = UUID.randomUUID().toString();
        payment.status = PaymentStatusView.PENDING_PAYMENT;
        payment.paymentDate = LocalDateTime.now();
        payment.customerId = customerView.id;
        payment.title = pendingPaymentRequest.title;
        payment.description = pendingPaymentRequest.description;
        payment.total = pendingPaymentRequest.total;
        payment.latestEffectTime = payService.latestEffectTime(pendingPaymentRequest);
        payment.method = PaymentMethodView.CREDIT;
        chargePaymentRequest.paymentView = payment;
        PaymentView paymentView = paymentWebService.charge(chargePaymentRequest);
        CreditPayResponse creditPayResponse = payService.creditPay(user, paymentView, pendingPaymentRequest.orders, dealerUserResponse);
        if ("success".equals(creditPayResponse.result)) {
            creditPayResponse.paymentId = payment.id;
        }
        return creditPayResponse;
    }

    @Path("/ajax/order/query/:type/:tradeNo")
    @GET
    public QueryPayResponse queryPay(Request request) {
        String type = request.pathParam("type");
        String tradeNo = request.pathParam("tradeNo");
        QueryPayRequest queryPayRequest = new QueryPayRequest();
        if ("order".equals(type)) {
            queryPayRequest.outTradeNo = tradeNo;
            queryPayRequest.transactionId = "";
        } else if ("pay".equals(type)) {
            queryPayRequest.outTradeNo = "";
            queryPayRequest.transactionId = tradeNo;
        } else
            throw new BadRequestException("type", null, "type is not correct, type={}", type);
        queryPayRequest.needDetail = true;
        return payService.queryPay(queryPayRequest);
    }

    @Path("/ajax/order/repay/:paymentId")
    @GET
    public void repay(Request request) {
        String paymentId = request.pathParam("paymentId");
        PaymentView paymentView = paymentWebService.get(paymentId);
        UpdatePaymentRequest updatePaymentRequest = new UpdatePaymentRequest();
        setRepayRequest(updatePaymentRequest, paymentView, request);
        paymentWebService.update(paymentId, updatePaymentRequest);
    }

    private void setRepayRequest(UpdatePaymentRequest repayRequest, PaymentView paymentView, Request request) {
        repayRequest.paymentTime = paymentView.paymentDate;
        repayRequest.status = PaymentStatusView.PENDING_PAYMENT;
        repayRequest.method = paymentView.method;
        repayRequest.total = paymentView.total;
        repayRequest.fullName = paymentView.billingAddress.fullName;
        repayRequest.phone = paymentView.billingAddress.phone;
        repayRequest.address = paymentView.billingAddress.address;
        repayRequest.city = paymentView.billingAddress.city;
        repayRequest.province = paymentView.billingAddress.province;
        repayRequest.zipCode = paymentView.billingAddress.zipCode;
        repayRequest.requestBy = request.require(User.class).id;
    }

    @Path("/ajax/order/:paymentId/status")
    @GET
    public Response isOrderPaid(Request request) {
        String paymentId = request.pathParam("paymentId");
        PaymentView paymentView = paymentWebService.get(paymentId);
        Map<String, String> data = Maps.newHashMap();
        data.put("status", paymentView.status.name());
        return Response.body(data);
    }

    @Path("/ajax/order/:orderId")
    @DELETE
    public void delete(Request request) {
        User user = request.require(User.class, null);
        if (user == null) throw new UnauthenticatedException("user not login");
        String orderId = request.pathParam("orderId");
        OrderView orderView = orderWebService.get(orderId);
        if (!orderView.customerId.equals(user.id)) throw new UnauthenticatedException("operation forbidden");
        orderWebService.delete(orderId);
    }

    @Path("/ajax/order/payment/:paymentId")
    @GET
    public Response findByPaymentId(Request request) {
        User user = request.require(User.class, null);
        if (user == null) user = userService.getCommonAnonym();
        String paymentId = request.pathParam("paymentId");
        SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
        searchOrderRequest.customerId = user.id;
        searchOrderRequest.paymentId = paymentId;
        searchOrderRequest.page = 1;
        searchOrderRequest.limit = Integer.MAX_VALUE;
        return Response.body(orderWebService.search(searchOrderRequest));
    }

    @Path("/ajax/order/:orderId/save")
    @PUT
    public PaymentResponse save(Request request) {
        String orderId = request.pathParam("orderId");
        String checkoutId = "checkout-request:" + orderId;
        Optional<String> checkoutSessionRequest = request.session().get(checkoutId);
        if (!checkoutSessionRequest.isPresent()) {
            throw new StandardException("missing checkout request");
        }

        CheckoutSessionRequest sessionRequest = JSON.fromJSON(checkoutSessionRequest.get(), CheckoutSessionRequest.class);

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        OrderViewBuildRequest orderViewBuildRequest = new OrderViewBuildRequest();
        orderViewBuildRequest.productId = sessionRequest.productId;
        orderViewBuildRequest.value = sessionRequest.value;
        OrderView orderView = productConvertWebService.buildOrderView(orderViewBuildRequest);
        orderView.id = orderId;
        checkoutRequest.orders = Lists.newArrayList(orderView);

        User user = request.require(User.class);
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(user.id);
        checkoutRequest.customer = customerView(user, dealerUser.isPresent() ? dealerUser.get() : null);

        checkoutRequest.customerIPAddress = request.client().ip();

        CheckoutResponse checkoutResponse = orderWebService.checkout(checkoutRequest);
        if (checkoutResponse.error != null) {
            throw new StandardException(checkoutResponse.error.errorMessage);
        }
        PendingPaymentRequest pendingPaymentRequest = new PendingPaymentRequest();
        pendingPaymentRequest.paymentId = UUID.randomUUID().toString();
        pendingPaymentRequest.title = orderView.productDisplayName;
        pendingPaymentRequest.description = orderView.productDisplayName;
        pendingPaymentRequest.total = orderView.total;
        pendingPaymentRequest.orders = checkoutRequest.orders;
        request.session().remove(checkoutId);
        request.session().set("payment-request:" + pendingPaymentRequest.paymentId, JSON.toJSON(pendingPaymentRequest));
        PaymentResponse response = new PaymentResponse();
        response.paymentId = pendingPaymentRequest.paymentId;
        return response;
    }
}
