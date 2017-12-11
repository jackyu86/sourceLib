package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumCertiTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumCertiTypeResponse;
import com.caej.insurance.api.enumtype.EnumCertiTypeResponse;
import com.caej.insurance.domain.EnumCertiType;
import com.caej.insurance.service.EnumCertiTypeService;

/**
 * @author miller
 */
public class EnumCertiTypeWebServiceImpl implements EnumCertiTypeWebService {
    @Inject
    EnumCertiTypeService enumCertiTypeService;

    @Override
    public AllEnumCertiTypeResponse findAll() {
        AllEnumCertiTypeResponse response = new AllEnumCertiTypeResponse();
        response.list = enumCertiTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    private EnumCertiTypeResponse response(EnumCertiType enumCertiType) {
        EnumCertiTypeResponse response = new EnumCertiTypeResponse();
        response.name = enumCertiType.name;
        response.value = enumCertiType.value;
        response.maxLength = enumCertiType.maxLength;
        response.minLength = enumCertiType.minLength;
        return response;
    }
}
