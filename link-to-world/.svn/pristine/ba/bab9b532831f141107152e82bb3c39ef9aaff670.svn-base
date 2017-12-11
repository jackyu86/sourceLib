package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceAreaWebService;
import com.caej.insurance.api.area.BatchGetAreaRequest;
import com.caej.insurance.api.area.InsuranceAreaResponse;
import com.caej.insurance.domain.InsuranceArea;
import com.caej.insurance.service.InsuranceAreaService;

/**
 * @author chi
 */
public class InsuranceAreaWebServiceImpl implements InsuranceAreaWebService {
    @Inject
    InsuranceAreaService insuranceAreaService;

    @Override
    public List<InsuranceAreaResponse> find() {
        return insuranceAreaService.find().stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceAreaResponse> batchGet(BatchGetAreaRequest request) {
        return insuranceAreaService.batchGet(request.areaIds).stream().map(this::response).collect(Collectors.toList());
    }

    private InsuranceAreaResponse response(InsuranceArea insuranceArea) {
        InsuranceAreaResponse response = new InsuranceAreaResponse();
        response.id = insuranceArea.id
        ;
        response.areaCode = insuranceArea.areaCode;
        response.displayName = insuranceArea.displayName;
        response.displayOrder = insuranceArea.displayOrder;
        response.englishName = insuranceArea.englishName;
        response.pinYin = insuranceArea.pinYin;
        response.createdTime = insuranceArea.createdTime;
        response.createdBy = insuranceArea.createdBy;
        return response;
    }
}
