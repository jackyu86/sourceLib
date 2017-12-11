package com.caej.insurance.web;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.InsuranceFormGroupQuery;
import com.caej.insurance.api.form.InsuranceFormGroupRequest;
import com.caej.insurance.api.form.InsuranceFormGroupResponse;
import com.caej.insurance.api.form.InsuranceFormQuery;
import com.caej.insurance.api.form.InsuranceFormResponse;
import com.caej.insurance.domain.InsuranceFormField;
import com.caej.insurance.domain.InsuranceFormGroup;
import com.caej.insurance.service.InsuranceFormFieldService;
import com.caej.insurance.service.InsuranceFormGroupService;

import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class InsuranceFormGroupWebServiceImpl implements InsuranceFormGroupWebService {
    @Inject
    InsuranceFormGroupService insuranceFormGroupService;
    @Inject
    InsuranceFormFieldService insuranceFormFieldService;

    @Override
    public InsuranceFormGroupResponse get(String id) {
        InsuranceFormGroup insuranceFormGroup = insuranceFormGroupService.get(new ObjectId(id));
        return response(insuranceFormGroup);
    }

    @Override
    public InsuranceFormGroupResponse getByName(String name) {
        Optional<InsuranceFormGroup> insuranceFormGroup = insuranceFormGroupService.findByName(name);
        if (!insuranceFormGroup.isPresent()) {
            throw new NotFoundException("missing insurance group, name={}", name);
        }
        return response(insuranceFormGroup.get());
    }

    @Override
    public InsuranceFormResponse get(InsuranceFormQuery query) {
        InsuranceFormResponse form = new InsuranceFormResponse();
        form.groups = query.groups.stream().map(formGroupRequest -> {
            com.caej.insurance.domain.InsuranceFormGroup insuranceFormGroup = insuranceFormGroupService.get(formGroupRequest.insuranceFormGroupId);

            InsuranceFormResponse.InsuranceFormGroupView insuranceFormGroupView = new InsuranceFormResponse.InsuranceFormGroupView();
            insuranceFormGroupView.insuranceFormGroup = response(insuranceFormGroup);

            insuranceFormGroupView.insuranceFormFields = insuranceFormFieldService.batchGet(formGroupRequest.insuranceFieldIds)
                .stream().map(this::fieldResponse).collect(Collectors.toList());
            return insuranceFormGroupView;
        }).collect(Collectors.toList());
        return form;
    }

    @Override
    public InsuranceFormResponse get() {
        InsuranceFormResponse response = new InsuranceFormResponse();
        response.groups = insuranceFormGroupService.findAll().stream().map(insuranceFormGroup -> {
            InsuranceFormResponse.InsuranceFormGroupView insuranceFormGroupView = new InsuranceFormResponse.InsuranceFormGroupView();
            insuranceFormGroupView.insuranceFormGroup = response(insuranceFormGroup);
            insuranceFormGroupView.insuranceFormFields = insuranceFormFieldService.findByGroupId(insuranceFormGroup.id)
                .stream().map(this::fieldResponse).collect(Collectors.toList());

            return insuranceFormGroupView;
        }).collect(Collectors.toList());
        return response;
    }

    @Override
    public FindView<InsuranceFormGroupResponse> find(InsuranceFormGroupQuery query) {
        return FindView.map(insuranceFormGroupService.find(query), this::response);
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceFormGroupRequest request) {
        insuranceFormGroupService.update(new ObjectId(id), request);
    }

    private InsuranceFormGroupResponse response(InsuranceFormGroup insuranceFormGroup) {
        InsuranceFormGroupResponse insuranceFormGroupResponse = new InsuranceFormGroupResponse();
        insuranceFormGroupResponse.id = insuranceFormGroup.id;
        insuranceFormGroupResponse.name = insuranceFormGroup.name;
        insuranceFormGroupResponse.displayName = insuranceFormGroup.displayName;
        insuranceFormGroupResponse.displayOrder = insuranceFormGroup.displayOrder;
        insuranceFormGroupResponse.required = insuranceFormGroup.required;
        insuranceFormGroupResponse.multiple = insuranceFormGroup.multiple;
        insuranceFormGroupResponse.description = insuranceFormGroup.description;
        insuranceFormGroupResponse.createdTime = insuranceFormGroup.createdTime;
        insuranceFormGroupResponse.createdBy = insuranceFormGroup.createdBy;
        insuranceFormGroupResponse.updatedTime = insuranceFormGroup.updatedTime;
        insuranceFormGroupResponse.updatedBy = insuranceFormGroup.updatedBy;
        return insuranceFormGroupResponse;
    }

    private InsuranceFormFieldResponse fieldResponse(InsuranceFormField insuranceFormField) {
        InsuranceFormFieldResponse response = new InsuranceFormFieldResponse();
        response.id = insuranceFormField.id;
        response.groupId = insuranceFormField.groupId;
        response.name = insuranceFormField.name;
        response.displayName = insuranceFormField.displayName;
        response.displayOrder = insuranceFormField.displayOrder;
        response.options = insuranceFormField.options;
        response.type = insuranceFormField.type;
        response.createdTime = insuranceFormField.createdTime;
        response.createdBy = insuranceFormField.createdBy;
        response.updatedTime = insuranceFormField.updatedTime;
        response.updatedBy = insuranceFormField.updatedBy;
        return response;
    }
}
