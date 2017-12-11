package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "insurance_declaration")
public class InsuranceDeclaration {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "question")
    public String question;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}

