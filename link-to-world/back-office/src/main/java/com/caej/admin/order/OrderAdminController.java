package com.caej.admin.order;

import static com.caej.order.order.OrderStatusView.SURRENDERING;
import static com.caej.order.order.OrderStatusView.VENDOR_INSURED;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.order.OrderWebService;
import com.caej.order.PaymentWebService;
import com.caej.order.order.CustomerView;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.order.UpdateOrderItemUnderwritingStatusRequest;
import com.caej.order.order.UpdateOrderPaymentRequest;
import com.caej.order.order.UpdateOrderStatusRequest;
import com.caej.order.order.UpdateOrderUnderwritingStatusRequest;
import com.caej.order.payment.ChargePaymentRequest;
import com.caej.order.payment.PaymentStatusView;
import com.caej.order.payment.PaymentView;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.ProductPeriodStartTimeType;
import com.caej.product.api.product.ProductResponse;
import com.caej.underwriting.api.UnderwritingWebService;
import com.caej.underwriting.api.underwriting.DischargeRequest;
import com.caej.underwriting.api.underwriting.DischargeResponse;
import com.google.common.collect.Lists;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.DealerQuery;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.product.DealerProductView;
import io.sited.admin.AdminUser;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UserResponse;
import io.sited.util.JSON;

/**
 * Created by hubery.chen on 2017/1/3.
 */
public class OrderAdminController {
    @Inject
    OrderWebService orderWebService;
    @Inject
    UnderwritingWebService underwritingWebService;
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    PaymentWebService paymentWebService;
    @Inject
    UserWebService userWebService;
    @Inject
    ProductWebService productWebService;

    @Path("/admin/ajax/order/find")
    @PUT
    public FindView<OrderAdminResponse> find(Request request) {
        SearchOrderRequest searchOrderRequest = request.body(SearchOrderRequest.class);
        AdminUser adminUser = request.require(AdminUser.class);
        if (adminUser.hasRole("ProvinceAdmin")) {
            searchOrderRequest.dealerIds = findDealers(adminUser).items.stream().map(dealerResponse -> dealerResponse.id).collect(Collectors.toList());
        }
        if (adminUser.hasRole("VendorAdmin")) {
            searchOrderRequest.vendorId = adminUser.roles.stream().filter(role -> role.contains("VendorAdmin")).collect(Collectors.toList()).get(0).replace("VendorAdmin", "");
        }

        return FindView.map(orderWebService.search(searchOrderRequest), this::response);
    }

    @Path("/admin/ajax/order/:orderId/insure")
    @PUT
    public void insure(Request request) {
        String orderId = request.pathParam("orderId");
        OrderInsureAJAXRequest orderInsureAJAXRequest = request.body(OrderInsureAJAXRequest.class);
        UpdateOrderUnderwritingStatusRequest updateOrderUnderwritingStatusRequest = new UpdateOrderUnderwritingStatusRequest();
        updateOrderUnderwritingStatusRequest.transNo = orderInsureAJAXRequest.transNo;
        updateOrderUnderwritingStatusRequest.transType = "102";
        updateOrderUnderwritingStatusRequest.transDate = orderInsureAJAXRequest.transDate.format(DateTimeFormatter.ofPattern("YYYYMMdd"));
        updateOrderUnderwritingStatusRequest.transTime = orderInsureAJAXRequest.transTime.toLocalTime().toString();
        updateOrderUnderwritingStatusRequest.applyCode = orderInsureAJAXRequest.applyCode;
        updateOrderUnderwritingStatusRequest.status = VENDOR_INSURED;
        updateOrderUnderwritingStatusRequest.itemStatusList = orderInsureAJAXRequest.items.stream().map(item -> {
            UpdateOrderItemUnderwritingStatusRequest updateOrderItemUnderwritingStatusRequest = new UpdateOrderItemUnderwritingStatusRequest();
            updateOrderItemUnderwritingStatusRequest.id = item.id;
            updateOrderItemUnderwritingStatusRequest.orderStatus = VENDOR_INSURED;
            updateOrderItemUnderwritingStatusRequest.policyCode = item.policyCode;
            updateOrderItemUnderwritingStatusRequest.policyAddress = item.policyAddress;
            return updateOrderItemUnderwritingStatusRequest;
        }).collect(Collectors.toList());
        orderWebService.updateUnderwriting(orderId, updateOrderUnderwritingStatusRequest);
    }

    @Path("/admin/ajax/order/:orderId/pay")
    @PUT
    public void pay(Request request) {
        String orderId = request.pathParam("orderId");
        OrderPayAJAXRequest orderPayAJAXRequest = request.body(OrderPayAJAXRequest.class);
        OrderView order = orderWebService.get(orderId);
        String paymentId = charge(order, orderPayAJAXRequest);

        UpdateOrderPaymentRequest updateOrderPaymentRequest = new UpdateOrderPaymentRequest();
        updateOrderPaymentRequest.ids = Lists.newArrayList(orderId);
        updateOrderPaymentRequest.outTradeNo = orderPayAJAXRequest.outTradeNo;
        updateOrderPaymentRequest.paymentMethod = orderPayAJAXRequest.method;
        updateOrderPaymentRequest.status = OrderStatusView.PAYMENT_COMPLETED;
        updateOrderPaymentRequest.paymentId = paymentId;
        orderWebService.updateOrderPayment(updateOrderPaymentRequest);
    }

