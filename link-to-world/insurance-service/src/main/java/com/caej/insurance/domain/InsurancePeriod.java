package com.caej.insurance.domain;

import com.caej.insurance.api.insurance.InsurancePeriodUnit;

import io.sited.db.Field;

/**
 * @author chi
 */
public class InsurancePeriod {
    @Field(name = "display_name")
    public String displayName;

    @Field(name = "value")
    public Integer value;

    @Field(name = "unit")
    public InsurancePeriodUnit unit;

    @Override
    public String toString() {
        return value + unit.name();
    }
}
