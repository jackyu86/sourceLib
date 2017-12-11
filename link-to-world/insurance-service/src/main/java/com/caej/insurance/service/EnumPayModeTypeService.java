package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumPayModeType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumPayModeTypeService {
    @Inject
    MongoRepository<EnumPayModeType> repository;

    public List<EnumPayModeType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
