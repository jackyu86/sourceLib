package com.caej.product.domain;

import io.sited.db.Field;

import java.util.Map;

/**
 * @author chi
 */
public class ProductFormField {
    @Field(name = "name")
    public String name;
    @Field(name = "display_order")
    public Integer displayOrder;
    @Field(name = "options")
    public Map<String, Object> options;
    @Field(name = "default_value")
    public String defaultValue;
    @Field(name = "multiple")
    public Boolean multiple;
    @Field(name = "editable")
    public Boolean editable;
    @Field(name = "display_as")
    public String displayAs;
}
