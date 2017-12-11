package com.caej.admin.vendor;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.vendor.BatchDeleteInsuranceVendorRequest;
import com.caej.insurance.api.vendor.CreateInsuranceVendorRequest;
import com.caej.insurance.api.vendor.InsuranceVendorQuery;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.insurance.api.vendor.UpdateInsuranceVendorRequest;

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
public class VendorAdminController {
    @Inject
    InsuranceVendorWebService insuranceVendorWebService;

    @Path("/admin/ajax/vendor/find")
    @PUT
    public FindView<InsuranceVendorResponse> find(Request request) {
        VendorAJAXRequest vendorAJAXRequest = request.body(VendorAJAXRequest.class);
        InsuranceVendorQuery query = new InsuranceVendorQuery();
        query.name = vendorAJAXRequest.name;
        query.level = vendorAJAXRequest.level;
        query.page = vendorAJAXRequest.page;
        query.limit = vendorAJAXRequest.limit;
        if (vendorAJAXRequest.order.contains("-")) {
            query.order = "created_time";
            query.desc = true;
        } else {
            query.order = "created_time";
            query.desc = false;
        }
        return insuranceVendorWebService.find(query);
    }

    @Path("/admin/ajax/vendor/:id")
    @GET
    public InsuranceVendorResponse get(Request request) {
        String id = request.pathParam("id");
        return insuranceVendorWebService.get(id);
    }

    @Path("/admin/ajax/vendor/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        UpdateInsuranceVendorRequest updateInsuranceVendorRequest = request.body(UpdateInsuranceVendorRequest.class);
        AdminUser current = request.require(AdminUser.class);
        updateInsuranceVendorRequest.requestBy = current.username;
        insuranceVendorWebService.update(id, updateInsuranceVendorRequest);
    }

    @Path("/admin/ajax/vendor")
    @POST
    public InsuranceVendorResponse create(Request request) {
        CreateInsuranceVendorRequest createInsuranceVendorRequest = request.body(CreateInsuranceVendorRequest.class);
        AdminUser current = request.require(AdminUser.class);
        createInsuranceVendorRequest.requestBy = current.username;
        return insuranceVendorWebService.create(createInsuranceVendorRequest);
    }

    @Path("/admin/ajax/vendor/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        insuranceVendorWebService.delete(id);
    }

    @Path("/admin/ajax/vendor/delete")
    @PUT
    public void batchDelete(Request request) {
        BatchDeleteInsuranceVendorRequest batchDeleteInsuranceVendorRequest = request.body(BatchDeleteInsuranceVendorRequest.class);
        insuranceVendorWebService.batchDelete(batchDeleteInsuranceVendorRequest);
    }
}
