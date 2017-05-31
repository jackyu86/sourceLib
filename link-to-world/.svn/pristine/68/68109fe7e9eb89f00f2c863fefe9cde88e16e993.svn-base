package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "insurance_form_group")
public class InsuranceFormGroup {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "display_name")
    public String displayName;
    @Field(name = "display_order")
    public Integer displayOrder;
    @Field(name = "required")
    public Boolean required;
    @Field(name = "multiple")
    public Boolean multiple;
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
