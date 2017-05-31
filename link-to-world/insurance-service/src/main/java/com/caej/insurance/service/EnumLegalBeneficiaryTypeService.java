package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumLegalBeneficiaryType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumLegalBeneficiaryTypeService {
    @Inject
    MongoRepository<EnumLegalBeneficiaryType> repository;

    public List<EnumLegalBeneficiaryType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
