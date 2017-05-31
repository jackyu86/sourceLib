package com.caej.insurance.api.job;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class JobResponse {
    public ObjectId id;
    public ObjectId jobTreeId;
    public ObjectId parentId;
    public String displayName;
    public String code;
    public Integer riskLevel;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
