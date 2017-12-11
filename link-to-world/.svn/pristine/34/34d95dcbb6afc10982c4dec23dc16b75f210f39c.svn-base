package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumAccountTypeWebService;
import com.caej.insurance.api.enumtype.EnumAccountTypeResponse;
import com.caej.insurance.domain.EnumAccountType;
import com.caej.insurance.service.EnumAccountTypeService;

/**
 * @author miller
 */
public class EnumAccountTypeWebServiceImpl implements EnumAccountTypeWebService {
    @Inject
    EnumAccountTypeService enumAccountTypeService;

    private EnumAccountTypeResponse response(EnumAccountType enumAccountType) {
        EnumAccountTypeResponse response = new EnumAccountTypeResponse();
        response.name = enumAccountType.name;
        response.value = enumAccountType.value;
        return response;
    }

    @Override
    public List<EnumAccountTypeResponse> findAll() {
        return enumAccountTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
