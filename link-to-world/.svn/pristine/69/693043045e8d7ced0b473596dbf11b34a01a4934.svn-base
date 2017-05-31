package com.caej.product.domain;

import java.util.List;

import com.caej.insurance.api.insurance.InsurancePaymentMethod;
import com.caej.insurance.domain.InsurancePeriod;

import io.sited.db.Field;

/**
 * @author chi
 */
public class ProductPayment {
    @Field(name = "methods")
    public List<InsurancePaymentMethod> methods;
    @Field(name = "fixed_periods")
    public List<InsurancePeriod> fixedPeriods;
    @Field(name = "irregular_periods")
    public List<InsurancePeriod> irregularPeriods;
    @Field(name = "year_periods")
    public List<InsurancePeriod> yearPeriods;
    @Field(name = "age_periods")
    public List<InsurancePeriod> agePeriods;
}
