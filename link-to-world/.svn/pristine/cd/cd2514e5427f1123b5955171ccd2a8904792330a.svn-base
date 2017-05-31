package com.caej.insurance.service;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.domain.InsuranceDeclaration;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class InsuranceDeclarationService {
    @Inject
    MongoRepository<InsuranceDeclaration> collection;

    public InsuranceDeclaration findById(ObjectId id) {
        return collection.query(new Document("_id", id)).findOne().get();
    }
}
