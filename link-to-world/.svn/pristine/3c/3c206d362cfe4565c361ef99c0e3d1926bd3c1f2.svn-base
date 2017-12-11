package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumPolicyTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumPolicyTypeResponse;
import com.caej.insurance.api.enumtype.EnumPolicyTypeResponse;
import com.caej.insurance.domain.EnumPolicyType;
import com.caej.insurance.service.EnumPolicyTypeService;

/**
 * @author miller
 */
public class EnumPolicyTypeWebServiceImpl implements EnumPolicyTypeWebService {
    @Inject
    EnumPolicyTypeService enumPolicyTypeService;

    @Override
    public AllEnumPolicyTypeResponse findAll() {
        AllEnumPolicyTypeResponse response = new AllEnumPolicyTypeResponse();
        response.list = enumPolicyTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    private EnumPolicyTypeResponse response(EnumPolicyType enumPolicyType) {
        EnumPolicyTypeResponse response = new EnumPolicyTypeResponse();
        response.name = enumPolicyType.name;
        response.value = enumPolicyType.value;
        return response;
    }
}
