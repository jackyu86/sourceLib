package com.caej.product.service.field;

import io.sited.form.type.AbstractFieldType;

/**
 * @author miller
 */
public class AddressFieldType extends AbstractFieldType<Address> {
    public AddressFieldType() {
        super(Address.class);
    }

    @Override
    public String name() {
        return "Address";
    }
}
