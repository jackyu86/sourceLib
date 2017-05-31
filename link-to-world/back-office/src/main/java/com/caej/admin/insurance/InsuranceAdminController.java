package com.caej.admin.insurance;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceWebService;
import com.caej.insurance.api.insurance.InsuranceAdminRequest;
import com.caej.insurance.api.insurance.InsuranceQuery;
import com.caej.insurance.api.insurance.InsuranceResponse;

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
public class InsuranceAdminController {
    @Inject
    InsuranceWebService insuranceWebService;

    @Path("/admin/ajax/insurance/find")
    @PUT
    public FindView<InsuranceResponse> find(Request request) {
        InsuranceQuery insuranceQuery = request.body(InsuranceQuery.class);
        insuranceQuery.desc = true;
        insuranceQuery.order = "created_time";
        return insuranceWebService.find(insuranceQuery);
    }

    @Path("/admin/ajax/insurance/:id")
    @GET
    public InsuranceResponse get(Request request) {
        String id = request.pathParam("id");
        return insuranceWebService.get(id);
    }

    @Path("/admin/ajax/insurance")
    @POST
    public InsuranceResponse create(Request request) {
        InsuranceAdminRequest insuranceAdminRequest = request.body(InsuranceAdminRequest.class);
        AdminUser adminUser = request.require(AdminUser.class);
        insuranceAdminRequest.requestBy = adminUser.id;
        return insuranceWebService.create(insuranceAdminRequest);
    }

    @Path("/admin/ajax/insurance/update/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        InsuranceAdminRequest insuranceAdminRequest = request.body(InsuranceAdminRequest.class);
        AdminUser adminUser = request.require(AdminUser.class);
        insuranceAdminRequest.requestBy = adminUser.id;
        insuranceWebService.update(id, insuranceAdminRequest);
    }

    @Path("/admin/ajax/insurance/delete/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        insuranceWebService.delete(id);
    }
}
