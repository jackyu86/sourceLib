package com.caej.insurance.api.form;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chi
 */
public class InsuranceFormQuery {
    public List<InsuranceFormGroupView> groups;

    public static class InsuranceFormGroupView {
        public ObjectId insuranceFormGroupId;
        public List<ObjectId> insuranceFieldIds;
    }
}
