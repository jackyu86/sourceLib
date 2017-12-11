package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "insurance_category")
public class InsuranceCategory {
    @Id
    public ObjectId id;
    @Field(name = "parent_id")
    public ObjectId parentId;
    @Field(name = "name")
    public String name;
    @Field(name = "display_order")
    public Integer displayOrder;
    @Field(name = "recommended")
    public Boolean recommended;
    @Field(name = "description")
    public String description;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
