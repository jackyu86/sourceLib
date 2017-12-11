package com.caej.insurance.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceLiabilityWebService;
import com.caej.insurance.api.insurance.BatchGetInsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;
import com.caej.insurance.api.insurance.InsuranceLiveBenefitView;
import com.caej.insurance.api.insurance.InsuranceRiskProtectionView;
import com.caej.insurance.domain.InsuranceLiability;
import com.caej.insurance.domain.InsuranceLiveBenefit;
import com.caej.insurance.domain.InsuranceRiskProtection;
import com.caej.insurance.service.InsuranceLiabilityService;

import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public class InsuranceLiabilityWebServiceImpl implements InsuranceLiabilityWebService {
    @Inject
    InsuranceLiabilityService insuranceLiabilityService;

    @Override
    public InsuranceLiabilityResponse get(String id) {
        InsuranceLiability insuranceLiability = insuranceLiabilityService.get(new ObjectId(id));
        return response(insuranceLiability);
    }

    @Override
    public List<InsuranceLiabilityResponse> batchGet(BatchGetInsuranceLiabilityRequest request) {
        return insuranceLiabilityService.batchGet(request.ids).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public InsuranceLiabilityResponse create(InsuranceLiabilityRequest request) {
        return response(insuranceLiabilityService.create(request));
    }

    @Override
    public FindView<InsuranceLiabilityResponse> find(InsuranceLiabilityQuery query) {
        return FindView.map(insuranceLiabilityService.find(query), this::response);
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceLiabilityRequest request) {
        insuranceLiabilityService.update(new ObjectId(id), request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceLiabilityService.delete(new ObjectId(id));
    }

    @Override
    public Long groupCount(@PathParam("groupId") String groupId) {
        return insuranceLiabilityService.groupCount(new ObjectId(groupId));
    }

    private InsuranceLiabilityResponse response(InsuranceLiability insuranceLiability) {
        InsuranceLiabilityResponse insuranceLiabilityResponse = new InsuranceLiabilityResponse();
        insuranceLiabilityResponse.id = insuranceLiability.id;
        insuranceLiabilityResponse.groupId = insuranceLiability.groupId;
        insuranceLiabilityResponse.name = insuranceLiability.name;
        insuranceLiabilityResponse.description = insuranceLiability.description;
        insuranceLiabilityResponse.priority = insuranceLiability.priority;
        insuranceLiabilityResponse.type = insuranceLiability.type;
        if (insuranceLiability.liveBenefit != null) {
            insuranceLiabilityResponse.liveBenefit = liveBenefitView(insuranceLiability.liveBenefit);
        }
        if (insuranceLiability.riskProtection != null) {
            insuranceLiabilityResponse.riskProtection = riskProtectionView(insuranceLiability.riskProtection);
        }
        insuranceLiabilityResponse.createdTime = insuranceLiability.createdTime;
        insuranceLiabilityResponse.createdBy = insuranceLiability.createdBy;
        insuranceLiabilityResponse.updatedTime = insuranceLiability.updatedTime;
        insuranceLiabilityResponse.updatedBy = insuranceLiability.updatedBy;
        return insuranceLiabilityResponse;
    }

    private InsuranceLiveBenefitView liveBenefitView(InsuranceLiveBenefit insuranceLiveBenefit) {
        InsuranceLiveBenefitView insuranceLiveBenefitView = new InsuranceLiveBenefitView();
        insuranceLiveBenefitView.receiveType = insuranceLiveBenefit.receiveType;
        insuranceLiveBenefitView.receiveFrequencyType = insuranceLiveBenefit.receiveFrequencyType;
        insuranceLiveBenefitView.receiveTimes = insuranceLiveBenefit.receiveTimes;
        insuranceLiveBenefitView.receiveTimeType = insuranceLiveBenefit.receiveTimeType;
        insuranceLiveBenefitView.proportionEnabled = insuranceLiveBenefit.proportionEnabled;
        insuranceLiveBenefitView.proportion = insuranceLiveBenefit.proportion;
        insuranceLiveBenefitView.startTime = insuranceLiveBenefit.startTime;
        insuranceLiveBenefitView.createdTime = insuranceLiveBenefit.createdTime;
        insuranceLiveBenefitView.createdBy = insuranceLiveBenefit.createdBy;
        insuranceLiveBenefitView.updatedTime = insuranceLiveBenefit.updatedTime;
        insuranceLiveBenefitView.updatedBy = insuranceLiveBenefit.updatedBy;
        return insuranceLiveBenefitView;
    }

    private InsuranceRiskProtectionView riskProtectionView(InsuranceRiskProtection insuranceRiskProtection) {
        InsuranceRiskProtectionView insuranceRiskProtectionView = new InsuranceRiskProtectionView();
        insuranceRiskProtectionView.type = insuranceRiskProtection.type;
        insuranceRiskProtectionView.createdTime = insuranceRiskProtection.createdTime;
        insuranceRiskProtectionView.createdBy = insuranceRiskProtection.createdBy;
        return insuranceRiskProtectionView;
    }
}
