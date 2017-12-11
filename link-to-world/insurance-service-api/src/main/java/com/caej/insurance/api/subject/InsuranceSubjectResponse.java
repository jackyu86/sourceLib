package com.caej.insurance.api.subject;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class InsuranceSubjectResponse {
    public ObjectId id;
    public String name;
    public String description;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
