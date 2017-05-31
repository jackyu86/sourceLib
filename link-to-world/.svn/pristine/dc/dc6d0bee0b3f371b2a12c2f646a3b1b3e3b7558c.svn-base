package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumBankTypeWebService;
import com.caej.insurance.api.enumtype.EnumBankTypeResponse;
import com.caej.insurance.domain.EnumBankType;
import com.caej.insurance.service.EnumBankTypeService;

/**
 * @author miller
 */
public class EnumBankTypeWebServiceImpl implements EnumBankTypeWebService {
    @Inject
    EnumBankTypeService enumBankTypeService;

    private EnumBankTypeResponse response(EnumBankType enumBankType) {
        EnumBankTypeResponse response = new EnumBankTypeResponse();
        response.name = enumBankType.name;
        response.value = enumBankType.value;
        return response;
    }

    @Override
    public List<EnumBankTypeResponse> findAll() {
        return enumBankTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
