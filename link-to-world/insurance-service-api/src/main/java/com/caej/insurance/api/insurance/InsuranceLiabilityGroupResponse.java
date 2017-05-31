package com.caej.insurance.api.insurance;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * 保险责任分组
 *
 * @author chi
 */
public class InsuranceLiabilityGroupResponse {
    public ObjectId id;
    public String name;
    public Integer priority;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
