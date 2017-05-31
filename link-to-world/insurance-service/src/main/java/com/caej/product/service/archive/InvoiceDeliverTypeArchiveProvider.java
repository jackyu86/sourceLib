package com.caej.product.service.archive;

import io.sited.form.Form;

import java.util.List;

/**
 * @author miller
 */
public class InvoiceDeliverTypeArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "InvoiceDeliverType";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<String> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(type -> {
            stringBuilder.append(value(type));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public String value(String type) {
        if (type.equals("0")) {
            return "挂号信";
        }
        return "快递";
    }
}
