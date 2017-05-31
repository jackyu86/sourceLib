package com.caej.order.order;

/**
 * @author chi
 */
public enum OrderStatusView {
    DRAFT, //拟定
    PAYMENT_PENDING, //待支�?
    PAYMENT_FAILED, //支付失败
    PAYMENT_COMPLETED, //支付成功
    AUDITING, //核保
    VENDOR_INSURED, //已承�?
    VENDOR_REJECT, //拒保
    DOCUMENTED, //已出�?
    SURRENDERING, //请求�?�?
    SURRENDERED, //已�??�?
    SURRENDER_FAILED, //�?保失�?
    REFUND //已�??�?
}
