package com.caej.product.service.archive;

import io.sited.form.Form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author miller
 */
public class PlanStartTimeArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "PlanStartTime";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    private String value(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter.format(localDate);
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<LocalDate> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(localDate -> {
            stringBuilder.append(value(localDate));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
