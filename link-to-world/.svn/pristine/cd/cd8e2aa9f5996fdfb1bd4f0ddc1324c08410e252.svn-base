package com.caej.insurance.domain;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceLiabilityType;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * 保险责任
 *
 * @author chi
 */
@Entity(name = "insurance_liability")
public class InsuranceLiability {
    @Id
    public ObjectId id;
    @Field(name = "group_id")
    public ObjectId groupId;
    @Field(name = "name")
    public String name;
    @Field(name = "description")
    public String description;
    @Field(name = "priority")
    public Integer priority;
    @Field(name = "type")
    public InsuranceLiabilityType type;
    @Field(name = "live_benefit")
    public InsuranceLiveBenefit liveBenefit;
    @Field(name = "risk_protection")
    public InsuranceRiskProtection riskProtection;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
