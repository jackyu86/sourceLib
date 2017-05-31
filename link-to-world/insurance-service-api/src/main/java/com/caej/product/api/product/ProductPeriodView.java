package com.caej.product.api.product;


import java.util.List;

import com.caej.insurance.api.insurance.InsurancePeriodType;
import com.caej.insurance.api.insurance.InsurancePeriodUnit;
import com.caej.insurance.api.insurance.InsurancePeriodView;

/**
 * @author chi
 */
public class ProductPeriodView {
    public InsurancePeriodType type;
    public InsurancePeriodUnit inputUnit;
    public InsurancePeriodView inputMax;
    public InsurancePeriodView inputMin;
    public List<InsurancePeriodView> selections;
    public List<InsurancePeriodView> ages;
    public InsurancePeriodView fixedValue;
    public ProductPeriodStartTimeType startTimeType;
    public InsurancePeriodView earliestInsuranceTime;
}
