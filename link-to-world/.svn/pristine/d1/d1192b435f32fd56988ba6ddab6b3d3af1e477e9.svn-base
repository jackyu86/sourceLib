package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumYesOrNotType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumYesOrNotTypeService {
    @Inject
    MongoRepository<EnumYesOrNotType> repository;

    public List<EnumYesOrNotType> findAll() {
        return repository.query().findMany();
    }
}
