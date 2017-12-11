package com.caej.product.domain;

import io.sited.db.Field;

/**
 * @author miller
 */
public class ProductAgreeUrl {
    @Field(name = "name")
    public String name;
    @Field(name = "url")
    public String url;
}
