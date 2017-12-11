package com.caej.api.pay;

/**
 * @author miller
 */
public class PrePayRequest {
    public String userId;
    public String outTradeNo;
    public Long timeStamp;
    public String sign;
    public String channel;
    public String notifyUrl;
    public String qrCodeWidth;
}
