package com.caej.underwriting.builder;

import com.caej.api.underwritting.UnderwritingRequest;

/**
 * @author miller
 */
public class UnderwritingCancelBuilder {
    public static UnderwritingRequest.UnderwritingRequestCancel build(com.caej.api.underwritting.UnderwritingRequest request) {
        UnderwritingRequest.UnderwritingRequestCancel cancel = new UnderwritingRequest.UnderwritingRequestCancel();
        cancel.policyCode = request.application.applyCode;
        //todo 等需求确定
        cancel.payMode = 7;
        //cancel.payMode = underwritingRequest.application.paymentMethod.payMode;
        return cancel;
    }
}
