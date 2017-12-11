package com.caej.product.service.field.provider;

import java.util.List;
import java.util.Map;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.product.domain.ProductFormField;
import com.caej.product.service.FormContext;
import com.caej.product.service.client.EnumGenderTypeWebServiceClient;
import com.google.common.collect.Lists;

import io.sited.form.EnumConstant;
import io.sited.form.FormConfig;
/**
 * @author chi
 */
public class GenderFieldProvider extends DefaultFieldProvider {
    private final EnumGenderTypeWebServiceClient enumGenderTypeWebServiceClient;

    public GenderFieldProvider(String groupName, String fieldName, FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService, EnumGenderTypeWebServiceClient enumGenderTypeWebServiceClient) {
        super(groupName, fieldName, formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
        this.enumGenderTypeWebServiceClient = enumGenderTypeWebServiceClient;
    }

    @Override
    protected Map<String, Object> options(FormContext form, InsuranceFormFieldResponse insuranceFormField, ProductFormField productFormField) {
        Map<String, Object> options = super.options(form, insuranceFormField, productFormField);
        if (!options.containsKey("constants")) {
            List<EnumConstant> list = Lists.newArrayList();
            enumGenderTypeWebServiceClient.findAll().list.forEach(type -> {
                EnumConstant enumConstant = new EnumConstant();
                enumConstant.displayName = type.name;
                enumConstant.value = type.value;
                list.add(enumConstant);
            });
            options.put("constants", list);
        }
        return options;
    }
}
