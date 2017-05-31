package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import org.bson.Document;

import com.caej.insurance.domain.EnumInvoiceDeliverType;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class EnumInvoiceDeliverTypeService {
    @Inject
    MongoRepository<EnumInvoiceDeliverType> repository;

    public List<EnumInvoiceDeliverType> findAll() {
        return repository.query().sort("display_order").findMany();
    }

    public Double price(String value) {
        return repository.query(new Document("value", value)).findOne().get().price;
    }
}
