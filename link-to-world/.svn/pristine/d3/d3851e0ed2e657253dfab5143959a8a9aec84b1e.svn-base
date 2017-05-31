package com.caej.admin.area;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceAreaWebService;
import com.caej.insurance.api.area.BatchGetAreaRequest;
import com.caej.insurance.api.area.InsuranceAreaResponse;

import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author miller
 */
public class InsuranceAreaAdminController {
    @Inject
    InsuranceAreaWebService insuranceAreaWebService;

    @Path("/admin/ajax/area")
    @GET
    public List<InsuranceAreaResponse> find(Request request) {
        return insuranceAreaWebService.find();
    }

    @Path("/admin/ajax/area/batch")
    @PUT
    public List<InsuranceAreaResponse> batchGet(Request request) {
        BatchGetAreaRequest batchGetAreaRequest = request.body(BatchGetAreaRequest.class);
        return insuranceAreaWebService.batchGet(batchGetAreaRequest);
    }
}
