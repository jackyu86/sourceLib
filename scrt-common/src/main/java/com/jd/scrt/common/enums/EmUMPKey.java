package com.jd.scrt.common.enums;

/**
 * Created by zhaosiming on 2016/1/28.
 */
public enum EmUMPKey {
    SUPER_UpdateInfoForPaymentService_getUserInfoFromRest("scrt.super.UpdateInfoForPaymentService.getUserInfoFromRest", "通过Rest接口获取小金库的用户信息"),
    SCRT_SUPER_CARDCENTRE_QUERY("scrt.super.CardCentreService.queryUserByCardId", "调用卡中心接口"),
    ;

    private String value;
    private String title;

    private EmUMPKey(String value, String title) {
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
