package com.caej.site.checkout;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.client.ProductWebServiceClient;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.order.OrderWebService;
import com.caej.order.PaymentWebService;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.payment.PaymentView;
import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.product.ProductResponse;
import com.caej.site.order.ajax.PendingPaymentRequest;
import com.caej.site.user.service.UserService;
import com.google.common.collect.Maps;

import app.dealer.api.CreditWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.credit.CreditResponse;
import app.dealer.api.credit.CreditStatusView;
import app.dealer.api.dealer.DealerUserResponse;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.user.web.User;
import io.sited.util.JSON;

/**
 * TODO(chi): rename path /checkout -> /order
 *
 * @author Jonathan.Guo
 */
public class PlaceOrderController {
    @Inject
    ProductWebServiceClient productWebServiceClient;
    @Inject
    ProductFormWebService productFormWebService;
    @Inject
    PaymentWebService paymentWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    CreditWebService creditWebService;
    @Inject
    OrderWebService orderWebService;
    @Inject
    InsuranceVendorWebService insuranceVendorWebService;
    @Inject
    UserService userService;

    @Path("/checkout/preview")
    @GET
    public Response preview(Request request) {
        String id = request.queryParam("id").orElseThrow(() -> new NotFoundException("missing checkoutId"));
        Optional<String> checkoutRequestOptional = request.session().get("checkout-request:" + id);
        if (!checkoutRequestOptional.isPresent()) {
            throw new NotFoundException("missing checkout request");
        }
        CheckoutSessionRequest checkoutSessionRequest = JSON.fromJSON(checkoutRequestOptional.get(), CheckoutSessionRequest.class);
        ProductResponse product = productWebServiceClient.get(checkoutSessionRequest.productId);
        Map<String, Object> context = Maps.newHashMap();
        context.put("id", checkoutSessionRequest.productId);
        context.put("checkoutId", id);
        context.put("product", product);
        context.put("vendor", insuranceVendorWebService.get(product.insuranceVendorId.toString()));
        Map<String, Object> value = Maps.newHashMap(checkoutSessionRequest.value);
        context.put("form", productFormWebService.checkoutPreview(checkoutSessionRequest.productId, value));
        return Response.template("/checkout/preview.html", context);
    }

    @Path("/checkout/pay")
    @GET
    public Response pay(Request request) {
        String paymentId = request.queryParam("id").orElseThrow(() -> new NotFoundException("missing payment id"));
        Optional<String> preparePayRequestOptional = request.session().get(paymentId(paymentId));
        if (!preparePayRequestOptional.isPresent()) {
            throw new NotFoundException("missing payment");
        }
        PendingPaymentRequest pendingPaymentRequest = JSON.fromJSON(preparePayRequestOptional.get(), PendingPaymentRequest.class);
        Map<String, Object> context = Maps.newHashMap();
        List<OrderView> orderList = pendingPaymentRequest.orders;
        context.put("orderList", orderList);
        context.put("paymentId", paymentId);
        sum(context, orderList);
        User user = request.require(User.class, null);
        if (user != null) {
            Optional<DealerUserResponse> dealerUserResponseOptional = dealerUserWebService.get(user.id);
            if (dealerUserResponseOptional.isPresent()) {
                String dealerId = dealerUserResponseOptional.get().dealerId;
                context.put("dealerId", dealerId);
                Optional<CreditResponse> credit = creditWebService.get(dealerId);
                Double totalCredit = credit.isPresent() ? BigDecimal.valueOf(credit.get().totalCredits).setScale(2, BigDecimal.ROUND_UP).doubleValue() : 0d;
                context.put("totalCredit", totalCredit);
                String creditStatus = credit.isPresent() ? credit.get().status.name() : CreditStatusView.ACTIVE.name();
                context.put("creditStatus", creditStatus);
            }
        }
        return Response.template("/checkout/pay.html", context);
    }

    private String paymentId(String id) {
        return "payment-request:" + id;
    }

    private void sum(Map<String, Object> context, List<OrderView> orderList) {
        Integer insuredCount = 0;
        Double totalPrice = 0D;
        Double total = 0D;
        Double discount = 0D;
        Double invoiceFee = 0D;
        for (OrderView orderView : orderList) {
            insuredCount += orderView.items.size();
            totalPrice += orderView.price;
            discount += orderView.discount;
            total += orderView.total;
            invoiceFee += orderView.invoiceFee == null ? 0D : orderView.invoiceFee;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        context.put("insuredCount", insuredCount);
        context.put("totalPrice", decimalFormat.format(totalPrice));
        context.put("salePrice", decimalFormat.format(totalPrice - discount));
        context.put("hasDiscount", discount == 0.0);
        context.put("invoiceFee", invoiceFee);
        context.put("total", decimalFormat.format(total));
    }

    @Path("/checkout/pay-finish/:paymentId")
    @GET
    public Response finish(Request request) {
        User user = request.require(User.class, null);
        if (user == null) {
            user = userService.getCommonAnonym();
        }
        String paymentId = request.pathParam("paymentId");
        SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
        searchOrderRequest.customerId = user.id;
        searchOrderRequest.paymentId = paymentId;
        searchOrderRequest.page = 1;
        searchOrderRequest.limit = Integer.MAX_VALUE;
        FindView<OrderView> orderList = orderWebService.search(searchOrderRequest);
        Map<String, Object> context = Maps.newHashMap();
        PaymentView payment = paymentWebService.get(paymentId);
        context.put("payment", payment);
        context.put("orderList", orderList);
        sum(context, orderList.items);
        context.put("paymentId", paymentId);
        context.put("order", orderList);
        return Response.template("/checkout/pay-finish.html", context);
    }
}
