package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.form.InsuranceFormGroupQuery;
import com.caej.insurance.api.form.InsuranceFormGroupRequest;
import com.caej.insurance.domain.InsuranceFormGroup;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class InsuranceFormGroupService {
    @Inject
    MongoRepository<InsuranceFormGroup> repository;


    public List<InsuranceFormGroup> findAll() {
        return repository.query().findMany();
    }

    public InsuranceFormGroup get(ObjectId id) {
        return repository.get(id);
    }

    public void insert(InsuranceFormGroup insuranceFormGroup) {
        repository.insert(insuranceFormGroup);
    }

    public Optional<InsuranceFormGroup> findByName(String name) {
        return repository.query(new Document("name", name)).findOne();
    }

    public FindView<InsuranceFormGroup> find(InsuranceFormGroupQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.displayName)) {
            filter.put("display_name", new Document("$regex", query.displayName));
        }
        if (!Strings.isNullOrEmpty(query.order)) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void update(ObjectId id, InsuranceFormGroupRequest request) {
        InsuranceFormGroup origin = repository.get(id);
        origin.displayName = request.displayName;
        origin.displayOrder = request.displayOrder;
        origin.required = request.required;
        origin.multiple = request.multiple;
        origin.description = request.description;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }
}
