package com.caej.site.esb.web.ajax;

import javax.inject.Inject;

import com.caej.api.esb.ESBRequest;
import com.caej.api.esb.ESBResponse;
import com.caej.esb.api.CreateESBRecordRequest;
import com.caej.esb.api.ESBRecordWebService;
import com.caej.site.config.ESBConfig;
import com.caej.site.esb.service.ESBService;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class ESBAJAXController {
    @Inject
    ESBService esbService;
    @Inject
    ESBConfig esbConfig;
    @Inject
    ESBRecordWebService esbRecordWebService;

    @Path("/esb/ajax/code/:phone")
    @GET
    public Response sendCode(Request request) throws Exception {
        String phone = request.pathParam("phone");
        CreateESBRecordRequest createESBRecordRequest = new CreateESBRecordRequest();
        ESBRequest esbRequest = esbService.buildSingleRequest(phone, "短信测试");
        createESBRecordRequest.serialNumber = esbRequest.header.request.serialNumber;
        createESBRecordRequest.reqTime = esbRequest.header.request.reqTime;
        createESBRecordRequest.items = JSON.toJSON(esbRequest.body.request.requestSms.items);
        ESBResponse esbResponse = new ESBResponse();
        try {
            esbResponse = esbService.callWebService(esbRequest);
            createESBRecordRequest.status = esbResponse.body.response.responseSms.status;
            createESBRecordRequest.errors = JSON.toJSON(esbResponse.body.response.responseSms.errors);
        } catch (Exception e) {
            esbResponse.body = new ESBResponse.ESBResponseBody();
            esbResponse.body.response = new ESBResponse.ESBResponseBodyResponse();
            esbResponse.body.response.responseSms = new ESBResponse.ESBResponseBodyResponseSms();
            esbResponse.body.response.responseSms.status = "-2";
            createESBRecordRequest.status = "-2";
            createESBRecordRequest.errors = e.getMessage();
        }
        esbRecordWebService.create(createESBRecordRequest);
        return Response.body(esbResponse);
    }
}
