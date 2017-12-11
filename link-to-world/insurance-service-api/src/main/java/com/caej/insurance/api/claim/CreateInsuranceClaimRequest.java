package com.caej.insurance.api.claim;

import org.bson.types.ObjectId;

/**
 * @author miller
 */
public class CreateInsuranceClaimRequest {
    public ObjectId id;
    public String name;
    public String title;
    public String content;
    public String requestBy;
}
