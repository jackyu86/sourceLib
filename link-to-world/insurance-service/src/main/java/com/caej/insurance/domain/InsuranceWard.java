package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "insurance_ward")
public class InsuranceWard {
    @Id
    public ObjectId id;
    @Field(name = "level")
    public InsuranceVendorLevel level;
    @Field(name = "city_id")
    public ObjectId cityId;
    @Field(name = "name")
    public String name;
    @Field(name = "ward_code")
    public String wardCode;
    @Field(name = "zip_code")
    public String zipCode;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
