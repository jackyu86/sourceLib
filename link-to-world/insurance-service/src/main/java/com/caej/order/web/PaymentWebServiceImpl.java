package com.caej.order.web;


import java.util.Optional;

import javax.inject.Inject;

import com.caej.order.PaymentWebService;
import com.caej.order.domain.Payment;
import com.caej.order.order.AddressView;
import com.caej.order.payment.ChargePaymentRequest;
import com.caej.order.payment.OfflinePaymentRequest;
import com.caej.order.payment.PaymentView;
import com.caej.order.payment.UpdatePaymentByNotifyRequest;
import com.caej.order.payment.UpdatePaymentRequest;
import com.caej.order.service.PaymentService;

/**
 * @author chi
 */
public class PaymentWebServiceImpl implements PaymentWebService {
    @Inject
    PaymentService paymentService;

    @Override
    public PaymentView get(String paymentId) {
        Optional<Payment> payment = paymentService.get(paymentId);
        return payment.map(this::response).orElse(null);
    }

    @Override
    public void update(String id, UpdatePaymentRequest request) {
        paymentService.update(id, request);
    }

    @Override
    public PaymentView updateByNotify(String outTradeNo, UpdatePaymentByNotifyRequest updatePaymentByNotifyRequest) {
        return response(paymentService.update(outTradeNo, updatePaymentByNotifyRequest));
    }

    @Override
    public PaymentView charge(ChargePaymentRequest chargePaymentRequest) {
        Payment payment = paymentService.charge(chargePaymentRequest.paymentView, chargePaymentRequest.customerView);
        return response(payment);
    }

    @Override
    public PaymentView offline(OfflinePaymentRequest offlinePaymentRequest) {
        return response(paymentService.offline(offlinePaymentRequest.paymentView, offlinePaymentRequest.fullName, offlinePaymentRequest.phone));
    }

    private PaymentView response(Payment payment) {
        PaymentView paymentView = new PaymentView();
        paymentView.id = payment.id;
        paymentView.paymentDate = payment.paymentTime;
        paymentView.status = payment.status;
        paymentView.method = payment.method;
        paymentView.payMode = payment.payMode;
        paymentView.total = payment.total;
        paymentView.title = payment.title;
        paymentView.description = payment.description;
        paymentView.optional = payment.optional;
        paymentView.showUrl = payment.showUrl;
        AddressView addressView = new AddressView();
        addressView.fullName = payment.fullName;
        addressView.phone = payment.phone;
        addressView.address = payment.address;
        addressView.city = payment.city;
        addressView.province = payment.province;
        addressView.zipCode = payment.zipCode;
        paymentView.billingAddress = addressView;
        paymentView.transactionId = payment.transactionId;
        paymentView.outTradeNo = payment.outTradeNo;
        paymentView.customerId = payment.customerId;
        paymentView.latestEffectTime = payment.latestEffectTime;
        paymentView.payUrl = payment.payUrl;
        paymentView.qrCode = payment.qrCode;
        return paymentView;
    }
}
