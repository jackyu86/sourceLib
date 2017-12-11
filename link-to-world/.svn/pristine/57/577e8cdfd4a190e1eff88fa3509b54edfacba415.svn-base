package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumAccountType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumAccountTypeService {
    @Inject
    MongoRepository<EnumAccountType> repository;

    public List<EnumAccountType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
