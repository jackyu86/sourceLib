package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumPolicyRelationType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumPolicyRelationTypeService {
    @Inject
    MongoRepository<EnumPolicyRelationType> repository;

    public List<EnumPolicyRelationType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
