package com.caej.admin.insuranceliability;

import java.util.List;

import javax.inject.Inject;
import com.caej.insurance.api.InsuranceLiabilityGroupWebService;
import com.caej.insurance.api.InsuranceLiabilityWebService;
import com.caej.insurance.api.insurance.BatchGetInsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;

import io.sited.admin.AdminUser;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author miller
 */
public class InsuranceLiabilityAdminController {
    @Inject
    InsuranceLiabilityWebService insuranceLiabilityWebService;
    @Inject
    InsuranceLiabilityGroupWebService insuranceLiabilityGroupWebService;

    @Path("/admin/ajax/insurance/liability/:id")
    @GET
    public InsuranceLiabilityResponse get(Request request) {
        String id = request.pathParam("id");
        return insuranceLiabilityWebService.get(id);
    }

    @Path("/admin/ajax/insurance/liability/batch")
    @PUT
    public List<InsuranceLiabilityResponse> batchGet(Request request) {
        BatchGetInsuranceLiabilityRequest batchGetInsuranceLiabilityRequest = request.body(BatchGetInsuranceLiabilityRequest.class);
        return insuranceLiabilityWebService.batchGet(batchGetInsuranceLiabilityRequest);
    }

    @Path("/admin/ajax/insurance/liability")
    @POST
    public InsuranceLiabilityResponse create(Request request) {
        InsuranceLiabilityRequest insuranceLiabilityRequest = request.body(InsuranceLiabilityRequest.class);
        AdminUser adminUser = request.require(AdminUser.class, null);
        insuranceLiabilityRequest.requestBy = adminUser.id;
        return insuranceLiabilityWebService.create(insuranceLiabilityRequest);
    }

    @Path("/admin/ajax/insurance/liability/find")
    @PUT
    public FindView<InsuranceLiabilityResponse> find(Request request) {
        LiabilityAdminAJAXRequest ajaxRequest = request.body(LiabilityAdminAJAXRequest.class);
        InsuranceLiabilityQuery query = new InsuranceLiabilityQuery();
        query.page = ajaxRequest.page;
        query.limit = ajaxRequest.limit;
        query.name = ajaxRequest.name;
        query.groupId = ajaxRequest.groupId;
        if (ajaxRequest.order != null && ajaxRequest.order.contains("-")) {
            query.desc = true;
            query.order = ajaxRequest.order.replace("-", "");
        } else {
            query.order = ajaxRequest.order;
            query.desc = false;
        }
        return insuranceLiabilityWebService.find(query);
    }

    @Path("/admin/ajax/insurance/liability/update/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        AdminUser adminUser = request.require(AdminUser.class);
        InsuranceLiabilityRequest insuranceLiabilityRequest = request.body(InsuranceLiabilityRequest.class);
        insuranceLiabilityRequest.requestBy = adminUser.id;
        insuranceLiabilityWebService.update(id, insuranceLiabilityRequest);
    }

    @Path("/admin/ajax/insurance/liability/delete/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        insuranceLiabilityWebService.delete(id);
    }

    @Path("/admin/ajax/insurance/liability/group/:groupId/count")
    @GET
    public Long groupCount(Request request) {
        String groupId = request.pathParam("groupId");
        return insuranceLiabilityWebService.groupCount(groupId);
    }

    @Path("/admin/ajax/insurance/liability/group")
    @POST
    public InsuranceLiabilityGroupAdminResponse createGroup(Request request) {
        InsuranceLiabilityGroupAdminRequest insuranceLiabilityGroupAdminRequest = request.body(InsuranceLiabilityGroupAdminRequest.class);
        AdminUser require = request.require(AdminUser.class, null);
        insuranceLiabilityGroupAdminRequest.requestBy = require.id;
        return insuranceLiabilityGroupWebService.create(insuranceLiabilityGroupAdminRequest);
    }

    @Path("/admin/ajax/insurance/liability/group/:id")
    @GET
    public InsuranceLiabilityGroupAdminResponse getGroup(Request request) {
        String id = request.pathParam("id");
        return insuranceLiabilityGroupWebService.get(id);
    }

    @Path("/admin/ajax/insurance/liability/group/:id")
    @PUT
    public void updateGroup(Request request) {
        InsuranceLiabilityGroupAdminRequest insuranceLiabilityGroupAdminRequest = request.body(InsuranceLiabilityGroupAdminRequest.class);
        AdminUser require = request.require(AdminUser.class, null);
        insuranceLiabilityGroupAdminRequest.requestBy = require.id;
        String id = request.pathParam("id");
        insuranceLiabilityGroupWebService.update(id, insuranceLiabilityGroupAdminRequest);
    }

    @Path("/admin/ajax/insurance/liability/group/:id")
    @DELETE
    public void deleteGroup(Request request) {
        String id = request.pathParam("id");
        insuranceLiabilityGroupWebService.delete(id);
    }

    @Path("/admin/ajax/insurance/liability/group/find")
    @PUT
    public FindView<InsuranceLiabilityGroupAdminResponse> findGroup(Request request) {
        LiabilityGroupAdminAJAXRequest ajaxRequest = request.body(LiabilityGroupAdminAJAXRequest.class);
        InsuranceLiabilityGroupQuery query = new InsuranceLiabilityGroupQuery();
        query.page = ajaxRequest.page;
        query.limit = ajaxRequest.limit;
        query.name = ajaxRequest.name;
        if (ajaxRequest.order != null && ajaxRequest.order.contains("-")) {
            query.desc = true;
            query.order = ajaxRequest.order.replace("-", "");
        } else {
            query.order = ajaxRequest.order;
            query.desc = false;
        }
        return insuranceLiabilityGroupWebService.find(query);
    }
}
