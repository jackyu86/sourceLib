package com.caej.underwriting.builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.product.service.field.Identification;
import com.google.common.collect.Lists;

import io.sited.form.FieldGroup;
import io.sited.form.Form;

/**
 * @author miller
 */
public class UnderwritingBeneficiaryBuilder {
    public static List<UnderwritingRequest.UnderwritingRequestBeneficiary> build(Form form) {
        List<UnderwritingRequest.UnderwritingRequestBeneficiary> result = Lists.newArrayList();
        FieldGroup fieldGroup = form.fieldGroup("beneficiary");
        if (fieldGroup.multiple) {
            Form.ListGroup listGroup = form.listGroup("beneficiary");
            listGroup.values.forEach(map -> result.add(build(map)));
        } else {
            Form.Group group = form.group("beneficiary");
            result.add(build(group.values));
        }
        return result;
    }

    private static UnderwritingRequest.UnderwritingRequestBeneficiary build(Map<String, Object> map) {
        UnderwritingRequest.UnderwritingRequestBeneficiary beneficiary = new UnderwritingRequest.UnderwritingRequestBeneficiary();
        beneficiary.beneType = map.get("type").toString();
        beneficiary.isLegal = map.get("legalBeneficiary").toString();
        if (beneficiary.isLegal.equals("Y")) return beneficiary;
        beneficiary.firstName = map.get("name") == null ? "" : map.get("name").toString();
        beneficiary.gender = map.get("gender") == null ? "" : map.get("gender").toString();
        beneficiary.birthday = map.get("birthDate") == null ? "" : ((LocalDate) map.get("birthDate")).format(DateTimeFormatter.ofPattern("YYYYMMdd"));
        Identification identification = (Identification) map.get("id");
        if (identification != null) {
            beneficiary.certiType = identification.type;
            beneficiary.certiCode = identification.number;
        }
        //todo certiExpireDate,nationality
        beneficiary.relation2Ins = map.get("relation") == null ? "" : map.get("relation").toString();
        beneficiary.beneRate = map.get("beneficiaryRate") == null ? 1 : Double.valueOf(map.get("beneficiaryRate").toString());
        return beneficiary;
    }
}
