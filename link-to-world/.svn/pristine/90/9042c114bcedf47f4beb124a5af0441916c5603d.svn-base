package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumGenderTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumGenderTypeResponse;
import com.caej.insurance.api.enumtype.EnumGenderTypeResponse;
import com.caej.insurance.domain.EnumGenderType;
import com.caej.insurance.service.EnumGenderTypeService;

/**
 * @author miller
 */
public class EnumGenderTypeWebServiceImpl implements EnumGenderTypeWebService {
    @Inject
    EnumGenderTypeService enumGenderTypeService;

    @Override
    public AllEnumGenderTypeResponse findAll() {
        AllEnumGenderTypeResponse response = new AllEnumGenderTypeResponse();
        response.list= enumGenderTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    private EnumGenderTypeResponse response(EnumGenderType enumGenderType) {
        EnumGenderTypeResponse response = new EnumGenderTypeResponse();
        response.name = enumGenderType.name;
        response.value = enumGenderType.value;
        return response;
    }
}
