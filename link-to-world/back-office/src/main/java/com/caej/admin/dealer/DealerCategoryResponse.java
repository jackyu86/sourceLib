package com.caej.admin.dealer;

import java.util.List;

import com.caej.insurance.api.category.InsuranceCategoryResponse;
import com.google.common.collect.Lists;

/**
 * @author Jonathan.Guo
 */
public class DealerCategoryResponse {
    public InsuranceCategoryResponse category;
    public List<DealerCategoryResponse> children = Lists.newArrayList();
    public List<DealerProductResponse> productList = Lists.newArrayList();
}
