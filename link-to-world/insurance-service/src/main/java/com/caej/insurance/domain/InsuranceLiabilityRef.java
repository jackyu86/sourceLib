package com.caej.insurance.domain;

import io.sited.db.Field;
import org.bson.types.ObjectId;

/**
 * @author chi
 */
public class InsuranceLiabilityRef {
    @Field(name = "insurance_liability_id")
    public ObjectId insuranceLiabilityId;
    @Field(name = "amount")
    public InsuranceAmount amount;
    @Field(name = "description")
    public String description;
}
