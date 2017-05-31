package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "insurance_city")
public class InsuranceCity {
    @Id
    public ObjectId id;
    @Field(name = "province_id")
    public ObjectId provinceId;
    @Field(name = "name")
    public String name;
    @Field(name = "city_code")
    public String cityCode;
    @Field(name = "zip_code")
    public String zipCode;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
