package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "insurance_claim")
public class InsuranceClaim {
    @Id
    public ObjectId id;
    @Field(name = "title")
    public String title;
    @Field(name = "name")
    public String name;
    @Field(name = "content")
    public String content;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
