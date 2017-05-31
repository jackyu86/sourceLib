package com.caej.product.service.archive;

import io.sited.form.Form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author miller
 */
public class LocalDateTimeArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "LocalDateTime";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    private String value(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(localDateTime);
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<LocalDateTime> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(localDateTime -> {
            stringBuilder.append(value(localDateTime));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
