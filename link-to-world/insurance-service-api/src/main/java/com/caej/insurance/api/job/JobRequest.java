package com.caej.insurance.api.job;

import org.bson.types.ObjectId;

/**
 * @author miller
 */
public class JobRequest {
    public ObjectId jobTreeId;
    public ObjectId parentId;
    public String displayName;
    public String code;
    public Integer riskLevel;
    public String requestBy;
}
