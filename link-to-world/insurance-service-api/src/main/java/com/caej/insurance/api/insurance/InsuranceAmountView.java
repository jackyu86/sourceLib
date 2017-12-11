package com.caej.insurance.api.insurance;

import io.sited.db.Field;

import java.util.List;

/**
 * 保险金额
 *
 * @author chi
 */
public class InsuranceAmountView {
    @Field(name = "type")
    public InsuranceAmountType type;
    @Field(name = "input_max")
    public Integer inputMax;
    @Field(name = "input_min")
    public Integer inputMin;
    @Field(name = "input_increment_unit")
    public Integer inputIncrementUnit;

    @Field(name = "selections")
    public List<Integer> selections;

    @Field(name = "min_units")
    public Integer minUnits;
    @Field(name = "max_units")
    public Integer maxUnits;

    @Field(name = "formula_name")
    public String formulaName;

    @Field(name = "fixed_value")
    public String fixedValue;
}
