package com.caej.product.service.price.engine;

import java.util.List;
import java.util.Map;

import com.caej.insurance.api.insurance.InsuranceAmountType;
import com.caej.insurance.api.insurance.InsurancePriceTableView;
import com.caej.insurance.domain.InsuranceAmount;
import com.caej.insurance.domain.InsuranceLiabilityRef;
import com.caej.product.domain.ProductInsurance;
import com.caej.product.service.field.LiabilityFieldType;
import com.caej.product.service.price.InsurancePriceEngine;
import com.caej.product.service.price.InsurancePriceRequest;
import com.caej.product.service.price.InsurancePriceResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.form.Field;
import io.sited.form.FieldGroup;
import io.sited.form.Form;

/**
 * @author miller
 */
public class BaseTableInsurancePriceEngineImpl implements InsurancePriceEngine {
    @Override
    public InsurancePriceResponse calculate(InsurancePriceRequest request) {
        FieldGroup insuredListGroup = request.form.fieldGroup("insured");
        int count = 1;
        if (insuredListGroup.multiple) {
            count = request.form.listGroup("insured").size() == 0 ? 1 : request.form.listGroup("insured").size();
        }
        boolean hasLiability = false;
        for (int i = 0; i < request.form.groups.size(); i++) {
            FieldGroup fieldGroup = request.form.groups.get(i);
            if (fieldGroup.name.equals("liability")) {
                hasLiability = true;
                break;
            }
        }
        if (!hasLiability) addDefaultLiability(request);
        InsurancePriceTableView price = request.insurance.priceTable;
        String key = key(price.xFactors, request.form);
        Map<String, Double> values = price.table.get(key);
        FieldName fieldName = new FieldName(price.yFactor);
        Object yKey = request.form.group(fieldName.groupName).value(fieldName.fieldName);
        if (yKey == null) yKey = Lists.newArrayList(values.keySet()).get(0);
        FieldName baseField = new FieldName(price.baseFactor);
        Double baseValue = Double.valueOf(request.form.group(baseField.groupName).value(baseField.fieldName).toString());
        InsurancePriceResponse calculateResponse = new InsurancePriceResponse();
        calculateResponse.price = values.get(yKey.toString()) * count * (baseValue / price.base);
        return calculateResponse;
    }

    private void addDefaultLiability(InsurancePriceRequest request) {
        final String groupName = "liability";
        FieldGroup liabilityGroup = new FieldGroup();
        liabilityGroup.name = groupName;
        liabilityGroup.displayName = "保险责任";
        liabilityGroup.fields = Lists.newArrayList();
        Map<String, Object> liabilityGroupValue = Maps.newHashMap();
        List<ProductInsurance> insurances = request.product.insurances;
        for (ProductInsurance insurance : insurances) {
            List<InsuranceLiabilityRef> liabilities = insurance.liabilities;
            for (InsuranceLiabilityRef liability : liabilities) {
                Field field = new Field();
                field.name = liability.insuranceLiabilityId.toHexString();
                field.groupName = groupName;
                field.type = new LiabilityFieldType();
                liabilityGroup.fields.add(field);
                liabilityGroupValue.put(liability.insuranceLiabilityId.toHexString(), getLiabilityValue(liability));
            }
        }
        request.form.groups.add(liabilityGroup);
        if (!request.form.value.containsKey(groupName)) request.form.value.put(groupName, liabilityGroupValue);
    }

    private String key(List<String> factors, Form form) {
        if (factors == null || factors.isEmpty()) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < factors.size(); i++) {
            if (i != 0) {
                b.append('-');
            }

            FieldName fieldName = new FieldName(factors.get(i));
            Object value = form.group(fieldName.groupName).value(fieldName.fieldName);
            b.append(value);
        }
        return b.toString();
    }

    private String getLiabilityValue(InsuranceLiabilityRef liability) {
        InsuranceAmount amount = liability.amount;
        if (InsuranceAmountType.USER_SELECTION.equals(amount.type)) {
            return amount.selections.get(0).toString();
        }
        return amount.fixedValue;
    }
}
