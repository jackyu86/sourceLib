package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumCountWayType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumCountWayTypeService {
    @Inject
    MongoRepository<EnumCountWayType> repository;

    public List<EnumCountWayType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
