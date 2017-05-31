package com.caej.insurance.domain;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceClauseType;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author miller
 */
@Entity(name = "insurance_clause")
public class InsuranceClause {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "type")
    public InsuranceClauseType type;
    @Field(name = "serial_number")
    public String serialNumber;
    @Field(name = "content_url")
    public String contentURL;
    @Field(name = "exclusions_url")
    public String exclusionsUrl;
    @Field(name = "word_url")
    public String wordURL;
    @Field(name = "pdf_url")
    public String pdfURL;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
