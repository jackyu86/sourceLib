package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumOverdueOptionTypeWebService;
import com.caej.insurance.api.enumtype.EnumOverdueOptionTypeResponse;
import com.caej.insurance.domain.EnumOverdueOptionType;
import com.caej.insurance.service.EnumOverdueOptionTypeService;

/**
 * @author miller
 */
public class EnumOverdueOptionTypeWebServiceImpl implements EnumOverdueOptionTypeWebService {
    @Inject
    EnumOverdueOptionTypeService enumOverdueOptionTypeService;

    private EnumOverdueOptionTypeResponse response(EnumOverdueOptionType enumOverdueOptionType) {
        EnumOverdueOptionTypeResponse response = new EnumOverdueOptionTypeResponse();
        response.name = enumOverdueOptionType.name;
        response.value = enumOverdueOptionType.value;
        return response;
    }

    @Override
    public List<EnumOverdueOptionTypeResponse> findAll() {
        return enumOverdueOptionTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
