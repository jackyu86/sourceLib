package com.caej.insurance.service;

import java.util.List;
import javax.inject.Inject;
import com.caej.insurance.domain.EnumGenderType;
import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumGenderTypeService {
    @Inject
    MongoRepository<EnumGenderType> repository;

    public List<EnumGenderType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
