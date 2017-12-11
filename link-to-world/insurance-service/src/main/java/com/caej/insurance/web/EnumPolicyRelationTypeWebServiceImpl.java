package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumPolicyRelationTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumPolicyRelationTypeResponse;
import com.caej.insurance.api.enumtype.EnumPolicyRelationTypeResponse;
import com.caej.insurance.domain.EnumPolicyRelationType;
import com.caej.insurance.service.EnumPolicyRelationTypeService;

/**
 * @author miller
 */
public class EnumPolicyRelationTypeWebServiceImpl implements EnumPolicyRelationTypeWebService {
    @Inject
    EnumPolicyRelationTypeService enumPolicyRelationTypeService;

    @Override
    public AllEnumPolicyRelationTypeResponse findAll() {
        AllEnumPolicyRelationTypeResponse response = new AllEnumPolicyRelationTypeResponse();
        response.list = enumPolicyRelationTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    private EnumPolicyRelationTypeResponse response(EnumPolicyRelationType enumPolicyRelationType) {
        EnumPolicyRelationTypeResponse response = new EnumPolicyRelationTypeResponse();
        response.name = enumPolicyRelationType.name;
        response.value = enumPolicyRelationType.value;
        return response;
    }
}
