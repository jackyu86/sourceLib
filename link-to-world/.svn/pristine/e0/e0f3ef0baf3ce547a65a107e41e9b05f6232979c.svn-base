package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * 保险责任分组
 *
 * @author chi
 */
@Entity(name = "insurance_liability_group")
public class InsuranceLiabilityGroup {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "priority")
    public Integer priority;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
