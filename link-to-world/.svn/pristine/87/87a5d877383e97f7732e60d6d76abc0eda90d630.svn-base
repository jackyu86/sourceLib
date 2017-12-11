package com.caej.product.service.group;

import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceAmountType;
import com.caej.product.domain.ProductFormField;
import com.caej.product.domain.ProductFormGroup;
import com.caej.product.service.FormContext;
import com.caej.product.service.FormGroupProvider;
import com.caej.product.service.ProductInsuranceLiabilityService;
import com.caej.product.service.ProductLiabilityHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldGroup;
import io.sited.form.FormConfig;

/**
 * @author chi
 */
public class ProductLiabilityFormGroupProvider extends FormGroupProvider {
    private final FormConfig formConfig;
    private final ProductInsuranceLiabilityService productInsuranceLiabilityService;
    private final Map<String, Integer> priorityMap;

    public ProductLiabilityFormGroupProvider(String groupName, FormConfig formConfig, ProductInsuranceLiabilityService productInsuranceLiabilityService) {
        super(groupName, "保险责任");
        this.formConfig = formConfig;
        this.productInsuranceLiabilityService = productInsuranceLiabilityService;
        this.priorityMap = Maps.newHashMap();
    }

    @Override
    public FieldGroup get(FormContext context) {
        ProductFormGroup group = context.group(name());
        if (group == null) {
            return null;
        }
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = name();
        fieldGroup.displayName = displayName();
        fieldGroup.fields = Lists.newArrayList();
        for (ProductFormField productFormField : group.fields) {
            Optional<Field> field = field(productFormField.name, context);
            if (field.isPresent()) {
                fieldGroup.fields.add(field.get());
            }
        }
        fieldGroup.fields.sort((field1, field2) -> priorityMap.get(field1.name).compareTo(priorityMap.get(field2.name)));
        return fieldGroup;
    }

    private Field getField(ProductLiabilityHelper.ProductLiability productInsuranceLiability, FormContext formContext) {
        Field field = new Field();
        field.type = formConfig.type("Liability").orElseThrow(() -> new StandardException("missing type Feature"));
        field.name = productInsuranceLiability.name;
        field.displayName = productInsuranceLiability.displayName;
        field.editable = !formContext.readOnly && productInsuranceLiability.amount.type != InsuranceAmountType.FIXED;
        field.multiple = false;
        field.groupName = name();
        field.options = Maps.newHashMap();
        field.options.put("required", true);
        field.options.put("type", productInsuranceLiability.amount.type);
        if (productInsuranceLiability.amount.type == InsuranceAmountType.FIXED) {
            field.options.put("fixValue", productInsuranceLiability.amount.fixedValue);
            field.defaultValue = productInsuranceLiability.amount.fixedValue;
        } else if (productInsuranceLiability.amount.type == InsuranceAmountType.USER_SELECTION) {
            field.options.put("selections", productInsuranceLiability.amount.selections);
            field.defaultValue = productInsuranceLiability.amount.selections.get(0);
        } else if (productInsuranceLiability.amount.type == InsuranceAmountType.USER_INPUT) {
            field.defaultValue = productInsuranceLiability.amount.inputMin;
        } else {
            throw new StandardException("not support insurance amount, type={}", productInsuranceLiability.amount.type);
        }
        return field;
    }

    @Override
    public Optional<Field> field(String fieldName, FormContext formContext) {
        ProductLiabilityHelper helper = productInsuranceLiabilityService.helper(formContext.product);
        ProductLiabilityHelper.ProductLiability productInsuranceLiability = helper.productLiability(new ObjectId(fieldName));
        priorityMap.putIfAbsent(fieldName, productInsuranceLiability.priority);
        return Optional.of(getField(productInsuranceLiability, formContext));
    }

    public Integer getPriority(String fieldName) {
        return priorityMap.get(fieldName);
    }
}
