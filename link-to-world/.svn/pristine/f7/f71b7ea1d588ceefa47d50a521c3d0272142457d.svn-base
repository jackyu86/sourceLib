package com.caej.site.order;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.api.pay.KdlinsPayWebService;
import com.caej.api.pay.QueryPayRequest;
import com.caej.api.pay.QueryPayResponse;
import com.caej.api.pay.RefundRequest;
import com.caej.api.pay.RefundResponse;
import com.caej.api.pay.ToPayRequest;
import com.caej.api.pay.ToPayResponse;
import com.caej.api.util.MD5Encrypt;
import com.caej.client.ProductWebServiceClient;
import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.order.OrderWebService;
import com.caej.order.PaymentWebService;
import com.caej.order.RefundTrackingWebService;
import com.caej.order.order.CreateRefundTrackingRequest;
import com.caej.order.order.OrderItemResponse;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.UpdateOrderPaymentRequest;
import com.caej.order.payment.PaymentMethodView;
import com.caej.order.payment.PaymentStatusView;
import com.caej.order.payment.PaymentView;
import com.caej.order.payment.UpdatePaymentRequest;
import com.caej.product.api.product.ProductPeriodStartTimeType;
import com.caej.product.api.product.ProductResponse;
import com.caej.site.config.PayConfig;
import com.caej.site.order.ajax.CreditPayResponse;
import com.caej.site.order.ajax.OrderToPayRequest;
import com.caej.site.order.ajax.OrderToPayResponse;
import com.caej.site.order.ajax.PendingPaymentRequest;
import com.caej.site.util.UUIDUtil;
import com.google.common.base.Strings;

import app.dealer.api.CreditWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.credit.CheckoutRequest;
import app.dealer.api.dealer.DealerUserResponse;
import io.sited.user.web.User;
import io.sited.util.JSON;
import io.sited.web.WebConfig;

/**
 * @author miller
 */
public class PayService {
    private final Logger logger = LoggerFactory.getLogger(PayService.class);
    @Inject
    PayConfig payConfig;
    @Inject
    WebConfig webConfig;
    @Inject
    ProductWebServiceClient productWebServiceClient;
    @Inject
    KdlinsPayWebService kdlinsPayWebService;
    @Inject
    PaymentWebService paymentWebService;
    @Inject
    OrderWebService orderWebService;
    @Inject
    CreditWebService creditWebService;
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    RefundTrackingWebService refundTrackingWebService;

    public OrderToPayResponse toPay(OrderToPayRequest request, User user, PaymentView paymentView) {
        ToPayRequest toPayRequest = new ToPayRequest();
        UpdatePaymentRequest updatePaymentRequest = new UpdatePaymentRequest();
        initToPayRequest(toPayRequest, request, paymentView);
        System.out.println(JSON.toJSON(toPayRequest));
        ToPayResponse toPayResponse = kdlinsPayWebService.pay(toPayRequest);
        if (0 == toPayResponse.status) {
            initUpdatePaymentRequest(updatePaymentRequest, toPayResponse, paymentView, toPayRequest, user);
            paymentWebService.update(paymentView.id, updatePaymentRequest);
        }

        return buildToPayResponseValue(request.method, toPayResponse);
    }

