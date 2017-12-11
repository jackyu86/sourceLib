package com.caej.product.api.product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.caej.insurance.api.insurance.InsurancePeriodView;

import io.sited.form.Form;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class FormView {
    public List<FieldGroupView> groups;
    public Map<String, Object> value;

    public static FormView view(Form form) {
        FormView formView = new FormView();
        formView.value = form.value;
        formView.groups = form.groups.stream().map(fieldGroup -> {
            FieldGroupView fieldGroupView = new FieldGroupView();
            fieldGroupView.name = fieldGroup.name;
            fieldGroupView.displayName = fieldGroup.displayName;
            fieldGroupView.optional = fieldGroup.optional;
            fieldGroupView.multiple = fieldGroup.multiple;
            fieldGroupView.fields = fieldGroup.fields.stream().map(field -> {
                FieldView fieldView = new FieldView();
                fieldView.name = field.name;
                fieldView.groupName = field.groupName;
                fieldView.type = field.type.name();
                fieldView.displayName = field.displayName;
                fieldView.options = field.options;
                fieldView.defaultValue = field.defaultValue;
                fieldView.editable = field.editable;
                fieldView.multiple = field.multiple;
                fieldView.hidden = field.hidden;
                fieldView.displayAs = field.displayAs;
                return fieldView;
            }).collect(Collectors.toList());
            return fieldGroupView;
        }).collect(Collectors.toList());
        return formView;
    }

    public Integer effectCount() {
        FieldGroupView fieldGroupView = fieldGroup("insured");
        if (fieldGroupView.multiple) {
            List<Object> list = JSON.convert(value.get("insured"), List.class);
            return list.size();
        } else {
            Map<String, Object> map = JSON.convert(value.get("product"), Map.class);
            return Integer.valueOf(map.get("unit").toString());
        }
    }

    public FieldGroupView fieldGroup(String name) {
        for (FieldGroupView group : groups) {
            if (group.name.equals(name)) {
                return group;
            }
        }
        return null;
    }

    public String period() {
        Map<String, Object> map = JSON.convert(value.get("plan"), Map.class);
        InsurancePeriodView periodView = JSON.convert(map.get("period"), InsurancePeriodView.class);
        return periodView.displayName;
    }
}
