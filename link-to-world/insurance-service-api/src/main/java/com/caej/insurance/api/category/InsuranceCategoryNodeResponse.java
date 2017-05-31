package com.caej.insurance.api.category;

import java.util.List;

/**
 * @author chi
 */
public class InsuranceCategoryNodeResponse {
    public InsuranceCategoryResponse category;
    public List<InsuranceCategoryNodeResponse> children;
}
