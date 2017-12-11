package com.caej.underwriting.builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.product.service.field.Address;

import io.sited.form.Form;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class UnderwritingOthersBuilder {
    public static UnderwritingRequest.UnderwritingRequestOthers build(Form form, Double invoicePremium) {
        UnderwritingRequest.UnderwritingRequestOthers others = new UnderwritingRequest.UnderwritingRequestOthers();
        Map<String, Object> map = form.value;
        if (map.get("others") == null) return others;
        if (form.fieldGroup("others").optional) return others;
        Map<String, Object> group = JSON.fromJSON(JSON.toJSON(map.get("others")), Map.class);
        others.invoiceName = group.get("invoiceName").toString();
        LocalDate invoiceDate = JSON.fromJSON(JSON.toJSON(group.get("invoiceDate")), LocalDate.class);
        others.invoiceDate = invoiceDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (group.get("invoiceDeliverType") != null) {
            others.invoicedeliverType = group.get("invoiceDeliverType").toString();
            others.firstName = group.get("firstName").toString();
            others.mobile = group.get("mobile").toString();
            Address address = JSON.fromJSON(JSON.toJSON(group.get("address")), Address.class);
            others.state = address.state;
            others.city = address.city;
            others.ward = address.ward;
            others.address = address.address1;
            others.postalCode = group.get("postalCode").toString();
            others.premium = invoicePremium;
        }
        return others;
    }
}
