package com.caej.product.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.caej.product.domain.ProductFormField;
import com.caej.product.domain.ProductFormGroup;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.form.Field;
import io.sited.form.FieldGroup;
import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class FormGroupProvider {
    private final String name;
    private final String displayName;
    private final Map<String, FieldProvider> providers = Maps.newHashMap();

    public FormGroupProvider(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public FormGroupProvider register(FieldProvider provider) {
        providers.put(provider.name(), provider);
        return this;
    }

    public FieldGroup get(FormContext context) {
        ProductFormGroup group = context.group(name);

        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = name();
        fieldGroup.displayName = displayName();
        fieldGroup.optional = group.optional;
        fieldGroup.multiple = group.multiple;
        fieldGroup.fields = Lists.newArrayList();

        for (ProductFormField field : group.fields) {
            Optional<Field> fieldOptional = field(field.name, context);
            if (fieldOptional.isPresent()) {
                fieldGroup.fields.add(fieldOptional.get());
            }
        }
        return fieldGroup;
    }

    public String name() {
        return name;
    }

    public String displayName() {
        return displayName;
    }

    public Optional<Field> field(String field, FormContext formContext) {
        FieldProvider provider = provider(field);
        if (provider == null) {
            throw new NotFoundException("missing provider, group={}, field={}", name(), field);
        }
        return provider.get(formContext);
    }

    protected FieldProvider provider(String fieldName) {
        return providers.get(fieldName);
    }

    protected List<FieldProvider> providers() {
        return Lists.newArrayList(providers.values());
    }
}
