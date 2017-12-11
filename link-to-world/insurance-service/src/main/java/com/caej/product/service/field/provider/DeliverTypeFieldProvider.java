package com.caej.product.service.field.provider;

import java.util.List;
import java.util.Map;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.product.domain.ProductFormField;
import com.caej.product.service.FormContext;
import com.caej.product.service.client.EnumDeliverTypeWebServiceClient;
import com.google.common.collect.Lists;

import io.sited.form.EnumConstant;
import io.sited.form.FormConfig;

/**
 * @author miller
 */
public class DeliverTypeFieldProvider extends DefaultFieldProvider {
    private final EnumDeliverTypeWebServiceClient enumDeliverTypeWebService;

    public DeliverTypeFieldProvider(String groupName, String fieldName, FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService, EnumDeliverTypeWebServiceClient enumDeliverTypeWebService) {
        super(groupName, fieldName, formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
        this.enumDeliverTypeWebService = enumDeliverTypeWebService;
    }

    @Override
    protected Map<String, Object> options(FormContext form, InsuranceFormFieldResponse insuranceFormField, ProductFormField productFormField) {
        Map<String, Object> options = super.options(form, insuranceFormField, productFormField);
        if (!options.containsKey("constants")) {
            List<EnumConstant> list = Lists.newArrayList();
            enumDeliverTypeWebService.findAll().list.forEach(type -> {
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
