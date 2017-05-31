package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumPayModeTypeWebService;
import com.caej.insurance.api.enumtype.EnumPayModeTypeResponse;
import com.caej.insurance.domain.EnumPayModeType;
import com.caej.insurance.service.EnumPayModeTypeService;

/**
 * @author miller
 */
public class EnumPayModeTypeWebServiceImpl implements EnumPayModeTypeWebService {
    @Inject
    EnumPayModeTypeService enumPayModeTypeService;

    private EnumPayModeTypeResponse response(EnumPayModeType enumPayModeType) {
        EnumPayModeTypeResponse response = new EnumPayModeTypeResponse();
        response.name = enumPayModeType.name;
        response.value = enumPayModeType.value;
        return response;
    }

    @Override
    public List<EnumPayModeTypeResponse> findAll() {
        return enumPayModeTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
