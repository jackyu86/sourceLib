package com.caej.insurance.web;

import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumMarriageTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumMarriageTypeResponse;
import com.caej.insurance.api.enumtype.EnumMarriageTypeResponse;
import com.caej.insurance.domain.EnumMarriageType;
import com.caej.insurance.service.EnumMarriageTypeService;

/**
 * @author miller
 */
public class EnumMarriageTypeWebServiceImpl implements EnumMarriageTypeWebService {
    @Inject
    EnumMarriageTypeService enumMarriageTypeService;

    @Override
    public AllEnumMarriageTypeResponse findAll() {
        AllEnumMarriageTypeResponse response = new AllEnumMarriageTypeResponse();
        response.list = enumMarriageTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    private EnumMarriageTypeResponse response(EnumMarriageType enumMarriageType) {
        EnumMarriageTypeResponse response = new EnumMarriageTypeResponse();
        response.name = enumMarriageType.name;
        response.value = enumMarriageType.value;
        return response;
    }
}
