package com.caej.insurance.web;


import java.util.Map;

import javax.inject.Inject;

import com.caej.insurance.api.EnumPayModeWebService;
import com.caej.insurance.service.EnumPayModeService;

/**
 * @author miller
 */
public class EnumPayModeWebServiceImpl implements EnumPayModeWebService {
    @Inject
    EnumPayModeService enumPayModeService;

    @Override
    public Map<String, String> findAll() {
        return enumPayModeService.findAllMap();
    }
}
