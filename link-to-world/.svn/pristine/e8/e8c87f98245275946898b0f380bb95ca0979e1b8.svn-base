package com.caej.product.domain;

import java.util.List;

import org.bson.types.ObjectId;

import com.caej.insurance.domain.InsuranceLiabilityRef;

import io.sited.db.Field;

/**
 * @author chi
 */
public class ProductInsurance {
    @Field(name = "insurance_id")
    public ObjectId insuranceId;
    @Field(name = "optional")
    public Boolean optional;
    @Field(name = "liabilities")
    public List<InsuranceLiabilityRef> liabilities;
}
