package com.caej.insurance.api.area;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class InsuranceAreaResponse {
    public ObjectId id;
    public String areaCode;
    public String displayName;
    public Integer displayOrder;
    public String englishName;
    public String pinYin;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
