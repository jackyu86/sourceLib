package com.caej.product.service.field.provider;

import java.util.Optional;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.product.api.product.ProductPeriodStartTimeType;
import com.caej.product.domain.ProductFormField;
import com.caej.product.service.FormContext;

import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldType;
import io.sited.form.FormConfig;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class PlanStartTimeFieldProvider extends DefaultFieldProvider {
    public PlanStartTimeFieldProvider(FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService) {
        super("plan", "startTime", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
    }

    @Override
    public Optional<Field> get(FormContext context) {
        InsuranceFormGroupResponse insuranceFormGroup = insuranceFormGroupWebService.getByName(groupName);
        InsuranceFormFieldResponse insuranceFormField = insuranceFormFieldWebService.findByName(groupName, fieldName);
        ProductFormField productFormField = context.field(groupName, fieldName);

        Field field = new Field();
        field.name = insuranceFormField.name;
        field.groupName = insuranceFormGroup.name;
        field.options = options(context, insuranceFormField, productFormField);

        ProductPeriodStartTimeType startTimeType = context.product.period.startTimeType;
        field.options.put("type", startTimeType);

        Optional<FieldType<Object>> type = formConfig.type(insuranceFormField.type);
        if (!type.isPresent()) {
            throw new StandardException("missing field type, type={}", insuranceFormField.type);
        }
        field.type = type.get();


        field.displayName = insuranceFormField.displayName;
        field.multiple = productFormField.multiple;
        field.defaultValue = productFormField.defaultValue == null ? null : JSON.fromJSON(productFormField.defaultValue, type.get().javaType());
        field.editable = context.readOnly ? false : productFormField.editable;
        return Optional.of(field);
    }
}
