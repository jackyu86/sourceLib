package com.caej.product.service.field.provider;

import java.util.Objects;
import java.util.Optional;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.product.service.FormContext;
import com.google.common.collect.Maps;

import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldType;
import io.sited.form.FormConfig;

/**
 * @author miller
 */
public class UnitFieldProvider extends DefaultFieldProvider {
    public UnitFieldProvider(String groupName, String fieldName, FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService) {
        super(groupName, fieldName, formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
    }

    @Override
    public String name() {
        return "unit";
    }

    @Override
    public Optional<Field> get(FormContext context) {
        Optional<Field> optional = super.get(context);
        if (optional.isPresent()) {
            Field field = optional.get();
            if (field.defaultValue == null) field.defaultValue = 1;
            return Optional.of(field);
        }
        Field field = new Field();
        field.name = "unit";
        field.groupName = "product";
        field.options = Maps.newHashMap();
        Optional<FieldType<Object>> type = formConfig.type("Unit");
        if (!type.isPresent()) {
            throw new StandardException("missing field type, type=Unit");
        }
        field.type = type.get();
        field.displayName = "限购份数";
        field.multiple = false;
        field.defaultValue = 1;
        field.editable = !context.readOnly && !Objects.equals(context.product.minUnits, context.product.maxUnits);
        return Optional.of(field);
    }
}
