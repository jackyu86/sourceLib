package com.caej.insurance.service;

import java.util.Map;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumPayMode;
import com.google.common.collect.Maps;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumPayModeService {
    @Inject
    MongoRepository<EnumPayMode> repository;

    public Map<String, String> findAllMap() {
        Map<String, String> map = Maps.newHashMap();
        repository.query().sort("display_order").find().forEach(enumPayMode -> {
            map.put(enumPayMode.name, enumPayMode.value);
        });
        return map;
    }
}
