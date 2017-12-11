package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumMarriageType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumMarriageTypeService {
    @Inject
    MongoRepository<EnumMarriageType> repository;

    public List<EnumMarriageType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
