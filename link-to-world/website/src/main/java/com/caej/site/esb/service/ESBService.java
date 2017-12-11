package com.caej.site.esb.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.cxf.endpoint.Client;

import com.caej.api.esb.ESBRequest;
import com.caej.api.esb.ESBResponse;
import com.caej.api.util.CxfClient;
import com.caej.site.config.ESBConfig;
import com.caej.site.util.UUIDUtil;
import com.google.common.collect.Lists;

import io.sited.util.XML;

/**
 * @author miller
 */
public class ESBService {
    private final Client client;
    private final ESBConfig esbConfig;

    public ESBService(ESBConfig esbConfig) {
        this.client = CxfClient.getCxfWebserviceClient(esbConfig.esbWsdl);
        this.esbConfig = esbConfig;
    }

    public ESBRequest buildSingleRequest(String phone, String content) {
        ESBRequest esbRequest = new ESBRequest();
        ESBRequest.ESBRequestHeader esbRequestHeader = new ESBRequest.ESBRequestHeader();
        ESBRequest.ESBRequestHeaderRequest esbRequestHeaderRequest = new ESBRequest.ESBRequestHeaderRequest();
        esbRequestHeaderRequest.encrypt = esbConfig.encrypt;
        esbRequestHeaderRequest.reqSysCode = esbConfig.reqSysCode;
        esbRequestHeaderRequest.reqTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS"));
        esbRequestHeaderRequest.reqType = esbConfig.reqType;
        ESBRequest.ESBRequestHeaderRequestSecurity esbRequestHeaderRequestSecurity = new ESBRequest.ESBRequestHeaderRequestSecurity();
        esbRequestHeaderRequestSecurity.appKey = esbConfig.appKey;
        esbRequestHeaderRequest.security = esbRequestHeaderRequestSecurity;
        esbRequestHeaderRequest.serialNumber = UUIDUtil.generate();
        esbRequestHeaderRequest.serviceCode = esbConfig.serviceCode;
        esbRequestHeader.request = esbRequestHeaderRequest;
        esbRequest.header = esbRequestHeader;
        ESBRequest.ESBRequestBody esbRequestBody = new ESBRequest.ESBRequestBody();
        esbRequest.body = esbRequestBody;
        ESBRequest.ESBRequestBodyRequest esbRequestBodyRequest = new ESBRequest.ESBRequestBodyRequest();
        esbRequestBody.request = esbRequestBodyRequest;
        ESBRequest.ESBRequestBodyRequestSms esbRequestBodyRequestSms = new ESBRequest.ESBRequestBodyRequestSms();
        esbRequestBodyRequest.requestSms = esbRequestBodyRequestSms;
        ESBRequest.ESBRequestBodySmsMain esbRequestBodySmsMain = new ESBRequest.ESBRequestBodySmsMain();
        esbRequestBodySmsMain.channelCode = esbConfig.channelCode;
        esbRequestBodySmsMain.type = esbConfig.sendType;
        esbRequestBodyRequestSms.main = esbRequestBodySmsMain;
        ESBRequest.ESBRequestBodySmsItem esbRequestBodySmsItem = new ESBRequest.ESBRequestBodySmsItem();
        esbRequestBodySmsItem.phone = phone;
        esbRequestBodySmsItem.content = content;
        esbRequestBodyRequestSms.items = Lists.newArrayList(esbRequestBodySmsItem);
        return esbRequest;
    }

    public ESBResponse callWebService(ESBRequest esbRequest) throws Exception {
        String requestXML = XML.toXML(esbRequest);
        Object[] ret = client.invoke("doServiceXml", requestXML);
        String xmlString = ret[0].toString().replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");
        return XML.fromXML(xmlString, ESBResponse.class);
    }
}
