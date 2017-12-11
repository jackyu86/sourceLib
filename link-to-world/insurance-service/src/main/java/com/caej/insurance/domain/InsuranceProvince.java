package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "insurance_province")
public class InsuranceProvince {
    @Id
    public ObjectId id;
    @Field(name = "country_id")
    public ObjectId countryId;
    @Field(name = "name")
    public String name;
    @Field(name = "short_name")
    public String shortName;
    @Field(name = "province_code")
    public String provinceCode;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
