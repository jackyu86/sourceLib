package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumCertiType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumCertiTypeService {
    @Inject
    MongoRepository<EnumCertiType> repository;

    public List<EnumCertiType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
