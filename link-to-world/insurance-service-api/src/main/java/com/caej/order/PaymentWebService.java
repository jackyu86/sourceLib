package com.caej.order;


import com.caej.order.payment.ChargePaymentRequest;
import com.caej.order.payment.OfflinePaymentRequest;
import com.caej.order.payment.PaymentView;
import com.caej.order.payment.UpdatePaymentByNotifyRequest;
import com.caej.order.payment.UpdatePaymentRequest;

import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface PaymentWebService {
    @Path("/api/payment/:id")
    @GET
    PaymentView get(@PathParam("id") String paymentId);

    @Path("/api/payment/:id")
    @PUT
    void update(@PathParam("id") String id, UpdatePaymentRequest request);

    @Path("/api/payment/notify/:outTradeNo")
    @PUT
    PaymentView updateByNotify(@PathParam("outTradeNo") String outTradeNo, UpdatePaymentByNotifyRequest updatePaymentByNotifyRequest);

    @Path("/api/payment")
    @POST
    PaymentView charge(ChargePaymentRequest chargePaymentRequest);

    @Path("/api/payment/offline")
    @POST
    PaymentView offline(OfflinePaymentRequest offlinePaymentRequest);
}
