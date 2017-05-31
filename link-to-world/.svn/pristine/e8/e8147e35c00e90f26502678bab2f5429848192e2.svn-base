package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.claim.InsuranceClaimQuery;
import com.caej.insurance.api.claim.InsuranceClaimRequest;
import com.caej.insurance.domain.InsuranceClaim;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class InsuranceClaimService {
    @Inject
    MongoRepository<InsuranceClaim> collection;

    public InsuranceClaim findById(ObjectId id) {
        Optional<InsuranceClaim> insuranceClaim = collection.query(new Document("_id", id)).findOne();
        return insuranceClaim.get();
    }

    public InsuranceClaim create(InsuranceClaimRequest request) {
        InsuranceClaim insuranceClaim = new InsuranceClaim();
        insuranceClaim.name = request.name;
        insuranceClaim.title = request.title;
        insuranceClaim.content = request.content;
        insuranceClaim.createdTime = LocalDateTime.now();
        insuranceClaim.createdBy = request.requestBy;
        collection.insert(insuranceClaim);
        return insuranceClaim;
    }

    public void update(ObjectId id, InsuranceClaim insuranceClaim) {
        collection.update(id, insuranceClaim);
    }

    public List<InsuranceClaim> batchGet(List<ObjectId> ids) {
        return collection.query(new Document("_id", new Document("$in", ids))).findMany();
    }

    public FindView<InsuranceClaim> find(InsuranceClaimQuery insuranceClaimQuery) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(insuranceClaimQuery.title)) {
            filter.put("title", new Document("$regex", insuranceClaimQuery.title));
        }
        return collection.query(filter).page(insuranceClaimQuery.page).limit(insuranceClaimQuery.limit).find();
    }

    public void delete(ObjectId id) {
        collection.delete(id);
    }
}
