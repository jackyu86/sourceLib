package com.caej.api.pay;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author miller
 */
public class ToPayResponse {
    public Integer status;
    public List<String> errors;
    public String billId;
    public String billUrl;
    //WX_NATIVE
    public String codeUrl;
    public String qrCode;
    //WX_JSAPI
    public String appId;
    @XmlElement(name = "package")
    public String packageName;
    public String nonceStr;
    public String timeStamp;
    public String paySign;
    public String signType;
    //ALI_WEB AND ALI_QRCODE
    public String payUrl;
}