package com.caej.admin.insuranceform;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.CreateInsuranceFormFieldRequest;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.insurance.api.form.UpdateInsuranceFormFieldRequest;

import io.sited.admin.AdminUser;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author chi
 */
public class InsuranceFormFieldAdminController {
    @Inject
    InsuranceFormFieldWebService insuranceFormFieldWebService;
    @Inject
    InsuranceFormGroupWebService insuranceFormGroupWebService;

    @Path("/admin/ajax/form-field/:id")
    @GET
    public InsuranceFormFieldResponse getField(Request request) {
        String id = request.pathParam("id");
        return insuranceFormFieldWebService.get(id);
    }

    @Path("/admin/ajax/form-field/:id")
    @PUT
    public void updateField(Request request) {
        String id = request.pathParam("id");
        UpdateInsuranceFormFieldRequest insuranceFormFieldRequest = request.body(UpdateInsuranceFormFieldRequest.class);
        AdminUser current = request.require(AdminUser.class);
        insuranceFormFieldRequest.requestBy = current.username;
        insuranceFormFieldWebService.update(id, insuranceFormFieldRequest);
    }

    @Path("/admin/ajax/form-field")
    @POST
    public void create(Request request) {
        CreateInsuranceFormFieldRequest createInsuranceFormFieldRequest = request.body(CreateInsuranceFormFieldRequest.class);
        AdminUser current = request.require(AdminUser.class);
        createInsuranceFormFieldRequest.requestBy = current.username;
        InsuranceFormGroupResponse custom = insuranceFormGroupWebService.getByName("custom");
        createInsuranceFormFieldRequest.groupId = custom.id.toHexString();
        insuranceFormFieldWebService.create(createInsuranceFormFieldRequest);
    }
}
