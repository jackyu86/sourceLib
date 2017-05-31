package com.caej.insurance.web;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumYesOrNotTypeWebService;
import com.caej.insurance.api.enumtype.EnumYesOrNotTypeResponse;
import com.caej.insurance.domain.EnumYesOrNotType;
import com.caej.insurance.service.EnumYesOrNotTypeService;

/**
 * @author miller
 */
public class EnumYesOrNotTypeWebServiceImpl implements EnumYesOrNotTypeWebService {
    @Inject
    EnumYesOrNotTypeService enumYesOrNotTypeService;

    private EnumYesOrNotTypeResponse response(EnumYesOrNotType enumYesOrNotType) {
        EnumYesOrNotTypeResponse response = new EnumYesOrNotTypeResponse();
        response.name = enumYesOrNotType.name;
        response.value = enumYesOrNotType.value;
        return response;
    }

    @Override
    public List<EnumYesOrNotTypeResponse> findAll() {
        return enumYesOrNotTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
    }
}
