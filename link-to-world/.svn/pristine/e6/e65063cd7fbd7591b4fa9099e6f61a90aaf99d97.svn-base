package com.caej.order.domain;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.caej.order.archive.OrderArchiveRecordStatus;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author miller
 */
@Entity(name = "order_archive_record")
public class OrderArchiveRecord {
    @Id
    public ObjectId id;
    @Field(name = "order_id")
    public String orderId;
    @Field(name = "status")
    public OrderArchiveRecordStatus status;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "count")
    public Integer count = 0;
    @Field(name = "last_error")
    public String lastError;
}
