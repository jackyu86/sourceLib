package com.caej.site.product.web.ajax.form;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chi
 */
public class ProductFormGroupAJAXView {
    public Boolean optional;
    public Boolean multi;
    public ObjectId insuranceFormGroupId;
    public List<ProductFormFieldAJAXView> fields;
}
