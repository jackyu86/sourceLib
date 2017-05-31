package com.caej.product.service.archive;

import java.util.List;

import com.caej.product.service.field.Identification;

import io.sited.form.Form;

/**
 * @author miller
 */
public class IdArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "Id";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<Identification> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(id -> {
            stringBuilder.append(id.type);
            stringBuilder.append(' ');
            stringBuilder.append(id.number);
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String value(Identification id) {
        return id.type + " " + id.number;
    }
}
