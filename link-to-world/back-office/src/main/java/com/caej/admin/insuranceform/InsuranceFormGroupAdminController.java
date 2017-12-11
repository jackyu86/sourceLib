package com.caej.admin.insuranceform;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldQuery;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupQuery;
import com.caej.insurance.api.form.InsuranceFormGroupRequest;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;

import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author miller
 */
public class InsuranceFormGroupAdminController {
    @Inject
    InsuranceFormGroupWebService insuranceFormGroupWebService;
    @Inject
    InsuranceFormFieldWebService insuranceFormFieldWebService;

    @Path("/admin/ajax/form-group/find")
    @PUT
    public FindView<InsuranceFormGroupResponse> find(Request request) {
        FormGroupAdminAJAXRequest formGroupAdminAJAXRequest = request.body(FormGroupAdminAJAXRequest.class);
        InsuranceFormGroupQuery query = new InsuranceFormGroupQuery();
        query.page = formGroupAdminAJAXRequest.page;
        query.limit = formGroupAdminAJAXRequest.limit;
        query.displayName = formGroupAdminAJAXRequest.displayName;
        if (formGroupAdminAJAXRequest.order != null && formGroupAdminAJAXRequest.order.contains("-")) {
            query.order = formGroupAdminAJAXRequest.order.replace("-", "");
            query.desc = true;
        } else {
            query.order = formGroupAdminAJAXRequest.order;
            query.desc = false;
        }
        return insuranceFormGroupWebService.find(query);
    }

    @Path("/admin/ajax/form-group/:id")
    @GET
    public InsuranceFormGroupResponse get(Request request) {
        String id = request.pathParam("id");
        return insuranceFormGroupWebService.get(id);
    }

    @Path("/admin/ajax/form-group/name/:name")
    @GET
    public InsuranceFormGroupResponse getByName(Request request) {
        String name = request.pathParam("name");
        return insuranceFormGroupWebService.getByName(name);
    }

    @Path("/admin/ajax/form-group/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        InsuranceFormGroupRequest insuranceFormGroupRequest = request.body(InsuranceFormGroupRequest.class);
        insuranceFormGroupWebService.update(id, insuranceFormGroupRequest);
    }

    @Path("/admin/ajax/form-field/find/:groupId")
    @PUT
    public FindView<InsuranceFormFieldResponse> findField(Request request) {
        String groupId = request.pathParam("groupId");
        FormFieldAdminAJAXRequest formFieldAdminAJAXRequest = request.body(FormFieldAdminAJAXRequest.class);
        InsuranceFormFieldQuery query = new InsuranceFormFieldQuery();
        query.page = formFieldAdminAJAXRequest.page;
        query.limit = formFieldAdminAJAXRequest.limit;
        query.displayName = formFieldAdminAJAXRequest.displayName;
        if (formFieldAdminAJAXRequest.order != null && formFieldAdminAJAXRequest.order.contains("-")) {
            query.order = formFieldAdminAJAXRequest.order.replace("-", "");
            query.desc = true;
        } else {
            query.order = formFieldAdminAJAXRequest.order;
            query.desc = false;
        }
        return insuranceFormFieldWebService.find(groupId, query);
    }

    @Path("/admin/ajax/form-field/group/:groupName/field/:fieldName")
    @GET
    public InsuranceFormFieldResponse getFieldByName(Request request) {
        String groupName = request.pathParam("groupName");
        String fieldName = request.pathParam("fieldName");
        return insuranceFormFieldWebService.findByName(groupName, fieldName);
    }
}
