package com.caej.product.service.field;

import io.sited.form.type.AbstractFieldType;

/**
 * @author chi
 */
public class IdentificationFieldType extends AbstractFieldType<Identification> {
    public IdentificationFieldType() {
        super(Identification.class);
    }

    @Override
    public String name() {
        return "Id";
    }

    @Override
    public boolean validate(Object value, Context context) {
        //todo add idNumber validation
        return true;
    }
}
