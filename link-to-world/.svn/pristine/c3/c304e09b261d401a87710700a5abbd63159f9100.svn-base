package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumChargeModeType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumChargeModeTypeService {
    @Inject
    MongoRepository<EnumChargeModeType> repository;

    public List<EnumChargeModeType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
