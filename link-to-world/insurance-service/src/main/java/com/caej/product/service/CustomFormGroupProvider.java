package com.caej.product.service;

import java.util.Map;
import java.util.Optional;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.product.domain.ProductFormField;
import com.caej.product.domain.ProductFormGroup;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldGroup;
import io.sited.form.FieldType;
import io.sited.form.FormConfig;

/**
 * @author chi
 */
public class CustomFormGroupProvider extends FormGroupProvider {
    private final InsuranceFormGroupWebService insuranceFormGroupWebService;
    private final InsuranceFormFieldWebService insuranceFormFieldWebService;
    private final FormConfig formConfig;

    public CustomFormGroupProvider(InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService, FormConfig formConfig) {
        super("custom", "自定义分组");
        this.insuranceFormGroupWebService = insuranceFormGroupWebService;
        this.insuranceFormFieldWebService = insuranceFormFieldWebService;
        this.formConfig = formConfig;
    }

    @Override
    public String name() {
        return "custom";
    }

    @Override
    public String displayName() {
        return insuranceFormGroupWebService.getByName(name()).displayName;
    }

    @Override
    public FieldGroup get(FormContext context) {
        ProductFormGroup group = context.group(name());

        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = name();
        fieldGroup.displayName = displayName();
        fieldGroup.optional = group.optional;
        fieldGroup.multiple = group.multiple;
        fieldGroup.fields = Lists.newArrayList();

        for (ProductFormField field : group.fields) {
            Optional<Field> fieldOptional = field(field.name, context);
            fieldOptional.ifPresent(field1 -> fieldGroup.fields.add(field1));
        }
        return fieldGroup;
    }

    @Override
    public Optional<Field> field(String fieldName, FormContext context) {
        InsuranceFormGroupResponse insuranceFormGroup = insuranceFormGroupWebService.getByName(name());
        InsuranceFormFieldResponse insuranceFormField = insuranceFormFieldWebService.findByName(name(), fieldName);
        ProductFormField productFormField = context.field(name(), fieldName);
        if (productFormField == null) return Optional.empty();

        Field field = new Field();
        field.name = insuranceFormField.name;
        field.groupName = insuranceFormGroup.name;
        field.options = options(insuranceFormField, productFormField);

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

    private Map<String, Object> options(InsuranceFormFieldResponse insuranceFormField, ProductFormField productFormField) {
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
