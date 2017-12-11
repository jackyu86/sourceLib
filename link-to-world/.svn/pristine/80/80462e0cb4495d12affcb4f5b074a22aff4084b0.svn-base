package com.caej.order.order;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.caej.order.payment.PaymentMethodView;

/**
 * @author chi
 */
public class OrderView {
    public String id;
    public String customerId;
    public String paymentId;
    public String vendorId;
    public String productId;
    public String productName;
    public String productDisplayName;
    public String productDescription;

    public LocalDateTime orderDate;
    public List<OrderItemView> items;
    public Map<String, Object> form;
    public OrderStatusView orderStatus;
    public ShippingStatus shippingStatus;
    public Double price;
    public Double shippingFee;
    public Double invoiceFee;
    public Double discount;
    public Double total;

    public LocalDateTime planStartTime;
    public String periodDisplayName;
    public Integer periodValue;
    public String periodUnit;
    public String unit;

    public String transNo;
    public String transType;

    public String errors;
    public String dealerId;
    public String dealerName;
    public LocalDateTime createdTime;
    public LocalDateTime updatedTime;
    public String outTradeNo;
    public String applyCode;
    public PaymentMethodView paymentMethod;
    public String insuredName;
    public String insuredIdType;
    public String insuredIdNumber;
    public String insuredPhone;
    public String insuredEmail;
    public String policyHolderName;
    public String policyHolderIdType;
    public String policyHolderIdNumber;
    public String policyHolderPhone;
    public String policyHolderEmail;

    public static class OrderItemView {
        public String id;
        public String name;
        public Map<String, Object> form;
        public Double price;
        public LocalDateTime orderDate;
        public OrderStatusView orderStatus;
        public String policyCode;
        public String policyAddress;
    }
}
