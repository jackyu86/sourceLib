package com.caej.product.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "product_amount")
public class ProductAmount {
    @Id
    public ObjectId id;

    @Field(name = "user_id")
    public ObjectId userId;

    @Field(name = "insurance_id")
    public ObjectId insuranceId;

    @Field(name = "product_id")
    public ObjectId productId;

    @Field(name = "amount")
    public Double amount;

    @Field(name = "created_time")
    public LocalDateTime createdTime;

    @Field(name = "created_by")
    public String createdBy;
}
