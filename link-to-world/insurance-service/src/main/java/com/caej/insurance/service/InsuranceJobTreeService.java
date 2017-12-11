package com.caej.insurance.service;

import java.time.LocalDateTime;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.job.JobTreeQuery;
import com.caej.insurance.api.job.JobTreeRequest;
import com.caej.insurance.domain.InsuranceJobTree;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class InsuranceJobTreeService {
    @Inject
    MongoRepository<InsuranceJobTree> repository;

    public InsuranceJobTree create(JobTreeRequest request) {
        InsuranceJobTree jobTree = new InsuranceJobTree();
        jobTree.name = request.name;
        jobTree.description = request.description;
        jobTree.createdTime = LocalDateTime.now();
        jobTree.updatedTime = LocalDateTime.now();
        jobTree.createdBy = request.requestBy;
        jobTree.updatedBy = request.requestBy;
        repository.insert(jobTree);
        return jobTree;
    }

    public FindView<InsuranceJobTree> find(JobTreeQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", new Document("$regex", query.name));
        }
        if (!Strings.isNullOrEmpty(query.order)) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public InsuranceJobTree get(ObjectId id) {
        return repository.get(id);
    }

    public void update(ObjectId id, JobTreeRequest request) {
        InsuranceJobTree origin = repository.get(id);
        origin.name = request.name;
        origin.description = request.description;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public void delete(ObjectId id) {
        repository.delete(id);
    }

}
