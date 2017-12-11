package com.caej.order.domain;

import java.time.LocalDateTime;

import com.caej.order.order.OrderStatusView;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author chi
 */
@Entity(name = "order_item")
public class OrderItem {
    @Id(autoGenerated = false)
    public String id;
    @Field(name = "order_id")
    public String orderId;
    @Field(name = "form")
    public String form;
    @Field(name = "name")
    public String name;
    @Field(name = "price")
    public Double price;
    @Field(name = "order_status")
    public OrderStatusView orderStatus;

    @Field(name = "order_date")
    public LocalDateTime orderDate;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "created_by")
    public String createdBy;

    @Field(name = "policy_code")
    public String policyCode;
    @Field(name = "policy_address")
    public String policyAddress;
    @Field(name = "refund_id")
    public String refundId;
}
