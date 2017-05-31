package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumChargeModeTypeWebService;
import com.caej.insurance.api.enumtype.EnumChargeModeTypeResponse;
import com.caej.insurance.domain.EnumChargeModeType;
import com.caej.insurance.service.EnumChargeModeTypeService;

/**
 * @author miller
 */
public class EnumChargeModeTypeWebServiceImpl implements EnumChargeModeTypeWebService {
    @Inject
    EnumChargeModeTypeService enumChargeModeTypeService;

    private EnumChargeModeTypeResponse response(EnumChargeModeType enumChargeModeType) {
        EnumChargeModeTypeResponse response = new EnumChargeModeTypeResponse();
        response.name = enumChargeModeType.name;
        response.value = enumChargeModeType.value;
        return response;
    }

    @Override
    public List<EnumChargeModeTypeResponse> findAll() {
        return enumChargeModeTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
