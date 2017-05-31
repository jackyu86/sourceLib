package com.caej.product.domain;

import io.sited.db.Field;

import java.util.List;

/**
 * @author chi
 */
public class ProductFormGroup {
    @Field(name = "name")
    public String name;
    @Field(name = "display_order")
    public Integer displayOrder;
    @Field(name = "optional")
    public Boolean optional = false;
    @Field(name = "multiple")
    public Boolean multiple = false;
    @Field(name = "fields")
    public List<ProductFormField> fields;
}
