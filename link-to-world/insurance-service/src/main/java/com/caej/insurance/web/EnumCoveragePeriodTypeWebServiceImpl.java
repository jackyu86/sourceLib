package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumCoveragePeriodTypeWebService;
import com.caej.insurance.api.enumtype.EnumCoveragePeriodTypeResponse;
import com.caej.insurance.domain.EnumCoveragePeriodType;
import com.caej.insurance.service.EnumCoveragePeriodTypeService;

/**
 * @author miller
 */
public class EnumCoveragePeriodTypeWebServiceImpl implements EnumCoveragePeriodTypeWebService {
    @Inject
    EnumCoveragePeriodTypeService enumCoveragePeriodTypeService;

    private EnumCoveragePeriodTypeResponse response(EnumCoveragePeriodType enumCoveragePeriodType) {
        EnumCoveragePeriodTypeResponse response = new EnumCoveragePeriodTypeResponse();
        response.name = enumCoveragePeriodType.name;
        response.value = enumCoveragePeriodType.value;
        return response;
    }

    @Override
    public List<EnumCoveragePeriodTypeResponse> findAll() {
        return enumCoveragePeriodTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
