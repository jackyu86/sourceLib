package com.caej.admin.insurancecategory;

import java.util.List;
import javax.inject.Inject;
import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.BatchInsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.insurance.api.category.InsuranceCategoryQuery;
import com.caej.insurance.api.category.InsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryResponse;
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
public class InsuranceCategoryAdminController {
    @Inject
    InsuranceCategoryWebService insuranceCategoryWebService;

    @Path("/admin/ajax/insurance/category/tree")
    @GET
    public List<InsuranceCategoryNodeResponse> tree(Request request) {
        return insuranceCategoryWebService.tree();
    }

    @Path("/admin/ajax/insurance/category")
    @GET
    public List<InsuranceCategoryResponse> list(Request request) {
        return insuranceCategoryWebService.list();
    }

    @Path("/admin/ajax/insurance/category")
    @POST
    public void create(Request request) {
        InsuranceCategoryRequest insuranceCategoryRequest = request.body(InsuranceCategoryRequest.class);
        AdminUser current = request.require(AdminUser.class);
        insuranceCategoryRequest.requestBy = current.username;
        insuranceCategoryWebService.create(insuranceCategoryRequest);
    }

    @Path("/admin/ajax/insurance/category/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        InsuranceCategoryRequest insuranceCategoryRequest = request.body(InsuranceCategoryRequest.class);
        AdminUser current = request.require(AdminUser.class);
        insuranceCategoryRequest.requestBy = current.username;
        insuranceCategoryWebService.update(id, insuranceCategoryRequest);
    }

    @Path("/admin/ajax/insurance/category/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        insuranceCategoryWebService.delete(id);
    }

    @Path("/admin/ajax/insurance/category/find")
    @PUT
    public FindView<InsuranceCategoryResponse> find(Request request) {
        InsuranceCategoryQuery query = request.body(InsuranceCategoryQuery.class);
        return insuranceCategoryWebService.find(query);
    }

    @Path("/admin/ajax/insurance/category/batch/find")
    @PUT
    public List<InsuranceCategoryResponse> batch(Request request) {
        BatchInsuranceCategoryRequest batchInsuranceCategoryRequest = request.body(BatchInsuranceCategoryRequest.class);
        return insuranceCategoryWebService.batch(batchInsuranceCategoryRequest);
    }
}
