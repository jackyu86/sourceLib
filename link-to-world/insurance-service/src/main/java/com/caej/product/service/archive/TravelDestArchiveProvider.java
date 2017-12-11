package com.caej.product.service.archive;

import java.util.List;

import com.caej.product.service.field.TravelDest;

import io.sited.form.Form;

/**
 * @author miller
 */
public class TravelDestArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "TravelDest";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<TravelDest> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(travelDest -> {
            stringBuilder.append(value(travelDest));
            stringBuilder.append(",");
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String value(TravelDest travelDest) {
        return travelDest.name;
    }
}
