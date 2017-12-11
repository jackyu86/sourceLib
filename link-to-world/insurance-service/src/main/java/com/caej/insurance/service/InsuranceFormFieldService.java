package com.caej.insurance.service;

import static java.time.LocalDateTime.now;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.form.CreateInsuranceFormFieldRequest;
import com.caej.insurance.api.form.InsuranceFormFieldQuery;
import com.caej.insurance.api.form.UpdateInsuranceFormFieldRequest;
import com.caej.insurance.domain.InsuranceFormField;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class InsuranceFormFieldService {
    @Inject
    MongoRepository<InsuranceFormField> repository;

    public FindView<InsuranceFormField> findAll() {
        return repository.query().find();
    }

    public InsuranceFormField get(ObjectId id) {
        return repository.get(id);
    }

    public List<InsuranceFormField> findByGroupId(ObjectId groupId) {
        return repository.query("group_id", groupId).limit(Integer.MAX_VALUE).findMany();
    }

    public List<InsuranceFormField> batchGet(List<ObjectId> ids) {
        return repository.batchGet(ids);
    }

    public void insert(InsuranceFormField insuranceFormField) {
        repository.insert(insuranceFormField);
    }

    public Optional<InsuranceFormField> findByGroupNameName(ObjectId groupId, String name) {
        return repository.query(new Document("group_id", groupId).append("name", name)).findOne();
    }

    public FindView<InsuranceFormField> find(ObjectId groupId, InsuranceFormFieldQuery query) {
        Document filter = new Document("group_id", groupId);
        if (!Strings.isNullOrEmpty(query.displayName)) {
            filter.put("display_name", new Document("$regex", query.displayName));
        }
        if (!Strings.isNullOrEmpty(query.order)) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void update(ObjectId id, UpdateInsuranceFormFieldRequest request) {
        InsuranceFormField origin = repository.get(id);
        origin.displayOrder = request.displayOrder;
        origin.displayName = request.displayName;
        origin.displayAs = request.displayAs;
        origin.options = request.options;
        origin.updatedTime = now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public void create(CreateInsuranceFormFieldRequest request) {
        InsuranceFormField insuranceFormField = new InsuranceFormField();
        insuranceFormField.type = request.type;
        insuranceFormField.displayOrder = request.displayOrder;
        insuranceFormField.groupId = new ObjectId(request.groupId);
        insuranceFormField.name = request.name;
        insuranceFormField.displayName = request.displayName;
        insuranceFormField.displayAs = request.displayAs;
        insuranceFormField.options = request.options;
        insuranceFormField.createdBy = request.requestBy;
        insuranceFormField.createdTime = now();
        repository.insert(insuranceFormField);
    }
}
