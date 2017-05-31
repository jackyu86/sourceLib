package com.caej.insurance.api.clause;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import com.caej.insurance.api.insurance.InsuranceClauseType;

/**
 * @author chi
 */
public class InsuranceClauseResponse {
    public ObjectId id;
    public String serialNumber;
    public String name;
    public InsuranceClauseType type;
    public String contentURL;
    public String exclusionsUrl;
    public String wordURL;
    public String pdfURL;
    public LocalDateTime createdTime;
    public String createdBy;
}
