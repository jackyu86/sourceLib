package com.caej.product.service.archive;

import java.util.List;

import com.caej.product.service.field.Address;
import com.google.common.base.Strings;

import io.sited.form.Form;

/**
 * @author miller
 */
public class AddressArchiveProvider implements ArchiveProvider {
    public String name() {
        return "Address";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        Address address = group.value(fieldName);
        return value(address);
    }

    private String value(Address address) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(address.state);
        stringBuilder.append(' ');
        stringBuilder.append(address.city);
        stringBuilder.append(' ');
        stringBuilder.append(address.ward);
        stringBuilder.append(' ');
        stringBuilder.append(address.address1);
        if (!Strings.isNullOrEmpty(address.address2)) {
            stringBuilder.append(' ');
            stringBuilder.append(address.address2);
        }
        return stringBuilder.toString();
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<Address> addressList = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        addressList.forEach(address -> {
            stringBuilder.append(value(address));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
