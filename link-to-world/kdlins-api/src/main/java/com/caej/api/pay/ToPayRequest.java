package com.caej.api.pay;

/**
 * @author miller
 */
public class ToPayRequest {
    public String userId;
    public String outTradeNo;
    public Long timeStamp;
    public String sign;
    public String channel;
    public Integer amount;
    public String title;
    public String description;
    public String optional;
    public String returnUrl;
    public String notifyUrl;
    public Integer timeout;
    public String limitPay;
    public String pcPay;
    public String openId;
    public String showUrl;
    public String qrCodeMode;
    public String qrCodeWidth;
}
