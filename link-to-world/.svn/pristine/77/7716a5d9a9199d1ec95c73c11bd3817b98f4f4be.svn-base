package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumLegalBeneficiaryTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumLegalBeneficiaryTypeResponse;
import com.caej.insurance.api.enumtype.EnumLegalBeneficiaryTypeResponse;
import com.caej.insurance.domain.EnumLegalBeneficiaryType;
import com.caej.insurance.service.EnumLegalBeneficiaryTypeService;

/**
 * @author miller
 */
public class EnumLegalBeneficiaryTypeWebServiceImpl implements EnumLegalBeneficiaryTypeWebService {
    @Inject
    EnumLegalBeneficiaryTypeService enumLegalBeneficiaryTypeService;

    private EnumLegalBeneficiaryTypeResponse response(EnumLegalBeneficiaryType enumLegalBeneficiaryType) {
        EnumLegalBeneficiaryTypeResponse response = new EnumLegalBeneficiaryTypeResponse();
        response.name = enumLegalBeneficiaryType.name;
        response.value = enumLegalBeneficiaryType.value;
        return response;
    }

    @Override
    public AllEnumLegalBeneficiaryTypeResponse findAll() {
        AllEnumLegalBeneficiaryTypeResponse response = new AllEnumLegalBeneficiaryTypeResponse();
        response.list = enumLegalBeneficiaryTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }
}
