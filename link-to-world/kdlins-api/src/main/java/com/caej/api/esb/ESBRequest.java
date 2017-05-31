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
public class ESBRequest {
    @XmlElement(name = "Header")
    public ESBRequestHeader header;
    @XmlElement(name = "Body")
    public ESBRequestBody body;


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Header")
    public static class ESBRequestHeader {
        @XmlElement(name = "Request")
        public ESBRequestHeaderRequest request;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Request")
    public static class ESBRequestHeaderRequest {
        @XmlElement(name = "Encrypt")
        public String encrypt;
        @XmlElement(name = "ReqSysCode")
        public String reqSysCode;
        @XmlElement(name = "ReqTime")
        public String reqTime;
        @XmlElement(name = "ReqType")
        public String reqType;
        @XmlElement(name = "Security")
        public ESBRequestHeaderRequestSecurity security;
        @XmlElement(name = "SerialNumber")
        public String serialNumber;
        @XmlElement(name = "ServiceCode")
        public String serviceCode;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Security")
    public static class ESBRequestHeaderRequestSecurity {
        @XmlElement(name = "AppKey")
        public String appKey;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Body")
    public static class ESBRequestBody {
        @XmlElement(name = "Request")
        public ESBRequestBodyRequest request;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Request")
    public static class ESBRequestBodyRequest {
        @XmlElement(name = "requestSms")
        public ESBRequestBodyRequestSms requestSms;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "requestSms")
    public static class ESBRequestBodyRequestSms {
        @XmlElement(name = "main")
        public ESBRequestBodySmsMain main;
        @XmlElementWrapper(name = "items")
        @XmlElement(name = "item")
        public List<ESBRequestBodySmsItem> items;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "main")
    public static class ESBRequestBodySmsMain {
        @XmlElement(name = "channelCode")
        public String channelCode;
        @XmlElement(name = "type")
        public String type;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "item")
    public static class ESBRequestBodySmsItem {
        @XmlElement(name = "phone")
        public String phone;
        @XmlElement(name = "content")
        public String content;
        @XmlElement(name = "time")
        public String time;
        @XmlElement(name = "taskId")
        public String taskId;
        @XmlElement(name = "reply")
        public String reply;
    }
}