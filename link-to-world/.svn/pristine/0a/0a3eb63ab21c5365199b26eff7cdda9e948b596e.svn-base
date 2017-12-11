package com.caej.product.service.archive;

import io.sited.form.Field;
import io.sited.form.Form;

import java.util.List;

/**
 * @author miller
 */
public class LiabilityArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "Liability";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return liabilityValue(group.fieldGroup.field(fieldName).get(), group.value(fieldName));
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<Integer> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(liability -> {
            stringBuilder.append(liabilityValue(group.fieldGroup.field(fieldName).get(), liability));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String liabilityValue(Field field, Integer value) {
        return field.displayName + ' ' + value;
    }
}
