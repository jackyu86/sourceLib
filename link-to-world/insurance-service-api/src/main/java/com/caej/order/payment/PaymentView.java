package com.caej.order.payment;



import java.time.LocalDateTime;

import com.caej.order.order.AddressView;

/**
 * @author chi
 */
public class PaymentView {
    public String id;
    public LocalDateTime paymentDate;
    public PaymentStatusView status;
    public PaymentMethodView method;
    public String payMode;
    public Double total = 0D;
    public String title;
    public String description;
    public String optional;
    public String showUrl;
    public String transactionId;
    public String outTradeNo;
    public String customerId;
    public LocalDateTime latestEffectTime;
    public String payUrl;
    public String qrCode;

    public AddressView billingAddress;
}
