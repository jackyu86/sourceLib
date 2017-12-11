package com.caej.api.underwritting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author miller
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class UnderwritingResponse {
    @XmlElement(name = "main")
    public UnderwritingResponseMain main;
    @XmlElement(name = "tradeStaus")
    public String tradeStaus;
    @XmlElementWrapper(name = "insureds")
    @XmlElement(name = "insured")
    public List<UnderwritingResponseInsured> insureds;
    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error")
    public List<UnderwritingResponseError> errors;

    @XmlRootElement(name = "main")
    public static class UnderwritingResponseMain {
        @XmlElement(name = "transNo")
        public String transNo;
        @XmlElement(name = "transType")
        public String transType;
        @XmlElement(name = "transDate")
        public String transDate;
        @XmlElement(name = "transTime")
        public String transTime;
    }

    @XmlRootElement(name = "insured")
    public static class UnderwritingResponseInsured {
        @XmlElement(name = "insuredNum")
        public Integer insuredNum;
        @XmlElement(name = "policyCode")
        public String policyCode;
        @XmlElement(name = "firstName")
        public String firstName;
        @XmlElement(name = "birthday")
        public String birthday;
        @XmlElement(name = "certiCode")
        public String certiCode;
        @XmlElement(name = "staus")
        public String staus;
        @XmlElement(name = "policyAddress")
        public String policyAddress;
    }

    @XmlRootElement(name = "error")
    public static class UnderwritingResponseError {
        @XmlElement(name = "code")
        public String code;
        @XmlElement(name = "errorMsg")
        public String errorMsg;
        @XmlElement(name = "filedCode")
        public String filedCode;
    }
}
