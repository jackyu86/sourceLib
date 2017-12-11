package com.caej.insurance.api.insurance;

import org.bson.types.ObjectId;

/**
 * 保险责任
 *
 * @author chi
 */
public class InsuranceLiabilityRefView {
    public ObjectId insuranceLiabilityId;
    public InsuranceAmountView amount;
    public String description;
}