    private void initToPayRequest(ToPayRequest toPayRequest, OrderToPayRequest request, PaymentView paymentView) {
        toPayRequest.userId = payConfig.payUserId;
        toPayRequest.outTradeNo = UUIDUtil.generate();
        toPayRequest.timeStamp = System.currentTimeMillis();
        System.out.println(toPayRequest.timeStamp);
        toPayRequest.sign = genSign(toPayRequest.timeStamp);
        toPayRequest.channel = request.method.name();
        Double amount = paymentView.total * 100;
        toPayRequest.amount = amount.intValue();
        toPayRequest.title = paymentView.title;
        toPayRequest.description = paymentView.description;
        toPayRequest.optional = Strings.isNullOrEmpty(paymentView.optional) ? "" : paymentView.optional;
        toPayRequest.returnUrl = webConfig.baseURL() + payConfig.returnUrl + "/" + paymentView.id;
        toPayRequest.notifyUrl = webConfig.baseURL() + payConfig.notifyUrl;
        toPayRequest.timeout = payConfig.timeout;
        toPayRequest.limitPay = "";
        if (PaymentMethodView.ALI_WEB.equals(request.method)) {
            toPayRequest.showUrl = Strings.isNullOrEmpty(paymentView.showUrl) ? webConfig.baseURL() : paymentView.showUrl;
        } else {
            toPayRequest.showUrl = "";
        }
        if (PaymentMethodView.WX_NATIVE.equals(request.method)) {
            toPayRequest.qrCodeMode = "4";
            toPayRequest.qrCodeWidth = "300";
        } else {
            toPayRequest.qrCodeMode = "";
            toPayRequest.qrCodeWidth = "";
        }
        toPayRequest.pcPay = "";
        toPayRequest.openId = "";
    }

