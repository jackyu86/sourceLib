package com.caej.product.service.field;

import com.caej.insurance.domain.InsurancePeriod;

import io.sited.form.type.AbstractFieldType;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class ProductPeriodFieldType extends AbstractFieldType<InsurancePeriod> {
    public ProductPeriodFieldType() {
        super(InsurancePeriod.class);
    }

    @Override
    public String name() {
        return "PlanPeriod";
    }

    @Override
    public InsurancePeriod normalize(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof InsurancePeriod) {
            return (InsurancePeriod) value;
        }
        if (value instanceof String) {
            return JSON.fromJSON((String) value, InsurancePeriod.class);
        }
        return JSON.fromJSON(JSON.toJSON(value), InsurancePeriod.class);
    }

    @Override
    public boolean validate(Object value, Context context) {
        return true;
    }
}
