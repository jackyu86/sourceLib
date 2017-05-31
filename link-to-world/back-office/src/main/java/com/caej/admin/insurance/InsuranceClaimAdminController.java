package com.caej.admin.insurance;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceClaimWebService;
import com.caej.insurance.api.claim.BatchGetInsuranceClaimRequest;
import com.caej.insurance.api.claim.InsuranceClaimQuery;
import com.caej.insurance.api.claim.InsuranceClaimRequest;
import com.caej.insurance.api.claim.InsuranceClaimResponse;
import com.caej.product.api.ProductWebService;

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
public class InsuranceClaimAdminController {
    @Inject
    InsuranceClaimWebService insuranceClaimWebService;
    @Inject
    ProductWebService productWebService;

    @Path("/admin/ajax/insurance/claim/batch")
    @PUT
    public List<InsuranceClaimResponse> batchGet(Request request) {
        BatchGetInsuranceClaimRequest batchGetInsuranceClaimRequest = request.body(BatchGetInsuranceClaimRequest.class);
        return insuranceClaimWebService.batchGet(batchGetInsuranceClaimRequest);
    }

    @Path("/admin/ajax/insurance/claim")
    @POST
    public InsuranceClaimResponse create(Request request) {
        InsuranceClaimRequest insuranceClaimRequest = request.body(InsuranceClaimRequest.class);
        return insuranceClaimWebService.create(insuranceClaimRequest);
    }

    @Path("/admin/ajax/insurance/claim/:id")
    @GET
    public InsuranceClaimResponse get(Request request) {
        String id = request.pathParam("id");
        return insuranceClaimWebService.get(id);
    }

    @Path("/admin/ajax/insurance/claim/find")
    @PUT
    public FindView<InsuranceClaimResponse> find(Request request) {
        InsuranceClaimQuery insuranceClaimQuery = request.body(InsuranceClaimQuery.class);
        return insuranceClaimWebService.find(insuranceClaimQuery);
    }

    @Path("/admin/ajax/insurance/claim/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        InsuranceClaimRequest insuranceClaimRequest = request.body(InsuranceClaimRequest.class);
        insuranceClaimWebService.update(id, insuranceClaimRequest);
    }

    @Path("/admin/ajax/insurance/claim/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        if (productWebService.countActiveInsuranceClaim(id) > 0) {
            throw new BadRequestException("insuranceClaimId", "存在关联的产品");
        }
        insuranceClaimWebService.delete(id);
    }
}
