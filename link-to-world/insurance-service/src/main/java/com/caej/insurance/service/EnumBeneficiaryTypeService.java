package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumBeneficiaryType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumBeneficiaryTypeService {
    @Inject
    MongoRepository<EnumBeneficiaryType> repository;

    public List<EnumBeneficiaryType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
