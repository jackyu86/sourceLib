package com.caej.insurance.api;

import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupResponseList;

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
public interface InsuranceLiabilityGroupWebService {
    @Path("/api/insurance/liability/group")
    @GET
    InsuranceLiabilityGroupResponseList all();

    @Path("/api/insurance/liability/group")
    @POST
    InsuranceLiabilityGroupAdminResponse create(InsuranceLiabilityGroupAdminRequest request);

    @Path("/api/insurance/liability/group/:id")
    @GET
    InsuranceLiabilityGroupAdminResponse get(@PathParam("id") String id);

    @Path("/api/insurance/liability/group/find")
    @PUT
    FindView<InsuranceLiabilityGroupAdminResponse> find(InsuranceLiabilityGroupQuery query);

    @Path("/api/insurance/liability/group/:id")
    @PUT
    void update(@PathParam("id") String id, InsuranceLiabilityGroupAdminRequest request);

    @Path("/api/insurance/liability/group/:id")
    @DELETE
    void delete(@PathParam("id") String id);

}
