package com.caej.order.payment;

import java.time.LocalDateTime;

/**
 * @author miller
 */
public class UpdatePaymentByNotifyRequest {
    public String pcOutTradeNo;
    public String type;
    public LocalDateTime notifyPayTime;
    public String messageDetail;
    public String transactionId;
    public PaymentStatusView status;
    public String requestBy;
}
