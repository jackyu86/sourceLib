package com.caej.insurance.service;

import java.util.List;
import javax.inject.Inject;
import com.caej.insurance.domain.EnumDeliverType;
import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumDeliverTypeService {
    @Inject
    MongoRepository<EnumDeliverType> repository;

    public List<EnumDeliverType> findAll() {
        return repository.query().sort("display_order").findMany();
    }
}
