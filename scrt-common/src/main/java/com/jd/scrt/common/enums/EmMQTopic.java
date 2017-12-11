package com.jd.scrt.common.enums;

/**
 * Created by zhaosiming on 2016/1/28.
 */
public enum EmMQTopic {
    SCRT_PAY_UPINFO("scrt_pay_upinfo", "查询支付单用户信息，相关数据更新改为异步处理"),
    SCRT_SUBSCRIBE_SUCCESS("scrt_subscribe_success", "对账成功"),
    SCRT_RAISE_COMPLETE("scrt_raise_complete", "认购成功"),
    ;
    private String value;
    private String title;

    private EmMQTopic(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String value() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return this.title;
    }
}
