package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "insurance_area")
public class InsuranceArea {
    @Id
    public ObjectId id;
    @Field(name = "area_code")
    public String areaCode;
    @Field(name = "display_name")
    public String displayName;
    @Field(name = "display_order")
    public Integer displayOrder;
    @Field(name = "english_name")
    public String englishName;
    @Field(name = "pin_yin")
    public String pinYin;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
