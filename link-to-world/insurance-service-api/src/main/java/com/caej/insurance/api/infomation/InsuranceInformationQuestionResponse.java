package com.caej.insurance.api.infomation;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * TODO: needs dynamic form support
 *
 * @author chi
 */
public class InsuranceInformationQuestionResponse {
    public ObjectId id;
    public String name;
    public String question;
    public String type;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
