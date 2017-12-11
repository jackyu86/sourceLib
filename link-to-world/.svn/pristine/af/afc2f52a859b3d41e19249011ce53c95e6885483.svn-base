package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.domain.InsuranceProvince;

import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class InsuranceProvinceService {
    @Inject
    MongoRepository<InsuranceProvince> collection;

    public List<InsuranceProvince> findByProvinceId(ObjectId countryId) {
        return collection.query(new Document("countryId", countryId)).findMany();
    }
}
