package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumPolicyType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumPolicyTypeService {
    @Inject
    MongoRepository<EnumPolicyType> repository;

    public List<EnumPolicyType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
