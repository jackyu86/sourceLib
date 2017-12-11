package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumOverManageTypeWebService;
import com.caej.insurance.api.enumtype.EnumOverManageTypeResponse;
import com.caej.insurance.domain.EnumOverManageType;
import com.caej.insurance.service.EnumOverManageTypeService;

/**
 * @author miller
 */
public class EnumOverManageTypeWebServiceImpl implements EnumOverManageTypeWebService {
    @Inject
    EnumOverManageTypeService enumOverManageTypeService;

    private EnumOverManageTypeResponse response(EnumOverManageType enumOverManageType) {
        EnumOverManageTypeResponse response = new EnumOverManageTypeResponse();
        response.name = enumOverManageType.name;
        response.value = enumOverManageType.value;
        return response;
    }

    @Override
    public List<EnumOverManageTypeResponse> findAll() {
        return enumOverManageTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
