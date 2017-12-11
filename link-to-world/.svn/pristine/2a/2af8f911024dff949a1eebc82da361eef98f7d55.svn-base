package com.caej.site.order.ajax;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.inject.Inject;

import com.caej.api.pay.OrderNotifyRequest;
import com.caej.order.OrderWebService;
import com.caej.order.PaymentWebService;
import com.caej.order.order.OrderItemResponse;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.UpdateOrderByNotify;
import com.caej.order.payment.PaymentStatusView;
import com.caej.order.payment.PaymentView;
import com.caej.order.payment.UpdatePaymentByNotifyRequest;
import com.caej.site.order.PayService;
import com.google.common.collect.Maps;

import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.web.User;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class OrderPayAJAXController {
    @Inject
    PaymentWebService paymentWebService;
    @Inject
    PayService payService;
    @Inject
    OrderWebService orderWebService;

    @Path("/order/pay/notify")
    @POST
    public Response notify(Request request) throws Exception {
        OrderNotifyRequest orderNotifyRequest = request.body(OrderNotifyRequest.class);
        if (!payService.isSignValid(orderNotifyRequest.timeStamp, orderNotifyRequest.sign))
            return Response.empty();
        UpdatePaymentByNotifyRequest updatePaymentByNotifyRequest = new UpdatePaymentByNotifyRequest();
        updatePaymentByNotifyRequest.pcOutTradeNo = orderNotifyRequest.pcOutTradeNo;
        updatePaymentByNotifyRequest.type = orderNotifyRequest.type;
        updatePaymentByNotifyRequest.notifyPayTime = orderNotifyRequest.payTime;
        updatePaymentByNotifyRequest.messageDetail = null != orderNotifyRequest.messageDetail ? JSON.toJSON(orderNotifyRequest.messageDetail) : null;
        updatePaymentByNotifyRequest.transactionId = orderNotifyRequest.transactionId;
        updatePaymentByNotifyRequest.status = PaymentStatusView.PAYMENT_COMPLETED;
        updatePaymentByNotifyRequest.requestBy = request.client().ip();
        PaymentView paymentView = paymentWebService.updateByNotify(orderNotifyRequest.outTradeNo, updatePaymentByNotifyRequest);
        UpdateOrderByNotify updateOrderByNotify = new UpdateOrderByNotify();
        updateOrderByNotify.status = OrderStatusView.valueOf(paymentView.status.name());
        updateOrderByNotify.outTradeNo = orderNotifyRequest.outTradeNo;
        orderWebService.updateByNotify(paymentView.id, updateOrderByNotify);
        return Response.body("SUCCESS".getBytes()).setContentType("text/plain");
    }

    @Path("/order/pay/notify")
    @GET
    public Response getNotify(Request request) throws Exception {
        OrderNotifyRequest orderNotifyRequest = request(request.queries());
        if (!payService.isSignValid(orderNotifyRequest.timeStamp, orderNotifyRequest.sign))
            return Response.empty();
        UpdatePaymentByNotifyRequest updatePaymentByNotifyRequest = new UpdatePaymentByNotifyRequest();
        updatePaymentByNotifyRequest.pcOutTradeNo = orderNotifyRequest.pcOutTradeNo;
        updatePaymentByNotifyRequest.type = orderNotifyRequest.type;
        updatePaymentByNotifyRequest.notifyPayTime = orderNotifyRequest.payTime;
        updatePaymentByNotifyRequest.messageDetail = null != orderNotifyRequest.messageDetail ? JSON.toJSON(orderNotifyRequest.messageDetail) : null;
        updatePaymentByNotifyRequest.transactionId = orderNotifyRequest.transactionId;
        updatePaymentByNotifyRequest.status = PaymentStatusView.PAYMENT_COMPLETED;
        updatePaymentByNotifyRequest.requestBy = request.client().ip();
        PaymentView paymentView = paymentWebService.updateByNotify(orderNotifyRequest.outTradeNo, updatePaymentByNotifyRequest);
        UpdateOrderByNotify updateOrderByNotify = new UpdateOrderByNotify();
        updateOrderByNotify.status = OrderStatusView.valueOf(paymentView.status.name());
        updateOrderByNotify.outTradeNo = orderNotifyRequest.outTradeNo;
        orderWebService.updateByNotify(paymentView.id, updateOrderByNotify);
        return Response.body("SUCCESS".getBytes()).setContentType("text/plain");
    }

    private OrderNotifyRequest request(Map<String, String> map) {
        OrderNotifyRequest orderNotifyRequest = new OrderNotifyRequest();
        orderNotifyRequest.sign = map.get("sign");
        orderNotifyRequest.timeStamp = Long.valueOf(map.get("timeStamp"));
        orderNotifyRequest.outTradeNo = map.get("outTradeNo");
        orderNotifyRequest.pcOutTradeNo = map.get("pcOutTradeNo");
        orderNotifyRequest.channel = map.get("channel");
        orderNotifyRequest.type = map.get("type");
        orderNotifyRequest.transactionId = map.get("transactionId");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        orderNotifyRequest.payTime = LocalDateTime.parse(map.get("payTime"), dateTimeFormatter);
        orderNotifyRequest.amount = Integer.valueOf(map.get("amount"));
        orderNotifyRequest.status = Boolean.valueOf(map.get("status"));
        if (map.get("messageDetail") != null)
            orderNotifyRequest.messageDetail = JSON.fromJSON(map.get("messageDetail"), Map.class);
        if (map.get("optional") != null)
            orderNotifyRequest.optional = JSON.fromJSON(map.get("optional"), Map.class);
        return orderNotifyRequest;
    }

    @Path("/order/pay/refund/:id")
    @PUT
    public Response refund(Request request) {
        User user = request.require(User.class, null);
        if (user == null) throw new UnauthenticatedException("user not login");
        OrderRefundRequest orderRefundRequest = request.body(OrderRefundRequest.class);
        Map<String, OrderView> map = Maps.newHashMap();
        Map<String, PaymentView> paymentViewMap = Maps.newHashMap();
        orderRefundRequest.itemIds.forEach(itemId -> {
            OrderItemResponse orderItemResponse = orderWebService.getItem(itemId);
            OrderView orderView = map.get(orderItemResponse.orderId);
            if (orderView == null) {
                orderView = orderWebService.get(orderItemResponse.orderId);
                if (!orderView.customerId.equals(user.id)) throw new UnauthenticatedException("operation forbidden");
            }
            PaymentView paymentView = paymentViewMap.get(orderView.paymentId);
            if (paymentView == null) {
                paymentView = paymentWebService.get(orderView.paymentId);
            }
            payService.refund(paymentView, orderItemResponse, "退保退款");
        });
        return Response.empty();
    }
}
