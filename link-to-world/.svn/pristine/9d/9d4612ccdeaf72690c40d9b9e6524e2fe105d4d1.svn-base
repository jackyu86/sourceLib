package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumChargePeriodType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumChargePeriodTypeService {
    @Inject
    MongoRepository<EnumChargePeriodType> repository;

    public List<EnumChargePeriodType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
