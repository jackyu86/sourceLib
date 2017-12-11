package com.caej.product.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.caej.product.api.product.ProductFormFieldDisplayView;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductFormGroup;
import com.caej.product.service.group.ProductLiabilityFormGroupProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldGroup;
import io.sited.form.Form;

/**
 * @author chi
 */
public class ProductFormService {
    private final Map<String, FormGroupProvider> providers = Maps.newHashMap();

    public void register(FormGroupProvider provider) {
        String name = provider.name();
        if (name == null || providers.containsKey(name)) {
            throw new StandardException("duplicate group provider, name={}", name);
        }
        providers.put(name, provider);
    }

    public Form form(Product product, Map<String, Object> value, boolean readOnly, String formContextName) {
        List<ProductFormGroup> productFormGroups = product.formGroups;
        FormContext context = new FormContext(product, value, productFormGroups, readOnly, formContextName, null);

        List<FieldGroup> groups = Lists.newArrayList();

        for (ProductFormGroup productFormGroup : context.productFormGroups) {
            if (!"liability".equals(productFormGroup.name) && !"product".equals(productFormGroup.name)) {
                FormGroupProvider provider = provider(productFormGroup.name);
                FieldGroup fieldGroup = provider.get(context);
                if (fieldGroup != null) {
                    fieldGroup.optional = productFormGroup.optional;
                    groups.add(fieldGroup);
                }
            }
        }
        FieldGroup featureGroup = provider("liability").get(context);
        if (featureGroup != null) {
            groups.add(featureGroup);
        }

        FieldGroup productGroup = provider("product").get(context);
        if (productGroup != null) {
            groups.add(productGroup);
        }
        return new Form(groups, value, Collections.singletonMap(FormContext.class, context));
    }

    public FormGroupProvider provider(String name) {
        if (!providers.containsKey(name)) {
            throw new StandardException("missing field group provider, name={}", name);
        }
        return providers.get(name);
    }

    public Form form(Product product, Map<String, Object> value, List<ProductFormFieldDisplayView> fields, String formContextName, String dealerId) {
        List<ProductFormGroup> productFormGroups = product.formGroups;
        FormContext context = new FormContext(product, value, productFormGroups, false, formContextName, dealerId);
        return form(value, fields, context);
    }

    private Form form(Map<String, Object> value, List<ProductFormFieldDisplayView> fields, FormContext context) {
        Map<String, FieldGroup> groups = Maps.newLinkedHashMap();

        fields.forEach(productFormFieldDisplayView -> {
            FormGroupProvider provider = provider(productFormFieldDisplayView.group);
            Optional<Field> field = provider.field(productFormFieldDisplayView.field, context);
            if (field.isPresent()) {
                FieldGroup fieldGroup = groups.get(productFormFieldDisplayView.group);
                if (fieldGroup == null) {
                    fieldGroup = new FieldGroup();
                    ProductFormGroup productFormGroup = context.group(productFormFieldDisplayView.group);
                    fieldGroup.optional = productFormGroup == null ? false : productFormGroup.optional;
                    fieldGroup.multiple = productFormGroup == null ? false : productFormGroup.multiple;
                    fieldGroup.name = productFormFieldDisplayView.group;
                    fieldGroup.fields = Lists.newArrayList();
                    groups.put(productFormFieldDisplayView.group, fieldGroup);
                }
                fieldGroup.fields.add(field.get());
            }
        });
        if (groups.get("liability") != null) {
            ProductLiabilityFormGroupProvider provider = (ProductLiabilityFormGroupProvider) provider("liability");
            groups.get("liability").fields.sort((field1, field2) -> provider.getPriority(field1.name).compareTo(provider.getPriority(field2.name)));
        }
        return new Form(Lists.newArrayList(groups.values()), value, Collections.singletonMap(FormContext.class, context));
    }

    public Form form(Product product, Map<String, Object> value, List<ProductFormFieldDisplayView> fields, String formContextName) {
        List<ProductFormGroup> productFormGroups = product.formGroups;
        FormContext context = new FormContext(product, value, productFormGroups, false, formContextName, null);
        return form(value, fields, context);
    }
}
