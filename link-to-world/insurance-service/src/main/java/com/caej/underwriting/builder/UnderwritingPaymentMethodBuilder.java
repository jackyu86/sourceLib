package com.caej.underwriting.builder;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.order.payment.PaymentView;

/**
 * @author miller
 */
public class UnderwritingPaymentMethodBuilder {
    public static UnderwritingRequest.UnderwritingRequestPaymentMethod build(PaymentView paymentView) {
        UnderwritingRequest.UnderwritingRequestPaymentMethod paymentMethod = new UnderwritingRequest.UnderwritingRequestPaymentMethod();
        paymentMethod.payMode = Integer.valueOf(paymentView.payMode);
        paymentMethod.paymentNo = paymentView.outTradeNo;
        paymentMethod.premium = paymentView.total;
        return paymentMethod;
    }
}
