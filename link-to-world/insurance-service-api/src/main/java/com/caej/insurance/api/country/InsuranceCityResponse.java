package com.caej.insurance.api.country;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class InsuranceCityResponse {
    public ObjectId id;
    public ObjectId provinceId;
    public String name;
    public String cityCode;
    public String zipCode;
    public LocalDateTime createdTime;
    public String createdBy;
}
