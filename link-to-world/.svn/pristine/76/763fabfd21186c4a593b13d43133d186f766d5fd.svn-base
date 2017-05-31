package com.caej.insurance.api;

import java.util.List;

import com.caej.insurance.api.form.CreateInsuranceFormFieldRequest;
import com.caej.insurance.api.form.InsuranceFormFieldQuery;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.UpdateInsuranceFormFieldRequest;

import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceFormFieldWebService {
    @Path("/api/insurance/form/field/:id")
    @GET
    InsuranceFormFieldResponse get(@PathParam("id") String id);

    @Path("/api/insurance/form/group/:groupId/fields")
    @GET
    List<InsuranceFormFieldResponse> findByGroupName(@PathParam("groupId") String groupId);

    @Path("/api/insurance/form/group/:groupName/field/:fieldName")
    @GET
    InsuranceFormFieldResponse findByName(@PathParam("groupName") String groupName, @PathParam("fieldName") String fieldName);

    @Path("/api/insurance/form/field/find/:groupId")
    @PUT
    FindView<InsuranceFormFieldResponse> find(@PathParam("groupId") String groupId, InsuranceFormFieldQuery query);

    @Path("/api/insurance/form/field/:id")
    @PUT
    void update(@PathParam("id") String id, UpdateInsuranceFormFieldRequest request);

    @Path("/api/insurance/form/field")
    @POST
    void create(CreateInsuranceFormFieldRequest request);
}
