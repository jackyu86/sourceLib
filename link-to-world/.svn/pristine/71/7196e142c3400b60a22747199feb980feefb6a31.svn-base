package com.caej.insurance.service;

import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.domain.InsuranceArea;

import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class InsuranceAreaService {
    @Inject
    MongoRepository<InsuranceArea> collection;

    public List<InsuranceArea> find() {
        return collection.query().findMany();
    }

    public List<InsuranceArea> batchGet(List<ObjectId> ids) {
        return collection.query(new Document("_id",new Document("$in",ids))).findMany();
    }
}
