package com.caej.product.service.field.provider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.insurance.api.job.JobResponse;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductFormField;
import com.caej.product.service.FormContext;
import com.caej.product.service.client.InsuranceJobWebServiceClient;
import com.google.common.collect.Lists;

import io.sited.StandardException;
import io.sited.form.EnumConstant;
import io.sited.form.Field;
import io.sited.form.FieldType;
import io.sited.form.FormConfig;

/**
 * @author miller
 */
public class JobFieldProvider extends DefaultFieldProvider {
    private final InsuranceJobWebServiceClient insuranceJobWebService;

    public JobFieldProvider(String groupName, String fieldName, FormConfig formConfig, InsuranceFormGroupWebService insuranceFormGroupWebService, InsuranceFormFieldWebService insuranceFormFieldWebService, InsuranceJobWebServiceClient insuranceJobWebService) {
        super(groupName, fieldName, formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService);
        this.insuranceJobWebService = insuranceJobWebService;
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
        field.displayAs = "pdp".equals(context.name) || "plp".equals(context.name) ?
            "text" : productFormField.displayAs != null ? productFormField.displayAs : insuranceFormField.displayAs;
        return Optional.of(field);
    }


    @Override
    protected Map<String, Object> options(FormContext context, InsuranceFormFieldResponse insuranceFormField, ProductFormField productFormField) {
        Map<String, Object> options = super.options(context, insuranceFormField, productFormField);
        Product product = context.product;
        List<JobResponse> jobs = insuranceJobWebService.firstLevel(product.insuranceJobTreeId.toHexString()).list;
        List<EnumConstant> constants = Lists.newArrayList();
        for (JobResponse job : jobs) {
            EnumConstant enumConstant = new EnumConstant();
            enumConstant.displayName = job.displayName;
            enumConstant.value = job.code;
            constants.add(enumConstant);
        }
        options.put("jobTreeId", product.insuranceJobTreeId);
        options.put("constants", constants);
        options.put("restrict", product.jobRestricted);
        options.put("allowedJobIds", product.jobIds);
        options.put("minJobLevel", product.minJobLevel);
        options.put("maxJobLevel", product.maxJobLevel);
        return options;
    }
}
