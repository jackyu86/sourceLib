package com.caej.underwriting.builder;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.product.api.product.ProductResponse;

/**
 * @author miller
 */
public class UnderwritingPlanBuilder {
    public static UnderwritingRequest.UnderwritingRequestPlan build(ProductResponse product) {
        UnderwritingRequest.UnderwritingRequestPlan plan = new UnderwritingRequest.UnderwritingRequestPlan();
        plan.planName = product.displayName;
        plan.planCode = product.planCode;
        return plan;
    }
}
