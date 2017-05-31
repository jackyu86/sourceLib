package com.caej.order.payment;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.time.LocalDateTime;

/**
 * @author chi
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchPaymentRequest {
    public String customerId;
    public String dealerId;
    public String paymentId;
    public LocalDateTime paymentDateFrom;
    public LocalDateTime paymentDateTo;
    public Integer skip;
    public Integer limit;
}
