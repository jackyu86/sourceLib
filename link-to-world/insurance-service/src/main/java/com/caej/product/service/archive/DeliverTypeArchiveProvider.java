package com.caej.product.service.archive;

import io.sited.form.Form;

import java.util.List;

/**
 * @author miller
 */
public class DeliverTypeArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "DeliverType";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    private String value(String type) {
        switch (type) {
            case "1":
                return "部门发送";
            case "2":
                return "邮寄";
            case "3":
                return "上门递送";
            case "4":
                return "银行柜台领取";
            case "5":
                return "电子保单";
            case "6":
                return "纸质保单";
            default:
                return null;
        }
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<String> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(value -> {
            stringBuilder.append(value(value));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
