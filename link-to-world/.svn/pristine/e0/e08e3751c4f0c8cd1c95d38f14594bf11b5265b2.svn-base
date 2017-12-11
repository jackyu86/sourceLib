package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author chi
 */
@Entity(name = "insurance_form_field")
public class InsuranceFormField {
    @Id
    public ObjectId id;
    @Field(name = "group_id")
    public ObjectId groupId;
    @Field(name = "name")
    public String name;
    @Field(name = "display_name")
    public String displayName;
    @Field(name = "display_order")
    public Integer displayOrder;
    @Field(name = "type")
    public String type;
    @Field(name = "options")
    public Map<String, Object> options;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "display_as")
    public String displayAs;
}
