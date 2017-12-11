package com.caej.product.service.field.provider;

import java.util.Map;
import java.util.Optional;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.product.domain.ProductFormField;
import com.caej.product.service.FieldProvider;
import com.caej.product.service.FormContext;
import com.google.common.collect.Maps;

import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldType;
import io.sited.form.FormConfig;

/**
 * @author chi
 */
public class DefaultFieldProvider implements FieldProvider {
    protected final String groupName;
    protected final String fieldName;
    protected final FormConfig formConfig;
    protected final InsuranceFormGroupWebService insuranceFormGroupWebService;
    protected final InsuranceFormFieldWebService insuranceFormFieldWebService;

    public DefaultFieldProvider(String groupName, String fieldName, FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService) {
        this.groupName = groupName;
        this.fieldName = fieldName;
        this.formConfig = formConfig;
        this.insuranceFormGroupWebService = insuranceFormGroupWebService;
        this.insuranceFormFieldWebService = insuranceFormFieldWebService;
    }

    public String name() {
        return fieldName;
    }

    @Override
    public Optional<Field> get(FormContext context) {
        InsuranceFormGroupResponse insuranceFormGroup = insuranceFormGroupWebService.getByName(groupName);
        InsuranceFormFieldResponse insuranceFormField = insuranceFormFieldWebService.findByName(groupName, fieldName);
        ProductFormField productFormField = context.field(groupName, fieldName);
        if (productFormField == null) return Optional.empty();

        Field field = new Field();
        field.name = insuranceFormField.name;
        field.groupName = insuranceFormGroup.name;

        field.options = options(context, insuranceFormField, productFormField);

        Optional<FieldType<Object>> type = formConfig.type(insuranceFormField.type);
        if (!type.isPresent()) {
            throw new StandardException("missing field type, type={}", insuranceFormField.type);
        }
        FieldType<?> fieldType = type.get();
        field.type = fieldType;
        field.displayName = insuranceFormField.displayName;
        field.multiple = productFormField.multiple;
        field.defaultValue = productFormField.defaultValue == null ? null : fieldType.normalize(productFormField.defaultValue);
        field.editable = context.readOnly ? false : productFormField.editable;
        field.displayAs = productFormField.displayAs != null ? productFormField.displayAs : insuranceFormField.displayAs;
        return Optional.of(field);
    }

    protected Map<String, Object> options(FormContext form, InsuranceFormFieldResponse insuranceFormField, ProductFormField productFormField) {
        Map<String, Object> options = Maps.newHashMap();

        if (insuranceFormField.options != null) {
            options.putAll(insuranceFormField.options);
        }

        if (productFormField.options != null) {
            options.putAll(productFormField.options);
        }
        return options;
    }
}
