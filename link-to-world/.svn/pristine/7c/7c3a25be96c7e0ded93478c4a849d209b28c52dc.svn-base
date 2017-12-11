package com.caej.product.service.archive;

import io.sited.form.Form;

import java.util.List;

/**
 * @author miller
 */
public class GenderArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "Gender";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<String> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(gender -> {
            stringBuilder.append(value(gender));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String value(String gender) {
        switch (gender) {
            case "M":
                return "男";
            case "F":
                return "女";
            case "N":
                return "不确定";
            default:
                return null;
        }
    }
}
