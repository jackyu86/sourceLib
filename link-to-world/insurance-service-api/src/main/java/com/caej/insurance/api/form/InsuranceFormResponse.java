package com.caej.insurance.api.form;

import java.util.List;

/**
 * @author chi
 */
public class InsuranceFormResponse {
    public List<InsuranceFormGroupView> groups;

    public static class InsuranceFormGroupView {
        public InsuranceFormGroupResponse insuranceFormGroup;
        public List<InsuranceFormFieldResponse> insuranceFormFields;
    }
}
