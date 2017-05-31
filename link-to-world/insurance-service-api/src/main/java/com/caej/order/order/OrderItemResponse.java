package com.caej.order.order;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author miller
 */
public class OrderItemResponse {
    public String id;
    public String orderId;
    public Map<String, Object> form;
    public String name;
    public Double price;
    public OrderStatusView orderStatus;

    public LocalDateTime orderDate;
    public LocalDateTime updatedTime;
    public String updatedBy;
    public String createdBy;

    public String policyCode;
    public String policyAddress;
}
