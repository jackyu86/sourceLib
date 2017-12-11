package com.caej.underwriting.builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.product.service.field.Address;
import com.caej.product.service.field.Identification;

import io.sited.form.FieldGroup;
import io.sited.form.Form;

/**
 * @author miller
 */
public class UnderwritingPolicyHolderBuilder {
    public static UnderwritingRequest.UnderwritingRequestPolicyHolder build(Form.Group policyHolder, FieldGroup fieldGroup) {
        UnderwritingRequest.UnderwritingRequestPolicyHolder policyHolderRequest = new UnderwritingRequest.UnderwritingRequestPolicyHolder();
        policyHolderRequest.firstName = policyHolder.value("name");
        policyHolderRequest.gender = policyHolder.value("gender");
        policyHolderRequest.birthday = ((LocalDate) policyHolder.value("birthDate")).format(DateTimeFormatter.ofPattern("YYYYMMdd"));
        Identification identification = policyHolder.value("id");
        policyHolderRequest.certiType = identification.type;
        policyHolderRequest.certiCode = identification.number;
        if (fieldGroup.field("nationality").isPresent()) {
            policyHolderRequest.nationality = policyHolder.value("nationality");
        }
        if (fieldGroup.field("marriageType").isPresent())
            policyHolderRequest.marriageStatus = policyHolder.value("marriageType");
        if (fieldGroup.field("workCompany").isPresent())
            policyHolderRequest.workCompany = policyHolder.value("workCompany");
        if (fieldGroup.field("title").isPresent())
            policyHolderRequest.title = policyHolder.value("title");
        //todo job
        policyHolderRequest.mobile = policyHolder.value("phone");
        if (fieldGroup.field("telephone").isPresent())
            policyHolderRequest.telephone = policyHolder.value("telephone");
        if (fieldGroup.field("email").isPresent())
            policyHolderRequest.email = policyHolder.value("email");
        if (fieldGroup.field("income").isPresent())
            policyHolderRequest.income = policyHolder.value("income");
        if (fieldGroup.field("incomeSource").isPresent())
            policyHolderRequest.incomeSource = policyHolder.value("incomeSource");
        if (fieldGroup.field("height").isPresent())
            policyHolderRequest.height = policyHolder.value("height");
        if (fieldGroup.field("weight").isPresent())
            policyHolderRequest.weight = policyHolder.value("weight");
        if (fieldGroup.field("address").isPresent()) {
            Address address = policyHolder.value("address");
            policyHolderRequest.state = address.state;
            policyHolderRequest.city = address.city;
            policyHolderRequest.ward = address.ward;
            policyHolderRequest.address = address.address1;
        }
        if (fieldGroup.field("postalCode").isPresent())
            policyHolderRequest.postalCode = policyHolder.value("postalCode");
        return policyHolderRequest;
    }
}
