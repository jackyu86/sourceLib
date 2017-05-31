package com.caej.product.service.field.provider;

import java.util.Map;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.insurance.InsurancePeriodType;
import com.caej.product.domain.ProductFormField;
import com.caej.product.domain.ProductPeriod;
import com.caej.product.service.FormContext;

import io.sited.form.FormConfig;

/**
 * @author chi
 */
public class PlanPeriodFieldProvider extends DefaultFieldProvider {
    public PlanPeriodFieldProvider(FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService) {
        super("plan", "period", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
    }

    @Override
    protected Map<String, Object> options(FormContext context, InsuranceFormFieldResponse insuranceFormField, ProductFormField field) {
        Map<String, Object> options = super.options(context, insuranceFormField, field);

        ProductPeriod productPeriod = context.product.period;
        options.put("type", productPeriod.type);

        if (productPeriod.type == InsurancePeriodType.AGE_DURATION) {
            options.put("ages", productPeriod.ages);
        } else if (productPeriod.type == InsurancePeriodType.USER_SELECTION) {
            options.put("selection", productPeriod.selections);
        } else if (productPeriod.type == InsurancePeriodType.FIXED) {
            options.put("fixedValue", productPeriod.fixedValue);
        }

        return options;
    }
}
