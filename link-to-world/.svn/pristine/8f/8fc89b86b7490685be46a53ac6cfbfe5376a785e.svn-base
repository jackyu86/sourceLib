package com.caej.product.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "product_subject")
public class ProductSubject {
    @Id
    public ObjectId id;
    @Field(name = "product_id")
    public String productId;
    @Field(name = "insurance_subject_id")
    public ObjectId insuranceSubjectId;
    @Field(name = "max")
    public Integer max;
    @Field(name = "min")
    public Integer min;
    @Field(name = "required")
    public Boolean required;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_by")
    public String updatedBy;
}
