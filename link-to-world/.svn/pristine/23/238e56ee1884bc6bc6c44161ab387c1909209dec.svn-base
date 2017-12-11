package com.caej.product.service.archive;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceJobWebService;

import io.sited.form.Form;

/**
 * @author miller
 */
public class JobArchiveProvider implements ArchiveProvider {
    @Inject
    InsuranceJobWebService insuranceJobWebService;

    @Override
    public String name() {
        return "Job";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    private String value(String id) {
        return insuranceJobWebService.get(id).displayName;
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<String> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(job -> {
            stringBuilder.append(value(job));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
