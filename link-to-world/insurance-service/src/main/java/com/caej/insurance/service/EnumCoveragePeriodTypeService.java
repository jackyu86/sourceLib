package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumCoveragePeriodType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumCoveragePeriodTypeService {
    @Inject
    MongoRepository<EnumCoveragePeriodType> repository;

    public List<EnumCoveragePeriodType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
