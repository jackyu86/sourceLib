package com.caej.product.domain;

import java.util.List;

import com.caej.insurance.api.insurance.InsurancePeriodType;
import com.caej.insurance.api.insurance.InsurancePeriodUnit;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.api.product.ProductPeriodStartTimeType;

import io.sited.db.Field;

/**
 * @author chi
 */
public class ProductPeriod {
    @Field(name = "type")
    public InsurancePeriodType type;
    @Field(name = "input_unit")
    public InsurancePeriodUnit inputUnit;
    @Field(name = "input_max")
    public InsurancePeriod inputMax;
    @Field(name = "input_min")
    public InsurancePeriod inputMin;
    @Field(name = "selections")
    public List<InsurancePeriod> selections;
    @Field(name = "ages")
    public List<InsurancePeriod> ages;
    @Field(name = "fixed_value")
    public InsurancePeriod fixedValue;
    @Field(name = "start_time_type")
    public ProductPeriodStartTimeType startTimeType;
    @Field(name = "earliest_insurance_time")
    public InsurancePeriod earliestInsuranceTime;
}
