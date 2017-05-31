package com.caej.product.service;


import java.util.List;
import java.util.Map;

import com.caej.product.domain.Product;
import com.caej.product.domain.ProductFormField;
import com.caej.product.domain.ProductFormGroup;

/**
 * @author chi
 */
public class FormContext {
    public final String name;
    public final Product product;
    public final List<ProductFormGroup> productFormGroups;
    public final Map<String, Object> value;
    public final boolean readOnly;
    public final String dealerId;

    public FormContext(Product product, Map<String, Object> value, List<ProductFormGroup> productFormGroups, boolean readOnly, String name, String dealerId) {
        this.product = product;
        this.productFormGroups = productFormGroups;
        this.value = value;
        this.readOnly = readOnly;
        this.name = name;
        this.dealerId = dealerId;
    }

    public ProductFormGroup group(String groupName) {
        for (ProductFormGroup productFormGroup : productFormGroups) {
            if (productFormGroup.name.equals(groupName)) {
                return productFormGroup;
            }
        }
        return null;
    }

    public ProductFormField field(String groupName, String fieldName) {
        for (ProductFormGroup productFormGroup : productFormGroups) {
            if (productFormGroup.name.equals(groupName)) {
                for (ProductFormField field : productFormGroup.fields) {
                    if (field.name.equals(fieldName)) {
                        return field;
                    }
                }
            }
        }
        return null;
    }
}
