package com.caej.api.esb;

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
@XmlRootElement(name = "Service")
public class ESBResponse {
    @XmlElement(name = "Header")
    public ESBResponseHeader header;
    @XmlElement(name = "Body")
    public ESBResponseBody body;

    @XmlRootElement(name = "Header")
    public static class ESBResponseHeader {
        @XmlElement(name = "Request")
        public ESBResponseHeaderRequest request;
        @XmlElement(name = "Response")
        public ESBResponseHeaderResponse response;
    }

    @XmlRootElement(name = "Request")
    public static class ESBResponseHeaderRequest {
        @XmlElement(name = "ServiceCode")
        public String serviceCode;
        @XmlElement(name = "ReqSysCode")
        public String reqSysCode;
        @XmlElement(name = "SerialNumber")
        public String serialNumber;
        @XmlElement(name = "ReqTime")
        public String reqTime;
        @XmlElement(name = "ReqType")
        public String reqType;
        @XmlElement(name = "Ucode")
        public String ucode;
        @XmlElement(name = "Bcode")
        public String bcode;
        @XmlElement(name = "Security")
        public ESBResponseHeaderRequestSecurity security;
    }

    @XmlRootElement(name = "Response")
    public static class ESBResponseHeaderResponse {
        @XmlElement(name = "RespSysCode")
        public String respSysCode;
        @XmlElement(name = "RespTime")
        public String respTime;
        @XmlElement(name = "ReturnCode")
        public String returnCode;
        @XmlElement(name = "returnMessage")
        public String returnMessage;
    }

    @XmlRootElement(name = "Security")
    public static class ESBResponseHeaderRequestSecurity {
        @XmlElement(name = "AppKey")
        public String appKey;
    }

    @XmlRootElement(name = "Body")
    public static class ESBResponseBody {
        @XmlElement(name = "Response")
        public ESBResponseBodyResponse response;
    }

    @XmlRootElement(name = "Response")
    public static class ESBResponseBodyResponse {
        @XmlElement(name = "responseSms")
        public ESBResponseBodyResponseSms responseSms;
    }

    @XmlRootElement(name = "responseSms")
    public static class ESBResponseBodyResponseSms {
        @XmlElement(name = "status")
        public String status;
        @XmlElementWrapper(name = "errors")
        @XmlElement(name = "error")
        public List<ESBResponseError> errors;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "error")
    public static class ESBResponseError {
        @XmlElement(name = "phone")
        public String phone;
        @XmlElement(name = "taskId")
        public String taskId;
        @XmlElement(name = "code")
        public String code;
        @XmlElement(name = "msg")
        public String msg;
    }
}
