package com.caej.insurance.api;

import java.util.List;

import com.caej.insurance.api.insurance.BatchGetInsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;

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
public interface InsuranceLiabilityWebService {
    @Path("/api/insurance/liability/:id")
    @GET
    InsuranceLiabilityResponse get(@PathParam("id") String id);

    @Path("/api/insurance/liability/batch")
    @PUT
    List<InsuranceLiabilityResponse> batchGet(BatchGetInsuranceLiabilityRequest request);

    @Path("/api/insurance/liability")
    @POST
    InsuranceLiabilityResponse create(InsuranceLiabilityRequest request);

    @Path("/api/insurance/liability/find")
    @PUT
    FindView<InsuranceLiabilityResponse> find(InsuranceLiabilityQuery query);

    @Path("/api/insurance/liability/update/:id")
    @PUT
    void update(@PathParam("id") String id, InsuranceLiabilityRequest request);

    @Path("/api/insurance/liability/:id")
    @DELETE
    void delete(@PathParam("id") String id);

    @Path("/api/insurance/liability/group/:groupId/count")
    @GET
    Long groupCount(@PathParam("groupId") String groupId);
}
