package com.caej.admin.insuranceclause;

import java.util.List;

import javax.inject.Inject;
import com.caej.insurance.api.InsuranceClauseWebService;
import com.caej.insurance.api.clause.BatchGetInsuranceClauseRequest;
import com.caej.insurance.api.clause.CreateInsuranceClauseRequest;
import com.caej.insurance.api.clause.InsuranceClauseQuery;
import com.caej.insurance.api.clause.InsuranceClauseResponse;
import com.caej.insurance.api.clause.UpdateInsuranceClauseRequest;
import com.caej.product.api.ProductWebService;
import io.sited.admin.AdminUser;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.exception.BadRequestException;

/**
 * @author miller
 */
public class InsuranceClauseAdminController {
    @Inject
    InsuranceClauseWebService insuranceClauseWebService;
    @Inject
    ProductWebService productWebService;

    @Path("/admin/ajax/clause")
    @POST
    public InsuranceClauseResponse create(Request request) {
        CreateInsuranceClauseRequest createInsuranceClauseRequest = request.body(CreateInsuranceClauseRequest.class);
        AdminUser current = request.require(AdminUser.class);
        createInsuranceClauseRequest.requestBy = current.username;
        return insuranceClauseWebService.create(createInsuranceClauseRequest);
    }

    @Path("/admin/ajax/clause/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        if (productWebService.countActiveInsuranceClause(id) > 0) {
            throw new BadRequestException("insuranceClauseId", "存在关联的产品");
        }
        insuranceClauseWebService.delete(id);
    }

    @Path("/admin/ajax/clause/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        UpdateInsuranceClauseRequest updateInsuranceClauseRequest = request.body(UpdateInsuranceClauseRequest.class);
        AdminUser current = request.require(AdminUser.class);
        updateInsuranceClauseRequest.requestBy = current.username;
        insuranceClauseWebService.update(id, updateInsuranceClauseRequest);
    }

    @Path("/admin/ajax/clause/:id")
    @GET
    public InsuranceClauseResponse get(Request request) {
        String id = request.pathParam("id");
        return insuranceClauseWebService.get(id);
    }

    @Path("/admin/ajax/clause/find")
    @PUT
    public FindView<InsuranceClauseResponse> find(Request request) {
        InsuranceClauseAJAXRequest insuranceClauseAJAXRequest = request.body(InsuranceClauseAJAXRequest.class);
        InsuranceClauseQuery insuranceClauseQuery = new InsuranceClauseQuery();
        insuranceClauseQuery.page = insuranceClauseAJAXRequest.page;
        insuranceClauseQuery.limit = insuranceClauseAJAXRequest.limit;
        insuranceClauseQuery.name = insuranceClauseAJAXRequest.name;
        return insuranceClauseWebService.find(insuranceClauseQuery);
    }

    @Path("/admin/ajax/clause/batch")
    @PUT
    public List<InsuranceClauseResponse> batch(Request request) {
        BatchGetInsuranceClauseRequest batchGetInsuranceClauseRequest = request.body(BatchGetInsuranceClauseRequest.class);
        return insuranceClauseWebService.batch(batchGetInsuranceClauseRequest);
    }

}
