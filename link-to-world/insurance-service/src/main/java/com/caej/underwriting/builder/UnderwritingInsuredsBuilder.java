package com.caej.underwriting.builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.order.order.OrderView;
import com.caej.product.service.field.Address;
import com.caej.product.service.field.Identification;
import com.google.common.collect.Lists;

import io.sited.util.JSON;

/**
 * @author miller
 */
public class UnderwritingInsuredsBuilder {
    public static List<UnderwritingRequest.UnderwritingRequestInsured> build(OrderView order) {
        //todo  改为从items拿
        List<UnderwritingRequest.UnderwritingRequestInsured> list = Lists.newArrayList();
        order.items.forEach(orderItemView -> {
            String[] array = orderItemView.id.split("-");
            list.add(build(orderItemView.form, Integer.valueOf(array[1]), orderItemView));
        });
        return list;
    }

    private static UnderwritingRequest.UnderwritingRequestInsured build(Map<String, Object> map, Integer num, OrderView.OrderItemView orderItemView) {
        UnderwritingRequest.UnderwritingRequestInsured insured = new UnderwritingRequest.UnderwritingRequestInsured();
        insured.insuredNum = num;
        insured.policyCode = orderItemView.policyCode;
        insured.relation2Ph = Integer.valueOf(map.get("relation").toString());
        insured.firstName = map.get("name").toString();
        insured.gender = map.get("gender").toString();
        insured.birthday = LocalDate.parse(map.get("birthDate").toString()).format(DateTimeFormatter.ofPattern("YYYYMMdd"));
        Identification identification = JSON.fromJSON(JSON.toJSON(map.get("id")), Identification.class);
        insured.certiType = identification.type;
        insured.certiCode = identification.number;
        insured.nationality = map.get("nationality") == null ? null : map.get("nationality").toString();
        insured.marriageStatus = map.get("marriageType") == null ? null : map.get("marriageType").toString();
        insured.workCompany = map.get("workCompany") == null ? null : map.get("workCompany").toString();
        insured.title = map.get("title") == null ? null : map.get("title").toString();
        //todo job
        insured.mobile = map.get("phone") == null ? null : map.get("phone").toString();
        insured.telephone = map.get("telephone") == null ? null : map.get("telephone").toString();
        insured.email = map.get("email") == null ? null : map.get("email").toString();
        insured.income = map.get("income") == null ? null : (Long) map.get("income");
        insured.incomeSource = map.get("incomeSource") == null ? null : map.get("incomeSource").toString();
        insured.height = map.get("height") == null ? null : (Double) map.get("height");
        insured.weight = map.get("weight") == null ? null : (Double) map.get("weight");
        if (map.get("address") != null) {
            Address address = JSON.fromJSON(JSON.toJSON(map.get("address")), Address.class);
            insured.state = address.state;
            insured.city = address.city;
            insured.ward = address.ward;
            insured.address = address.address1;
        }
        insured.policyCode = map.get("postalCode") == null ? null : map.get("postalCode").toString();
        return insured;
    }
}
