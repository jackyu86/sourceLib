package com.caej.underwriting.builder;

import java.util.List;
import java.util.Map;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.product.service.field.EffectiveInsurance;
import com.google.common.collect.Lists;

import io.sited.form.Field;
import io.sited.form.FieldGroup;
import io.sited.form.Form;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class UnderwritingInformationBuilder {
    public static List<UnderwritingRequest.UnderwritingRequestDeclaration> build(Form form) {
        Map<String, Object> map1 = form.value;
        if (map1.get("information") == null) return null;
        List<UnderwritingRequest.UnderwritingRequestDeclaration> result = Lists.newArrayList();
        FieldGroup fieldGroup = form.fieldGroup("information");
        if (fieldGroup.multiple) {
            Form.ListGroup listGroup = form.listGroup("information");
            listGroup.values.forEach(map -> result.addAll(build(map, fieldGroup.fields)));
        } else {
            Form.Group group = form.group("information");
            result.addAll(build(group.values, fieldGroup.fields));
        }
        return result;
    }

    private static List<UnderwritingRequest.UnderwritingRequestDeclaration> build(Map<String, Object> map, List<Field> fields) {
        List<UnderwritingRequest.UnderwritingRequestDeclaration> list = Lists.newArrayList();
        int i = 1;
        fields.forEach(field -> {
            UnderwritingRequest.UnderwritingRequestDeclaration declaration = new UnderwritingRequest.UnderwritingRequestDeclaration();
            declaration.customer = field.options.get("customer").toString();
            declaration.orderNum = i + "";
            if ("highAccidentInsuranceQuestion5".equals(field.name)) {
                if (null != map.get(field.name)) {
                    EffectiveInsurance effectiveInsurance = JSON.fromJSON(map.get(field.name).toString(), EffectiveInsurance.class);
                    declaration.fill1 = effectiveInsurance.fill1.toString();
                    declaration.fill2 = effectiveInsurance.fill2.toString();
                    declaration.fill3 = effectiveInsurance.fill3.toString();
                    declaration.fill4 = effectiveInsurance.fill4.toString();
                    declaration.fill5 = effectiveInsurance.fill5.toString();
                }
            } else if (map.get(field.name) instanceof Boolean) {
                declaration.yesNo = Boolean.valueOf(map.get(field.name).toString()) ? "Y" : "N";
            } else {
                declaration.fill1 = map.get(field.name).toString();
            }
            list.add(declaration);
        });

        return list;
    }
}
