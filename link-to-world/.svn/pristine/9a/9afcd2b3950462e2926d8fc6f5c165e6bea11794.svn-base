package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumChargePeriodTypeWebService;
import com.caej.insurance.api.enumtype.EnumChargePeriodTypeResponse;
import com.caej.insurance.domain.EnumChargePeriodType;
import com.caej.insurance.service.EnumChargePeriodTypeService;

/**
 * @author miller
 */
public class EnumChargePeriodTypeWebServiceImpl implements EnumChargePeriodTypeWebService {
    @Inject
    EnumChargePeriodTypeService enumChargePeriodTypeService;

    private EnumChargePeriodTypeResponse response(EnumChargePeriodType enumChargePeriodType) {
        EnumChargePeriodTypeResponse response = new EnumChargePeriodTypeResponse();
        response.name = enumChargePeriodType.name;
        response.value = enumChargePeriodType.value;
        return response;
    }

    @Override
    public List<EnumChargePeriodTypeResponse> findAll() {
        return enumChargePeriodTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
