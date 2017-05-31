package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumDeliverTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumDeliverTypeResponse;
import com.caej.insurance.api.enumtype.EnumDeliverTypeResponse;
import com.caej.insurance.domain.EnumDeliverType;
import com.caej.insurance.service.EnumDeliverTypeService;

/**
 * @author miller
 */
public class EnumDeliverTypeWebServiceImpl implements EnumDeliverTypeWebService {
    @Inject
    EnumDeliverTypeService enumDeliverTypeService;

    @Override
    public AllEnumDeliverTypeResponse findAll() {
        AllEnumDeliverTypeResponse response = new AllEnumDeliverTypeResponse();
        response.list = enumDeliverTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    private EnumDeliverTypeResponse response(EnumDeliverType enumDeliverType) {
        EnumDeliverTypeResponse response = new EnumDeliverTypeResponse();
        response.name = enumDeliverType.name;
        response.value = enumDeliverType.value;
        return response;
    }
}