    private void initUpdatePaymentRequest(UpdatePaymentRequest updatePaymentRequest, ToPayResponse toPayResponse, PaymentView paymentView, ToPayRequest toPayRequest, User user) {
        updatePaymentRequest.paymentTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(toPayRequest.timeStamp), TimeZone.getDefault().toZoneId());
        updatePaymentRequest.status = paymentView.status;
        updatePaymentRequest.method = PaymentMethodView.valueOf(toPayRequest.channel);
        updatePaymentRequest.total = paymentView.total;
        updatePaymentRequest.fullName = paymentView.billingAddress.fullName;
        updatePaymentRequest.phone = paymentView.billingAddress.phone;
        updatePaymentRequest.address = paymentView.billingAddress.address;
        updatePaymentRequest.city = paymentView.billingAddress.city;
        updatePaymentRequest.province = paymentView.billingAddress.province;
        updatePaymentRequest.zipCode = paymentView.billingAddress.zipCode;
        updatePaymentRequest.requestBy = user.id;
        updatePaymentRequest.outTradeNo = toPayRequest.outTradeNo;
        updatePaymentRequest.title = paymentView.title;
        updatePaymentRequest.description = paymentView.description;
        updatePaymentRequest.optional = paymentView.optional;
        updatePaymentRequest.returnUrl = toPayRequest.returnUrl;
        updatePaymentRequest.notifyUrl = toPayRequest.notifyUrl;
        updatePaymentRequest.timeout = toPayRequest.timeout;
        updatePaymentRequest.limitPay = toPayRequest.limitPay;
        updatePaymentRequest.pcPay = toPayRequest.pcPay;
        updatePaymentRequest.openId = toPayRequest.openId;
        updatePaymentRequest.showUrl = toPayRequest.showUrl;
        updatePaymentRequest.qrCodeMode = toPayRequest.qrCodeMode;
        updatePaymentRequest.qrCodeWidth = toPayRequest.qrCodeWidth;
        updatePaymentRequest.sign = toPayRequest.sign;
        updatePaymentRequest.payTimestamp = toPayRequest.timeStamp;
        updatePaymentRequest.codeUrl = toPayResponse.codeUrl;
        updatePaymentRequest.qrCode = toPayResponse.qrCode;
        updatePaymentRequest.payUrl = toPayResponse.payUrl;
        updatePaymentRequest.billId = toPayResponse.billId;
        updatePaymentRequest.billUrl = toPayResponse.billUrl;
        updatePaymentRequest.toPayError = Arrays.toString(toPayResponse.errors.toArray());
    }

    private OrderToPayResponse buildToPayResponseValue(PaymentMethodView method, ToPayResponse toPayResponse) {
        OrderToPayResponse orderToPayResponse = new OrderToPayResponse();
        orderToPayResponse.method = method;
        if (PaymentMethodView.ALI_WEB.equals(method)) {
            orderToPayResponse.value = toPayResponse.payUrl;
        }
        if (PaymentMethodView.WX_NATIVE.equals(method)) {
            orderToPayResponse.value = toPayResponse.qrCode;
        }
        orderToPayResponse.status = toPayResponse.status;
        return orderToPayResponse;
    }

    private String genSign(Long timestamp) {
        try {
            return MD5Encrypt.encrypt(payConfig.payUserId + timestamp + payConfig.payKey);
        } catch (Exception e) {
            throw new RuntimeException("genSign error,error={}", e);
        }
    }

    public OrderToPayResponse response(PaymentView paymentView) {
        if (PaymentStatusView.PAYMENT_FAILED.name().equals(paymentView.status.name())) return null;
        OrderToPayResponse orderToPayResponse = new OrderToPayResponse();
        orderToPayResponse.method = paymentView.method;
        orderToPayResponse.status = 0;
        if (PaymentMethodView.ALI_WEB.equals(paymentView.method)) {
            if (paymentView.payUrl == null) return null;
            orderToPayResponse.value = paymentView.payUrl;
        }
        if (PaymentMethodView.WX_NATIVE.equals(paymentView.method)) {
            if (paymentView.qrCode == null) return null;
            orderToPayResponse.value = paymentView.qrCode;
        }
        return orderToPayResponse;
    }

    public CreditPayResponse creditPay(User user, PaymentView paymentView, List<OrderView> orders, DealerUserResponse dealerUserResponse) {
        CreditPayResponse creditPayResponse = useCredit(user, dealerUserResponse.dealerId, paymentView);
        UpdateOrderPaymentRequest updateOrderPaymentRequest = new UpdateOrderPaymentRequest();
        updateOrderPaymentRequest.ids = orders.stream().map(orderView -> orderView.id).collect(Collectors.toList());
        updateOrderPaymentRequest.status = OrderStatusView.PAYMENT_COMPLETED;
        updateOrderPaymentRequest.paymentId = paymentView.id;
        updateOrderPaymentRequest.paymentMethod = paymentView.method;
        updateOrderPaymentRequest.outTradeNo = paymentView.outTradeNo;
        orderWebService.updateOrderPayment(updateOrderPaymentRequest);
        return creditPayResponse;
    }

    private CreditPayResponse useCredit(User user, String dealerId, PaymentView paymentView) {
        CheckoutRequest checkoutCreditRequest = new CheckoutRequest();
        checkoutCreditRequest.amount = paymentView.total;
        checkoutCreditRequest.paymentId = paymentView.id;
        checkoutCreditRequest.operator = user.username;
        checkoutCreditRequest.requestBy = "kdlins-website";
        CreditPayResponse creditPayResponse = new CreditPayResponse();
        try {
            creditWebService.checkout(dealerId, checkoutCreditRequest);
            updatePaymentByCredit(paymentView);
            creditPayResponse.result = "success";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            creditPayResponse.result = "fail";
            if (e.getMessage().contains("totalCreditsNoneEnough")) {
                creditPayResponse.message = "授信余额不足!";
            } else {
                creditPayResponse.message = "出错了！";
            }
        }
        return creditPayResponse;
    }

    private void updatePaymentByCredit(PaymentView paymentView) {
        UpdatePaymentRequest updatePaymentRequest = new UpdatePaymentRequest();
        updatePaymentRequest.paymentTime = LocalDateTime.now();
        updatePaymentRequest.status = PaymentStatusView.PAYMENT_COMPLETED;
        updatePaymentRequest.method = PaymentMethodView.CREDIT;
        updatePaymentRequest.total = paymentView.total;
        updatePaymentRequest.outTradeNo = UUIDUtil.generate();
        paymentWebService.update(paymentView.id, updatePaymentRequest);
    }

    public LocalDateTime latestEffectTime(PendingPaymentRequest pendingPaymentRequest) {
        LocalDateTime latestEffectTime = null;
        for (OrderView orderView : pendingPaymentRequest.orders) {
            ProductResponse productResponse = productWebServiceClient.get(orderView.productId);
            if (ProductPeriodStartTimeType.LATEST.equals(productResponse.period.startTimeType)) {
                LocalDateTime currentEffectTime = latestEffectTime(productResponse);
                if (latestEffectTime == null) latestEffectTime = currentEffectTime;
                if (latestEffectTime.isBefore(currentEffectTime)) latestEffectTime = currentEffectTime;
            } else {
                LocalDateTime currentEffectTime = userInput(orderView.form);
                if (latestEffectTime == null) latestEffectTime = currentEffectTime;
                if (latestEffectTime.isBefore(currentEffectTime)) latestEffectTime = currentEffectTime;
            }
        }
        return latestEffectTime;
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
            //todo age
            case AGE:
            case LIFE:
            case NONE:
            default:
                break;
        }
        return startTime;
    }

    private LocalDateTime userInput(Map<String, Object> form) {
        Map<String, Object> group = JSON.fromJSON(JSON.toJSON(form.get("plan")), Map.class);
        return LocalDateTime.parse(group.get("startTime").toString() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Boolean isSignValid(Long timestamp, String sign) throws Exception {
        String source = genSign(timestamp);
        return sign.equals(source);
    }

    public QueryPayResponse queryPay(QueryPayRequest queryPayRequest) {
        queryPayRequest.userId = payConfig.payUserId;
        queryPayRequest.timeStamp = System.currentTimeMillis();
        queryPayRequest.sign = genSign(queryPayRequest.timeStamp);
        System.out.println(JSON.toJSON(queryPayRequest));
        return kdlinsPayWebService.queryPay(queryPayRequest);
    }

    public RefundResponse refund(PaymentView payment, OrderItemResponse orderItem, String refundReason) {
        CreateRefundTrackingRequest trackingRequest = new CreateRefundTrackingRequest();
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.userId = payConfig.payUserId;
        refundRequest.timeStamp = System.currentTimeMillis();
        refundRequest.sign = genSign(refundRequest.timeStamp);
        refundRequest.outRefundNo = orderItem.id;
        refundRequest.transactionId = payment.transactionId;
        Double total = payment.total * 100;
        refundRequest.amount = total.intValue();
        refundRequest.channel = refundChannel(payment.method);
        refundRequest.refundReason = refundReason;
        Map<String, Object> map = kdlinsPayWebService.refund(refundRequest);
        RefundResponse refundResponse = new RefundResponse();
        refundResponse.refundId = map.get("refundId").toString();
        refundResponse.status = map.get("status").toString();
        refundResponse.errors = JSON.toJSON(map.get("errors"));
        trackingRequest.orderItemId = orderItem.id;
        trackingRequest.refundId = refundResponse.refundId;
        trackingRequest.request = JSON.toJSON(refundRequest);
        trackingRequest.status = refundResponse.status;
        trackingRequest.errors = refundResponse.errors;
        refundTrackingWebService.create(trackingRequest);
        return refundResponse;
    }

    private String refundChannel(PaymentMethodView method) {
        if (method.name().contains("ALI")) {
            return "alipay";
        } else if (method.name().contains("WX")) {
            return "weixin";
        }
        return null;
    }

    public OrderToPayResponse offlineResponse(PaymentView paymentView) {
        OrderToPayResponse orderToPayResponse = new OrderToPayResponse();
        orderToPayResponse.status = 0;
        orderToPayResponse.method = PaymentMethodView.OFFLINE;
        orderToPayResponse.paymentId = paymentView.id;
        orderToPayResponse.value = webConfig.baseURL() + payConfig.returnUrl + "/" + paymentView.id;
        return orderToPayResponse;
    }
}
