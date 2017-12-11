package com.caej.product.service.archive;

import io.sited.form.Form;

import java.util.List;

/**
 * @author miller
 */
public class MarriageTypeArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "MarriageType";
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

    private String value(String marriageType) {
        switch (marriageType) {
            case "1":
                return "已婚";
            case "2":
                return "未婚";
            case "3":
                return "离婚";
            case "4":
                return "丧偶";
            case "5":
                return "其他";
            default:
                return null;
        }
    }
}
