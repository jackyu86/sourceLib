package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "insurance_job")
public class InsuranceJob {
    @Id
    public ObjectId id;
    @Field(name = "job_tree_id")
    public ObjectId jobTreeId;
    @Field(name = "parent_id")
    public ObjectId parentId;
    @Field(name = "display_name")
    public String displayName;
    @Field(name = "code")
    public String code;
    @Field(name = "risk_level")
    public Integer riskLevel;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
