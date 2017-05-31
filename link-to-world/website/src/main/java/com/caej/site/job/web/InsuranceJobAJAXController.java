package com.caej.site.job.web;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceJobWebService;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

/**
 * @author chi
 */
public class InsuranceJobAJAXController {
    @Inject
    InsuranceJobWebService insuranceJobWebService;

    @Path("/ajax/job/:treeId")
    @GET
    public Response top(Request request) {
        return Response.body(insuranceJobWebService.firstLevel(request.pathParam("treeId")).list);
    }

    @Path("/ajax/job/:id/children")
    @GET
    public Response children(Request request) {
        return Response.body(insuranceJobWebService.children(request.pathParam("id")));
    }

    @Path("/ajax/job/:id/ancestor")
    @GET
    public Response ancestor(Request request) {
        return Response.body(insuranceJobWebService.ancestor(request.pathParam("id")));
    }
}
