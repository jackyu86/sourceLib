package com.caej.product.service.group;

import java.util.Optional;

import com.caej.product.service.FieldProvider;
import com.caej.product.service.FormContext;
import com.caej.product.service.FormGroupProvider;
import com.google.common.collect.Lists;

import io.sited.form.Field;
import io.sited.form.FieldGroup;

/**
 * @author chi
 */
public class ProductFormGroupProvider extends FormGroupProvider {
    public ProductFormGroupProvider() {
        super("product", "产品");
    }

    @Override
    public FieldGroup get(FormContext context) {
        FieldGroup fieldGroup = new FieldGroup();
        fieldGroup.name = name();
        fieldGroup.optional = false;
        fieldGroup.fields = Lists.newArrayList();

        for (FieldProvider fieldProvider : providers()) {
            Optional<Field> fieldOptional = fieldProvider.get(context);
            if (fieldOptional.isPresent()) {
                fieldGroup.fields.add(fieldOptional.get());
            }
        }
        return fieldGroup;
    }

}
