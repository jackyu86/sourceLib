package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumOverdueOptionType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumOverdueOptionTypeService {
    @Inject
    MongoRepository<EnumOverdueOptionType> repository;

    public List<EnumOverdueOptionType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
