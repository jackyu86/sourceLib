package com.caej.site.esb.web;

import java.io.IOError;
import java.io.IOException;

import javax.inject.Inject;

import com.caej.api.esb.ESBRequest;
import com.caej.api.esb.KdlinsESBWebService;

import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

/**
 * @author miller
 */
public class ESBController {
    @Inject
    KdlinsESBWebService kdlinsESBWebService;

    @Path("/api/esb")
    @POST
    public Response response(Request request) throws IOError {
        /*ESBResponse response = new ESBResponse();
        response.status = "2";
        response.errors = new ArrayList<>();
        ESBResponse.ESBResponseError error = new ESBResponse.ESBResponseError();
        error.phone = "1805005759";
        error.code = "1";
        error.msg = "提交失败";
        response.errors.add(error);
        return Response.body(response).setContentType("text/xml");*/
        return Response.empty();
    }

    @Path("/api/esb")
    @PUT
    public Response to(Request request) throws IOException {
        ESBRequest esbRequest = request.body(ESBRequest.class);
        return Response.body(kdlinsESBWebService.response(esbRequest));
    }
}
