package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumBankType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumBankTypeService {
    @Inject
    MongoRepository<EnumBankType> repository;

    public List<EnumBankType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
