package com.caej.insurance.api;

import com.caej.insurance.api.form.InsuranceFormGroupQuery;
import com.caej.insurance.api.form.InsuranceFormGroupRequest;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.insurance.api.form.InsuranceFormQuery;
import com.caej.insurance.api.form.InsuranceFormResponse;

import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceFormGroupWebService {
    @Path("/api/insurance/form/group/:id")
    @GET
    InsuranceFormGroupResponse get(@PathParam("id") String id);

    @Path("/api/insurance/form/group/name/:name")
    @PUT
    InsuranceFormGroupResponse getByName(@PathParam("name") String name);

    @Path("/api/insurance/form/group")
    @PUT
    InsuranceFormResponse get(InsuranceFormQuery query);

    @Path("/api/insurance/form/group")
    @GET
    InsuranceFormResponse get();

    @Path("/api/insurance/form/group/find")
    @PUT
    FindView<InsuranceFormGroupResponse> find(InsuranceFormGroupQuery query);

    @Path("/api/insurance/form/group/:id")
    @PUT
    void update(@PathParam("id") String id, InsuranceFormGroupRequest request);
}
