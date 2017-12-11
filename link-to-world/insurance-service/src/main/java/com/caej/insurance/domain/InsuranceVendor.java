package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "insurance_vendor")
public class InsuranceVendor {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "level")
    public InsuranceVendorLevel level;
    @Field(name = "vendor_code")
    public String vendorCode;
    @Field(name = "short_name")
    public String shortName;
    @Field(name = "description")
    public String description;
    @Field(name = "image_url")
    public String imageURL;
    @Field(name = "licence_image_url")
    public String licenceImageURL;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
