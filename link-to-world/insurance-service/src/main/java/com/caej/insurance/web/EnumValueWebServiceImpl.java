package com.caej.insurance.web;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.insurance.api.EnumValueWebService;
import com.caej.insurance.api.enumtype.EnumTypeResponse;
import com.caej.insurance.api.enumtype.EnumTypeValueRequest;
import com.caej.insurance.api.enumtype.EnumValueResponse;
import com.caej.insurance.service.EnumTypeService;
import com.caej.insurance.service.EnumValueService;
import com.google.common.collect.Lists;

import io.sited.http.PathParam;

/**
 * @author chi
 */
public class EnumValueWebServiceImpl implements EnumValueWebService {
    @Inject
    EnumValueService enumValueService;
    @Inject
    EnumTypeService enumTypeService;

    @Override
    public List<EnumValueResponse> values(String name) {
        return enumValueService.values(name);
    }

    @Override
    public List<EnumValueResponse> valuesByType(@PathParam("type") String type) {
        Optional<EnumTypeResponse> optional = enumTypeService.getByType(type);
        if (!optional.isPresent()) return Lists.newArrayList();
        return enumValueService.values(optional.get().name);
    }

    @Override
    public EnumValueResponse create(String name, EnumTypeValueRequest request) {
        return enumValueService.create(name, request);
    }

    @Override
    public EnumValueResponse update(String name, String id, EnumTypeValueRequest request) {
        return enumValueService.update(name, id, request);
    }

    @Override
    public void delete(String name, String id) {
        enumValueService.delete(name, id);
    }
}