    @Path("/admin/ajax/order/:orderId/status")
    @PUT
    public void updateStatus(Request request) {
        String orderId = request.pathParam("orderId");
        UpdateOrderStatusRequest updateOrderStatusRequest = request.body(UpdateOrderStatusRequest.class);
        orderWebService.updateOrderStatus(orderId, updateOrderStatusRequest);
    }

    @Path("/admin/ajax/order/:orderId")
    @GET
    public OrderView get(Request request) {
        String orderId = request.pathParam("orderId");
        return orderWebService.get(orderId);
    }

    @Path("/admin/ajax/order/:orderId/discharge")
    @PUT
    public Response discharge(Request request) {
        String orderId = request.pathParam("orderId");

        OrderView orderView = orderWebService.get(orderId);

        if (OrderStatusView.VENDOR_INSURED.equals(orderView.orderStatus) && OrderStatusView.SURRENDER_FAILED.equals(orderView.orderStatus)) {
            throw new UnauthenticatedException("invalid order status");
        }

        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.status = SURRENDERING;
        orderWebService.updateOrderStatus(orderId, updateOrderStatusRequest);

        DischargeRequest dischargeRequest = new DischargeRequest();
        dischargeRequest.orderId = orderId;

        DischargeResponse dischargeResponse = underwritingWebService.discharge(dischargeRequest);
        return Response.body(dischargeResponse);
    }


    private String charge(OrderView order, OrderPayAJAXRequest request) {
        ChargePaymentRequest chargePaymentRequest = new ChargePaymentRequest();
        UserResponse userResponse = userWebService.get(order.customerId);
        CustomerView customerView = new CustomerView();
        customerView.id = userResponse.id;
        customerView.dealerId = order.dealerId;
        customerView.customerName = userResponse.fullName;
        customerView.phone = userResponse.phone;
        chargePaymentRequest.customerView = customerView;

        PaymentView payment = new PaymentView();
        payment.id = UUID.randomUUID().toString();
        payment.status = PaymentStatusView.PAYMENT_COMPLETED;
        payment.paymentDate = LocalDateTime.now();
        payment.customerId = customerView.id;
        payment.title = order.productDisplayName;
        payment.description = order.productDisplayName;
        payment.total = order.total;
        ProductResponse productResponse = productWebService.get(order.productId);
        payment.latestEffectTime = ProductPeriodStartTimeType.LATEST.equals(productResponse.period.startTimeType) ? latestEffectTime(productResponse) : userInput(order.form);
        chargePaymentRequest.paymentView = payment;
        payment.method = request.method;
        payment.outTradeNo = request.outTradeNo;

        return paymentWebService.charge(chargePaymentRequest).id;
    }

    private LocalDateTime latestEffectTime(ProductResponse product) {
        InsurancePeriodView earliestInsuranceTime = product.period.earliestInsuranceTime;
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime startTime = localDateTime;
        switch (earliestInsuranceTime.unit) {
            case DAY:
                startTime = localDateTime.plus(earliestInsuranceTime.value - 1, ChronoUnit.DAYS);
                break;
            case MONTH:
                startTime = localDateTime.plus(earliestInsuranceTime.value, ChronoUnit.MONTHS);
                break;
            case YEAR:
                startTime = localDateTime.plus(earliestInsuranceTime.value, ChronoUnit.YEARS);
                break;
            default:
                break;
        }
        return startTime;
    }

    private LocalDateTime userInput(Map<String, Object> form) {
        Map<String, Object> group = JSON.fromJSON(JSON.toJSON(form.get("plan")), Map.class);
        return LocalDateTime.parse(group.get("startTime").toString() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private FindView<DealerResponse> findDealers(AdminUser adminUser) {
        DealerQuery dealerQuery = new DealerQuery();
        if (adminUser.hasRole("ProvinceAdmin")) {
            dealerQuery.states = states(adminUser);
        }
        return dealerWebService.find(dealerQuery);
    }

    private List<String> states(AdminUser adminUser) {
        List<String> states = adminUser.roles.stream().filter(role -> role.contains("ProvinceAdmin")).map(role -> role.replace("ProvinceAdmin", "")).collect(Collectors.toList());
        FindProvincesRequest request = new FindProvincesRequest();
        request.shortNames = states;
        return insuranceCountryWebService.provinces(request).items.stream().map(insuranceProvinceResponse -> insuranceProvinceResponse.name).collect(Collectors.toList());
    }

    private OrderAdminResponse response(OrderView order) {
        OrderAdminResponse response = JSON.convert(order, OrderAdminResponse.class);
        if (order.dealerId != null) {
            Optional<DealerProductView> dealerProductOptional = dealerProductWebService.getByDealerIdAndProductName(order.dealerId, order.productName);
            dealerProductOptional.ifPresent(dealerProduct -> {
                if (response.surrenderMark != null) {
                    response.surrenderMark = dealerProduct.surrenderMark;
                }
            });
        }
        return response;
    }
}
