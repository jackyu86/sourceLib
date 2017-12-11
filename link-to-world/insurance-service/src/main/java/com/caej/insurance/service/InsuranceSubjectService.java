package com.caej.insurance.service;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.subject.InsuranceSubjectQuery;
import com.caej.insurance.domain.InsuranceSubject;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.NotFoundException;

/**
 * @author miller
 */
public class InsuranceSubjectService {
    @Inject
    MongoRepository<InsuranceSubject> repository;

    public InsuranceSubject get(ObjectId id) {
        return repository.get(id);
    }

    public FindView<InsuranceSubject> find(InsuranceSubjectQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", query.name);
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void delete(ObjectId id) {
        if (!isExists(id)) {
            throw new NotFoundException("missing insurance subject,id={}", id);
        }
        repository.delete(id);
    }

    public boolean isExists(ObjectId id) {
        return repository.query("_id", id).findOne().isPresent();
    }
}
