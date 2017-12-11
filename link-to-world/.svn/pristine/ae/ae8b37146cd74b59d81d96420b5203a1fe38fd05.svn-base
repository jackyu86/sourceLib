package com.caej.product.service.price.engine;

import io.sited.StandardException;

public class FieldName {
    public final String raw;
    public final String groupName;
    public final String fieldName;

    public FieldName(String name) {
        this.raw = name;
        int p = name.indexOf('.');
        if (p < 0 || p == name.length() - 1) {
            throw new StandardException("invalid field name, name={}", name);
        }
        groupName = name.substring(0, p);
        fieldName = name.substring(p + 1, name.length());
    }
}
