package com.caej.product.service.price.engine;

import com.caej.product.service.price.InsurancePriceEngine;
import com.caej.product.service.price.InsurancePriceRequest;
import com.caej.product.service.price.InsurancePriceResponse;

import io.sited.form.FieldGroup;

/**
 * @author chi
 */
public class FixedInsurancePriceEngineImpl implements InsurancePriceEngine {
    @Override
    public InsurancePriceResponse calculate(InsurancePriceRequest request) {
        FieldGroup insuredListGroup = request.form.fieldGroup("insured");
        int count = 1;
        if (insuredListGroup.multiple) {
            count = request.form.listGroup("insured").size() == 0 ? 1 : request.form.listGroup("insured").size();
        }
        InsurancePriceResponse productInsurancePriceResponse = new InsurancePriceResponse();
        productInsurancePriceResponse.price = request.insurance.priceTable.fixedPrice * count;
        return productInsurancePriceResponse;
    }
}
