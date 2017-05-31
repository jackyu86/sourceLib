package com.caej.product.service.archive;

import io.sited.form.Form;

import java.util.List;

/**
 * @author miller
 */
public class RelationArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "Relation";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<String> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(relation -> {
            stringBuilder.append(value(relation));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String value(String relation) {
        switch (relation) {
            case "0":
                return "无关或不确定";
            case "1":
                return "配偶";
            case "2":
                return "子女";
            case "3":
                return "父母";
            case "4":
                return "亲属";
            case "5":
                return "本人";
            case "6":
                return "其他";
            case "7":
                return "雇佣关系";
            case "8":
                return "祖父母、外祖父母";
            case "9":
                return "孙子女、外孙子女";
            default:
                return null;
        }
    }
}
