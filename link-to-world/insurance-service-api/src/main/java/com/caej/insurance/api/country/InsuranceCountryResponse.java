package com.caej.insurance.api.country;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class InsuranceCountryResponse {
    public ObjectId id;
    public String name;
    public String countryCode;
    public String imageURL;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
