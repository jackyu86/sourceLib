package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumBeneficiaryTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumBeneficiaryTypeResponse;
import com.caej.insurance.api.enumtype.EnumBeneficiaryTypeResponse;
import com.caej.insurance.domain.EnumBeneficiaryType;
import com.caej.insurance.service.EnumBeneficiaryTypeService;

/**
 * @author miller
 */
public class EnumBeneficiaryTypeWebServiceImpl implements EnumBeneficiaryTypeWebService {
    @Inject
    EnumBeneficiaryTypeService enumBeneficiaryTypeService;

    @Override
    public AllEnumBeneficiaryTypeResponse findAll() {
        AllEnumBeneficiaryTypeResponse response = new AllEnumBeneficiaryTypeResponse();
        response.list = enumBeneficiaryTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    private EnumBeneficiaryTypeResponse response(EnumBeneficiaryType enumBeneficiaryType) {
        EnumBeneficiaryTypeResponse response = new EnumBeneficiaryTypeResponse();
        response.name = enumBeneficiaryType.name;
        response.value = enumBeneficiaryType.value;
        return response;
    }
}
