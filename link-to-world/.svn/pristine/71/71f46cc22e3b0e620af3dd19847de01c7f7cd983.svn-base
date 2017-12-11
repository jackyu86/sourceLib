package com.caej.product.service.field.provider;

import java.util.List;
import java.util.Optional;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.insurance.api.insurance.InsurancePaymentMethod;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.domain.ProductFormField;
import com.caej.product.domain.ProductPayment;
import com.caej.product.service.FormContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldType;
import io.sited.form.FormConfig;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class PlanPaymentFieldProvider extends DefaultFieldProvider {
    public PlanPaymentFieldProvider(FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService) {
        super("plan", "payment", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
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
        field.options = Maps.newHashMap();

        List<InsurancePeriod> periods = Lists.newArrayList();
        ProductPayment productPayment = context.product.payment;
        if (productPayment.fixedPeriods != null) {
            periods.addAll(productPayment.fixedPeriods);
        }
        if (productPayment.irregularPeriods != null) {
            periods.addAll(productPayment.irregularPeriods);
        }
        if (productPayment.agePeriods != null) {
            periods.addAll(productPayment.agePeriods);
        }
        if (productPayment.yearPeriods != null) {
            periods.addAll(productPayment.yearPeriods);
        }
        field.options.put("periods", periods);

        Optional<FieldType<Object>> type = formConfig.type(insuranceFormField.type);
        if (!type.isPresent())

        {
            throw new StandardException("missing field type, type={}", insuranceFormField.type);
        }

        field.hidden =

            isFixedPayment(context.product.payment);

        field.type = type.get();
        field.displayName = insuranceFormField.displayName;
        field.multiple = productFormField.multiple;
        field.defaultValue = productFormField.defaultValue == null ? null : JSON.fromJSON(productFormField.defaultValue, type.get().

            javaType());
        field.editable = context.readOnly ? false : productFormField.editable;
        field.displayAs = productFormField.displayAs != null ? productFormField.displayAs : insuranceFormField.displayAs;
        return Optional.of(field);
    }

    private boolean isFixedPayment(ProductPayment productPayment) {
        return productPayment.methods.size() == 1 && productPayment.methods.get(0).equals(InsurancePaymentMethod.FIXED);
    }
}
