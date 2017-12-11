package com.caej.insurance.web;

import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceLiabilityGroupWebService;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupResponseList;
import com.caej.insurance.domain.InsuranceLiabilityGroup;
import com.caej.insurance.service.InsuranceLiabilityGroupService;

import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public class InsuranceLiabilityGroupWebServiceImpl implements InsuranceLiabilityGroupWebService {
    @Inject
    InsuranceLiabilityGroupService insuranceLiabilityGroupService;

    @Override
    public InsuranceLiabilityGroupResponseList all() {
        InsuranceLiabilityGroupResponseList list = new InsuranceLiabilityGroupResponseList();
        list.list = insuranceLiabilityGroupService.findAll().stream().map(this::response).collect(Collectors.toList());
        return list;
    }

    @Override
    public InsuranceLiabilityGroupAdminResponse create(InsuranceLiabilityGroupAdminRequest request) {
        return adminResponse(insuranceLiabilityGroupService.create(request));
    }

    @Override
    public InsuranceLiabilityGroupAdminResponse get(@PathParam("id") String id) {
        return adminResponse(insuranceLiabilityGroupService.get(new ObjectId(id)));
    }

    @Override
    public FindView<InsuranceLiabilityGroupAdminResponse> find(InsuranceLiabilityGroupQuery query) {
        return FindView.map(insuranceLiabilityGroupService.find(query), this::adminResponse);
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceLiabilityGroupAdminRequest request) {
        insuranceLiabilityGroupService.update(new ObjectId(id), request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceLiabilityGroupService.delete(new ObjectId(id));
    }

    private InsuranceLiabilityGroupAdminResponse adminResponse(InsuranceLiabilityGroup group) {
        InsuranceLiabilityGroupAdminResponse response = new InsuranceLiabilityGroupAdminResponse();
        response.id = group.id;
        response.name = group.name;
        response.priority = group.priority;
        response.createdBy = group.createdBy;
        response.createdTime = group.createdTime;
        response.updatedBy = group.updatedBy;
        response.updatedTime = group.updatedTime;
        return response;
    }

    private InsuranceLiabilityGroupResponse response(InsuranceLiabilityGroup insuranceLiabilityGroup) {
        InsuranceLiabilityGroupResponse insuranceLiabilityGroupResponse = new InsuranceLiabilityGroupResponse();
        insuranceLiabilityGroupResponse.id = insuranceLiabilityGroup.id;
        insuranceLiabilityGroupResponse.name = insuranceLiabilityGroup.name;
        insuranceLiabilityGroupResponse.priority = insuranceLiabilityGroup.priority;
        insuranceLiabilityGroupResponse.createdTime = insuranceLiabilityGroup.createdTime;
        insuranceLiabilityGroupResponse.createdBy = insuranceLiabilityGroup.createdBy;
        insuranceLiabilityGroupResponse.updatedTime = insuranceLiabilityGroup.updatedTime;
        insuranceLiabilityGroupResponse.updatedBy = insuranceLiabilityGroup.updatedBy;
        return insuranceLiabilityGroupResponse;
    }

}
