package com.caej.product.service.archive;

import io.sited.form.Form;

import java.util.List;

/**
 * @author miller
 */
public class LegalBeneficiaryArchiveProvider implements ArchiveProvider {
    @Override
    public String name() {
        return "LegalBeneficiary";
    }

    @Override
    public String value(Form.Group group, String fieldName) {
        return value(group.value(fieldName));
    }

    @Override
    public String listValue(Form.Group group, String fieldName) {
        List<String> list = group.listValue(fieldName);
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(beneficiary -> {
            stringBuilder.append(value(beneficiary));
            stringBuilder.append(',');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private String value(String legalBeneficiary) {
        switch (legalBeneficiary) {
            case "Y":
                return "是";
            case "N":
                return "否";
            case "W":
                return "与该项无关";
            default:
                return null;
        }
    }
}
