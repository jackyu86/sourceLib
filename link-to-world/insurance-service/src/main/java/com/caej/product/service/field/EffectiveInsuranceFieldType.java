package com.caej.product.service.field;

import io.sited.form.type.AbstractFieldType;

/**
 * @author miller
 */
public class EffectiveInsuranceFieldType extends AbstractFieldType<EffectiveInsurance> {
    public EffectiveInsuranceFieldType() {
        super(EffectiveInsurance.class);
    }

    @Override
    public String name() {
        return "EffectiveInsurance";
    }
}
