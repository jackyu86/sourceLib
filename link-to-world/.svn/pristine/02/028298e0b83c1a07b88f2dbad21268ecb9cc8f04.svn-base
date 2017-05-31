package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumCountWayTypeWebService;
import com.caej.insurance.api.enumtype.EnumCountWayTypeResponse;
import com.caej.insurance.domain.EnumCountWayType;
import com.caej.insurance.service.EnumCountWayTypeService;

/**
 * @author miller
 */
public class EnumCountWayTypeWebServiceImpl implements EnumCountWayTypeWebService {
    @Inject
    EnumCountWayTypeService enumCountWayTypeService;

    private EnumCountWayTypeResponse response(EnumCountWayType enumCountWayType) {
        EnumCountWayTypeResponse response = new EnumCountWayTypeResponse();
        response.name = enumCountWayType.name;
        response.value = enumCountWayType.value;
        return response;
    }

    @Override
    public List<EnumCountWayTypeResponse> findAll() {
        return enumCountWayTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
