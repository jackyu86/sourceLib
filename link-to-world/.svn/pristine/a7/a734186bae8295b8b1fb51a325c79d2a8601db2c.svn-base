package com.caej.product.service.field.provider;

import java.util.List;
import java.util.Map;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.product.domain.ProductFormField;
import com.caej.product.service.FormContext;
import com.google.common.collect.Lists;

import io.sited.form.EnumConstant;
import io.sited.form.FormConfig;

/**
 * @author miller
 */
public class AgreeFieldProvider extends DefaultFieldProvider {
    public AgreeFieldProvider(String groupName, String fieldName, FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService) {
        super(groupName, fieldName, formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
    }

    @Override
    protected Map<String, Object> options(FormContext form, InsuranceFormFieldResponse insuranceFormField, ProductFormField productFormField) {
        Map<String, Object> options = super.options(form, insuranceFormField, productFormField);
        if (form.product.agreeUrls != null) {
            List<EnumConstant> list = Lists.newArrayList();
            form.product.agreeUrls.forEach(productAgreeUrl -> {
                EnumConstant enumConstant = new EnumConstant();
                enumConstant.displayName = productAgreeUrl.name;
                enumConstant.value = productAgreeUrl.url;
                list.add(enumConstant);
            });
            options.put("constants", list);
        }
        return options;
    }
}
