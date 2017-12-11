package com.caej.order.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "refund_tracking")
public class RefundTracking {
    @Id
    public ObjectId id;
    @Field(name = "order_id")
    public String oderId;
    @Field(name = "order_item_id")
    public String orderItemId;
    @Field(name = "refund_id")
    public String refundId;
    @Field(name = "request")
    public String request;
    @Field(name = "status")
    public String status;
    @Field(name = "errors")
    public String errors;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
}
