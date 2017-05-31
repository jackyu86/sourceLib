package com.caej.product.service.archive;

import java.util.List;

import com.caej.insurance.domain.InsurancePeriod;

import io.sited.form.Form;

/**
 * @author miller
 */
public class PlanPeriodArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "PlanPeriod";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    public String value(InsurancePeriod insurancePeriod) {
        return insurancePeriod.displayName;
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<InsurancePeriod> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(insurancePeriod -> {
            stringBuilder.append(value(insurancePeriod));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
