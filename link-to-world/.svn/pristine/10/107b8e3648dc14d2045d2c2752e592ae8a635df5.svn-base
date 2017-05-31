package com.caej.insurance.web;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.insurance.api.EnumTypeWebService;
import com.caej.insurance.api.enumtype.EnumTypeResponse;
import com.caej.insurance.service.EnumTypeService;

import io.sited.http.PathParam;
import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class EnumTypeWebServiceImpl implements EnumTypeWebService {
    @Inject
    EnumTypeService enumTypeService;

    @Override
    public List<EnumTypeResponse> findAll() {
        return enumTypeService.findAll();
    }

    @Override
    public EnumTypeResponse get(@PathParam("name") String name) {
        Optional<EnumTypeResponse> enumTypeResponse = enumTypeService.get(name);
        if (!enumTypeResponse.isPresent()) {
            throw new NotFoundException("missing enum type, name={}", name);
        }
        return enumTypeResponse.get();
    }
}
