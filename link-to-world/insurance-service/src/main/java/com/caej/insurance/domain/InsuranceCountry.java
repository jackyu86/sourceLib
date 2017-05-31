package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "insurance_country")
public class InsuranceCountry {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "country_code")
    public String countryCode;
    @Field(name = "flag_image_url")
    public String flagImageURL;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
