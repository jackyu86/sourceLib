package com.caej.insurance.web;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceClaimWebService;
import com.caej.insurance.api.claim.BatchCreateInsuranceClaimRequest;
import com.caej.insurance.api.claim.BatchGetInsuranceClaimRequest;
import com.caej.insurance.api.claim.CreateInsuranceClaimRequest;
import com.caej.insurance.api.claim.InsuranceClaimQuery;
import com.caej.insurance.api.claim.InsuranceClaimRequest;
import com.caej.insurance.api.claim.InsuranceClaimResponse;
import com.caej.insurance.domain.InsuranceClaim;
import com.caej.insurance.service.InsuranceClaimService;
import com.google.common.collect.Lists;

import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceClaimWebServiceImpl implements InsuranceClaimWebService {
    @Inject
    InsuranceClaimService insuranceClaimService;

    @Override
    public InsuranceClaimResponse get(String id) {
        InsuranceClaim claimGuide = insuranceClaimService.findById(new ObjectId(id));
        return response(claimGuide);
    }

    @Override
    public InsuranceClaimResponse create(InsuranceClaimRequest request) {
        return response(insuranceClaimService.create(request));
    }

    @Override
    public FindView<InsuranceClaimResponse> find(InsuranceClaimQuery query) {
        return FindView.map(insuranceClaimService.find(query), this::response);
    }

    @Override
    public List<InsuranceClaimResponse> batchCreateOrUpdate(BatchCreateInsuranceClaimRequest request) {
        List<InsuranceClaimResponse> result = Lists.newArrayList();
        for (CreateInsuranceClaimRequest claim : request.list) {
            if (claim.id != null) {
                InsuranceClaim origin = insuranceClaimService.findById(claim.id);
                origin.name = claim.name;
                origin.title = claim.title;
                origin.content = claim.content;
                origin.updatedTime = LocalDateTime.now();
                origin.updatedBy = claim.requestBy;
                insuranceClaimService.update(claim.id, origin);
                result.add(response(origin));
            } else {
                result.add(response(insuranceClaimService.create(request(claim))));
            }
        }
        return result;
    }

    private InsuranceClaimRequest request(CreateInsuranceClaimRequest create) {
        InsuranceClaimRequest request = new InsuranceClaimRequest();
        request.name = create.name;
        request.title = create.title;
        request.content = create.content;
        request.requestBy = create.requestBy;
        return request;
    }

    @Override
    public List<InsuranceClaimResponse> batchGet(BatchGetInsuranceClaimRequest request) {
        if (request.ids == null) {
            return Collections.emptyList();
        }
        return insuranceClaimService.batchGet(request.ids).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceClaimRequest request) {
        InsuranceClaim origin = insuranceClaimService.findById(new ObjectId(id));
        origin.name = request.name;
        origin.title = request.title;
        origin.content = request.content;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        insuranceClaimService.update(new ObjectId(id), origin);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceClaimService.delete(new ObjectId(id));
    }


    private InsuranceClaimResponse response(InsuranceClaim insuranceClaim) {
        InsuranceClaimResponse response = new InsuranceClaimResponse();
        response.id = insuranceClaim.id;
        response.name = insuranceClaim.name;
        response.title = insuranceClaim.title;
        response.content = insuranceClaim.content;
        response.createdTime = insuranceClaim.createdTime;
        response.createdBy = insuranceClaim.createdBy;
        return response;
    }
}
