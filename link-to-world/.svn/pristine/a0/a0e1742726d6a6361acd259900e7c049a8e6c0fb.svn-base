package com.caej.order.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import com.caej.insurance.api.EnumPayModeWebService;
import com.caej.order.domain.Payment;
import com.caej.order.domain.PaymentTracking;
import com.caej.order.order.CustomerView;
import com.caej.order.payment.PaymentMethodView;
import com.caej.order.payment.PaymentStatusView;
import com.caej.order.payment.PaymentView;
import com.caej.order.payment.UpdatePaymentByNotifyRequest;
import com.caej.order.payment.UpdatePaymentRequest;

import io.sited.db.FindView;
import io.sited.db.JDBCRepository;
import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class PaymentService {
    @Inject
    JDBCRepository<Payment> repository;
    @Inject
    JDBCRepository<PaymentTracking> trackingRepository;
    @Inject
    EnumPayModeWebService enumPayModeWebService;

    public FindView<Payment> searchPaymentPending(LocalDateTime startCreatedTime, LocalDateTime endCreatedTime) {
        return repository.query(" status='PENDING_PAYMENT' and method<>'NOTSELECTED' and method<>'OFFLINE' and method<>'CREDIT' and created_time<? and created_time>?", startCreatedTime, endCreatedTime)
            .find();
    }

    public FindView<Payment> searchTimeout(LocalDateTime timeout) {
        return repository.query(" status='PENDING_PAYMENT' and method<>'NOTSELECTED' and method<>'OFFLINE' and method<>'CREDIT' and latest_effect_time<?", timeout)
            .find();
    }

    public void update(String id, UpdatePaymentRequest request) {
        Optional<Payment> origin = get(id);
        if (!origin.isPresent()) {
            throw new NotFoundException("missing payment,id={}", id);
        }
        Payment payment = origin.get();
        PaymentTracking tracking = new PaymentTracking();
        tracking.id = UUID.randomUUID().toString();
        tracking.paymentId = payment.id;
        setOldValue(tracking, payment);
        setNewValue(tracking, payment, request);
        repository.update(id, payment);
        trackingRepository.insert(tracking);
    }

    public Optional<Payment> get(String id) {
        return repository.query("id=?", id).findOne();
    }

    private void setOldValue(PaymentTracking tracking, Payment origin) {
        tracking.paymentDate = origin.paymentTime;
        tracking.status = origin.status;
        tracking.method = origin.method;
        tracking.payMode = origin.payMode;
        tracking.total = origin.total;
        tracking.fullName = origin.fullName;
        tracking.phone = origin.phone;
        tracking.address = origin.address;
        tracking.city = origin.city;
        tracking.province = origin.province;
        tracking.zipCode = origin.zipCode;
        tracking.updatedTime = origin.updatedTime;
        tracking.updatedBy = origin.updatedBy;
        tracking.pcOutTradeNo = origin.pcOutTradeNo;
        tracking.type = origin.type;
        tracking.transactionId = origin.transactionId;
        tracking.notifyPayTime = origin.notifyPayTime;
        tracking.messageDetail = origin.messageDetail;
    }

    private void setNewValue(PaymentTracking tracking, Payment origin, UpdatePaymentRequest request) {
        origin.paymentTime = request.paymentTime;
        origin.status = request.status;
        origin.method = request.method;
        origin.payMode = payMode(request.method);
        origin.total = request.total;
        origin.fullName = request.fullName;
        origin.phone = request.phone;
        origin.address = request.address;
        origin.city = request.city;
        origin.province = request.province;
        origin.zipCode = request.zipCode;
        origin.updatedBy = request.requestBy;
        origin.updatedTime = LocalDateTime.now();
        origin.outTradeNo = request.outTradeNo;
        origin.billId = request.billId;
        origin.billUrl = request.billUrl;
        origin.title = request.title;
        origin.description = request.description;
        origin.optional = request.optional;
        origin.returnUrl = request.returnUrl;
        origin.notifyUrl = request.notifyUrl;
        origin.timeout = request.timeout;
        origin.limitPay = request.limitPay;
        origin.pcPay = request.pcPay;
        origin.openId = request.openId;
        origin.showUrl = request.showUrl;
        origin.qrCodeMode = request.qrCodeMode;
        origin.qrCodeWidth = request.qrCodeWidth;
        origin.sign = request.sign;
        origin.payTimestamp = request.payTimestamp;
        origin.codeUrl = request.codeUrl;
        origin.qrCode = request.qrCode;
        origin.payUrl = request.payUrl;
        origin.toPayError = request.toPayError;
        tracking.paymentDateNew = request.paymentTime;
        tracking.statusNew = request.status;
        tracking.methodNew = request.method;
        tracking.payModeNew = payMode(request.method);
        tracking.totalNew = request.total;
        tracking.fullNameNew = request.fullName;
        tracking.phoneNew = request.phone;
        tracking.addressNew = request.address;
        tracking.cityNew = request.city;
        tracking.provinceNew = request.province;
        tracking.zipCodeNew = request.zipCode;
        tracking.updatedByNew = request.requestBy;
        tracking.updatedTimeNew = origin.updatedTime;
    }

    private String payMode(PaymentMethodView paymentMethodView) {
        Map<String, String> map = enumPayModeWebService.findAll();
        String name = "";
        if (paymentMethodView.name().contains("ALI")) {
            name = "支付宝";
        } else if (paymentMethodView.name().contains("WX")) {
            name = "财付通";
        } else if (paymentMethodView.name().equals("CREDIT")) {
            name = "代理代收";
        } else if (paymentMethodView.name().equals("OFFLINE")) {
            name = "代理代收";
        }
        return map.get(name);
    }

    public Payment update(String outTradeNo, UpdatePaymentByNotifyRequest updatePaymentByNotifyRequest) {
        Optional<Payment> origin = findByOutTradeNo(outTradeNo);
        if (!origin.isPresent()) {
            throw new NotFoundException("missing payment,outTradeNo={}", outTradeNo);
        }
        Payment payment = origin.get();
        return update(payment, updatePaymentByNotifyRequest);
    }

    public Optional<Payment> findByOutTradeNo(String outTradeNo) {
        return repository.query("select * from payment where out_trade_no=?", outTradeNo).findOne();
    }

    public Payment update(Payment payment, UpdatePaymentByNotifyRequest updatePaymentByNotifyRequest) {
        PaymentTracking tracking = new PaymentTracking();
        tracking.id = UUID.randomUUID().toString();
        tracking.paymentId = payment.id;
        setOldValue(tracking, payment);
        setNewValue(tracking, payment, updatePaymentByNotifyRequest);
        repository.update(payment.id, payment);
        trackingRepository.insert(tracking);
        return payment;
    }

    private void setNewValue(PaymentTracking tracking, Payment origin, UpdatePaymentByNotifyRequest request) {
        origin.pcOutTradeNo = request.pcOutTradeNo;
        origin.type = request.type;
        origin.notifyPayTime = request.notifyPayTime;
        origin.messageDetail = request.messageDetail;
        origin.transactionId = request.transactionId;
        origin.status = request.status;
        origin.updatedBy = request.requestBy;
        origin.updatedTime = LocalDateTime.now();
        tracking.statusNew = request.status;
        tracking.updatedByNew = request.requestBy;
        tracking.updatedTimeNew = origin.updatedTime;
        tracking.pcOutTradeNoNew = request.pcOutTradeNo;
        tracking.typeNew = request.type;
        tracking.notifyPayTimeNew = request.notifyPayTime;
        tracking.messageDetailNew = request.messageDetail;
        tracking.transactionIdNew = request.transactionId;
    }

    public Payment offline(PaymentView paymentView, String fullName, String phone) {
        Payment payment = new Payment();
        payment.id = paymentView.id;
        payment.createdTime = LocalDateTime.now();
        payment.paymentTime = paymentView.paymentDate;
        payment.status = paymentView.status;
        payment.total = paymentView.total;
        payment.title = paymentView.title;
        payment.description = paymentView.description;
        payment.fullName = fullName;
        payment.phone = phone;
        payment.method = PaymentMethodView.OFFLINE;
        payment.payMode = payMode(PaymentMethodView.OFFLINE);
        payment.customerId = paymentView.customerId;
        PaymentTracking tracking = new PaymentTracking();
        tracking.id = UUID.randomUUID().toString();
        tracking.paymentId = paymentView.id;
        tracking.paymentDateNew = payment.paymentTime;
        tracking.statusNew = payment.status;
        tracking.totalNew = payment.total;
        tracking.fullNameNew = payment.fullName;
        tracking.phoneNew = payment.phone;
        tracking.methodNew = payment.method;
        tracking.payModeNew = "";
        repository.insert(payment);
        trackingRepository.insert(tracking);
        return payment;
    }

    public Payment charge(PaymentView paymentView, CustomerView customerView) {
        Payment payment = new Payment();
        payment.id = paymentView.id;
        payment.createdTime = LocalDateTime.now();
        payment.paymentTime = paymentView.paymentDate;
        payment.status = paymentView.status;
        payment.total = paymentView.total;
        payment.title = paymentView.title;
        payment.description = paymentView.description;
        payment.fullName = customerView.customerName;
        payment.phone = customerView.phone;
        payment.method = PaymentMethodView.NOTSELECTED;
        payment.payMode = "";
        payment.customerId = customerView.id;
        payment.latestEffectTime = paymentView.latestEffectTime;
        PaymentTracking tracking = new PaymentTracking();
        tracking.id = UUID.randomUUID().toString();
        tracking.paymentId = paymentView.id;
        tracking.paymentDateNew = payment.paymentTime;
        tracking.statusNew = payment.status;
        tracking.totalNew = payment.total;
        tracking.fullNameNew = payment.fullName;
        tracking.phoneNew = payment.phone;
        tracking.methodNew = payment.method;
        tracking.payModeNew = "";
        repository.insert(payment);
        trackingRepository.insert(tracking);
        return payment;
    }

    public void updateStatusByJob(Payment payment, PaymentStatusView status) {
        PaymentTracking paymentTracking = new PaymentTracking();
        paymentTracking.id = UUID.randomUUID().toString();
        paymentTracking.paymentId = payment.id;
        paymentTracking.paymentDate = payment.paymentTime;
        paymentTracking.status = payment.status;
        paymentTracking.methodNew = payment.method;
        paymentTracking.payMode = payment.payMode;
        paymentTracking.total = payment.total;
        paymentTracking.fullName = payment.fullName;
        paymentTracking.phone = payment.phone;
        paymentTracking.address = payment.address;
        paymentTracking.city = payment.city;
        paymentTracking.province = payment.province;
        paymentTracking.zipCode = payment.zipCode;
        paymentTracking.updatedTime = payment.updatedTime;
        paymentTracking.updatedBy = payment.updatedBy;
        paymentTracking.paymentDateNew = payment.paymentTime;
        paymentTracking.statusNew = status;
        paymentTracking.methodNew = payment.method;
        paymentTracking.payModeNew = payment.payMode;
        paymentTracking.totalNew = payment.total;
        paymentTracking.fullNameNew = payment.fullName;
        paymentTracking.phoneNew = payment.phone;
        paymentTracking.addressNew = payment.address;
        paymentTracking.cityNew = payment.city;
        paymentTracking.provinceNew = payment.province;
        paymentTracking.zipCodeNew = payment.zipCode;
        paymentTracking.updatedTimeNew = LocalDateTime.now();
        paymentTracking.updatedByNew = "job";
        paymentTracking.pcOutTradeNo = payment.pcOutTradeNo;
        paymentTracking.pcOutTradeNoNew = payment.pcOutTradeNo;
        paymentTracking.type = payment.type;
        paymentTracking.notifyPayTime = payment.notifyPayTime;
        paymentTracking.notifyPayTimeNew = payment.notifyPayTime;
        paymentTracking.messageDetail = payment.messageDetail;
        paymentTracking.messageDetailNew = payment.messageDetail;
        paymentTracking.transactionId = payment.transactionId;
        paymentTracking.transactionIdNew = payment.transactionId;
        repository.query("update payment set updated_time=?,updated_by=?,status=? where id=?", paymentTracking.updatedTimeNew, paymentTracking.updatedByNew, status.name(), payment.id).execute();
        trackingRepository.insert(paymentTracking);
    }

}
