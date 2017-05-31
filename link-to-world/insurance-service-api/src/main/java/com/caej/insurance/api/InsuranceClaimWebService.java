package com.caej.insurance.api;

import java.util.List;

import com.caej.insurance.api.claim.BatchCreateInsuranceClaimRequest;
import com.caej.insurance.api.claim.BatchGetInsuranceClaimRequest;
import com.caej.insurance.api.claim.InsuranceClaimQuery;
import com.caej.insurance.api.claim.InsuranceClaimRequest;
import com.caej.insurance.api.claim.InsuranceClaimResponse;

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
public interface InsuranceClaimWebService {
    @Path("/api/insurance/claim/:id")
    @GET
    InsuranceClaimResponse get(@PathParam("id") String id);

    @Path("/api/insurance/claim")
    @POST
    InsuranceClaimResponse create(InsuranceClaimRequest request);

    @Path("/api/insurance/claim/find")
    @PUT
    FindView<InsuranceClaimResponse> find(InsuranceClaimQuery query);

    @Path("/api/insurance/claim/batch")
    @POST
    List<InsuranceClaimResponse> batchCreateOrUpdate(BatchCreateInsuranceClaimRequest request);

    @Path("/api/insurance/claim/batch")
    @PUT
    List<InsuranceClaimResponse> batchGet(BatchGetInsuranceClaimRequest request);

    @Path("/api/insurance/claim/:id")
    @PUT
    void update(@PathParam("id") String id, InsuranceClaimRequest request);

    @Path("/api/insurance/claim/:id")
    @DELETE
    void delete(@PathParam("id") String id);
}
