package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.domain.EnumOverManageType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumOverManageTypeService {
    @Inject
    MongoRepository<EnumOverManageType> repository;

    public List<EnumOverManageType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
