package com.caej.insurance.api;

import java.util.List;

import com.caej.insurance.api.insurance.BatchGetInsuranceRequest;
import com.caej.insurance.api.insurance.InsuranceAdminRequest;
import com.caej.insurance.api.insurance.InsuranceQuery;
import com.caej.insurance.api.insurance.InsuranceResponse;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceWebService {
    @Path("/api/insurance/:id")
    @GET
    InsuranceResponse get(@PathParam("id") String id);

    @Path("/api/insurance/batch")
    @PUT
    List<InsuranceResponse> batchGet(BatchGetInsuranceRequest request);

    @Path("/api/insurance/find")
    @PUT
    FindView<InsuranceResponse> find(InsuranceQuery insuranceQuery);

    @Path("/api/insurance")
    @POST
    InsuranceResponse create(InsuranceAdminRequest request);

    @Path("/api/insurance/update/:id")
    @PUT
    void update(@PathParam("id") String id, InsuranceAdminRequest request);

    @Path("/api/insurance/delete/:id")
    @DELETE
    void delete(@PathParam("id") String id);
}
