package com.caej.insurance.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.form.CreateInsuranceFormFieldRequest;
import com.caej.insurance.api.form.InsuranceFormFieldQuery;
import com.caej.insurance.api.form.InsuranceFormFieldResponse;
import com.caej.insurance.api.form.UpdateInsuranceFormFieldRequest;
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
public class InsuranceFormFieldWebServiceImpl implements InsuranceFormFieldWebService {
    @Inject
    InsuranceFormFieldService insuranceFormFieldService;

    @Inject
    InsuranceFormGroupService insuranceFormGroupService;

    @Override
    public InsuranceFormFieldResponse get(String id) {
        InsuranceFormField insuranceFormField = insuranceFormFieldService.get(new ObjectId(id));
        return response(insuranceFormField);
    }

    @Override
    public List<InsuranceFormFieldResponse> findByGroupName(String groupId) {
        List<InsuranceFormField> fields = insuranceFormFieldService.findByGroupId(new ObjectId(groupId));
        return fields.stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public InsuranceFormFieldResponse findByName(String groupName, String fieldName) {
        Optional<InsuranceFormGroup> insuranceFormGroup = insuranceFormGroupService.findByName(groupName);
        if (!insuranceFormGroup.isPresent()) {
            throw new NotFoundException("missing insurance form group, name={}", groupName);
        }
        Optional<InsuranceFormField> insuranceFormField = insuranceFormFieldService.findByGroupNameName(insuranceFormGroup.get().id, fieldName);
        if (!insuranceFormField.isPresent()) {
            throw new NotFoundException("missing insurance form field, group={}, name={}", groupName, fieldName);
        }
        return response(insuranceFormField.get());
    }

    @Override
    public FindView<InsuranceFormFieldResponse> find(String groupId, InsuranceFormFieldQuery query) {
        return FindView.map(insuranceFormFieldService.find(new ObjectId(groupId), query), this::response);
    }

    @Override
    public void update(@PathParam("id") String id, UpdateInsuranceFormFieldRequest request) {
        insuranceFormFieldService.update(new ObjectId(id), request);
    }

    @Override
    public void create(CreateInsuranceFormFieldRequest request) {
        insuranceFormFieldService.create(request);
    }

    public InsuranceFormFieldResponse response(InsuranceFormField insuranceFormField) {
        InsuranceFormFieldResponse response = new InsuranceFormFieldResponse();
        response.id = insuranceFormField.id;
        response.groupId = insuranceFormField.groupId;
        response.name = insuranceFormField.name;
        response.displayName = insuranceFormField.displayName;
        response.displayOrder = insuranceFormField.displayOrder;
        response.type = insuranceFormField.type;
        response.options = insuranceFormField.options;
        response.createdTime = insuranceFormField.createdTime;
        response.createdBy = insuranceFormField.createdBy;
        response.updatedTime = insuranceFormField.updatedTime;
        response.updatedBy = insuranceFormField.updatedBy;
        response.displayAs = insuranceFormField.displayAs;
        return response;
    }
}
