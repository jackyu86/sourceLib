package com.caej.insurance.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceClauseWebService;
import com.caej.insurance.api.clause.BatchGetInsuranceClauseRequest;
import com.caej.insurance.api.clause.CreateInsuranceClauseRequest;
import com.caej.insurance.api.clause.InsuranceClauseQuery;
import com.caej.insurance.api.clause.InsuranceClauseResponse;
import com.caej.insurance.api.clause.UpdateInsuranceClauseRequest;
import com.caej.insurance.domain.InsuranceClause;
import com.caej.insurance.service.InsuranceClauseService;

import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceClauseWebServiceImpl implements InsuranceClauseWebService {
    @Inject
    InsuranceClauseService insuranceClauseService;


    @Override
    public List<InsuranceClauseResponse> batch(BatchGetInsuranceClauseRequest request) {
        return insuranceClauseService.batchGet(request.ids).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public InsuranceClauseResponse get(@PathParam("id") String id) {
        InsuranceClause productTerm = insuranceClauseService.get(new ObjectId(id));
        return response(productTerm);
    }

    @Override
    public FindView<InsuranceClauseResponse> find(InsuranceClauseQuery query) {
        return FindView.map(insuranceClauseService.find(query), this::response);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceClauseService.delete(new ObjectId(id));
    }

    @Override
    public InsuranceClauseResponse create(CreateInsuranceClauseRequest request) {
        return response(insuranceClauseService.create(request));
    }

    @Override
    public void update(@PathParam("id") String id, UpdateInsuranceClauseRequest request) {
        insuranceClauseService.update(new ObjectId(id), request);
    }

    private InsuranceClauseResponse response(InsuranceClause insuranceClause) {
        InsuranceClauseResponse response = new InsuranceClauseResponse();
        response.serialNumber = insuranceClause.serialNumber;
        response.id = insuranceClause.id;
        response.type = insuranceClause.type;
        response.name = insuranceClause.name;
        response.serialNumber = insuranceClause.serialNumber;
        response.wordURL = insuranceClause.wordURL;
        response.pdfURL = insuranceClause.pdfURL;
        response.contentURL = insuranceClause.contentURL;
        response.exclusionsUrl = insuranceClause.exclusionsUrl;
        response.createdBy = insuranceClause.createdBy;
        response.createdTime = insuranceClause.createdTime;
        return response;
    }
}
