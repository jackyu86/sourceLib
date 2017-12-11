package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupQuery;
import com.caej.insurance.domain.InsuranceLiabilityGroup;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class InsuranceLiabilityGroupService {
    @Inject
    MongoRepository<InsuranceLiabilityGroup> repository;

    public List<InsuranceLiabilityGroup> findAll() {
        return repository.query().findMany();
    }

    public InsuranceLiabilityGroup create(InsuranceLiabilityGroupAdminRequest request) {
        InsuranceLiabilityGroup group = new InsuranceLiabilityGroup();
        group.name = request.name;
        group.priority = request.priority;
        group.createdBy = request.requestBy;
        group.updatedBy = request.requestBy;
        group.createdTime = LocalDateTime.now();
        group.updatedTime = LocalDateTime.now();
        repository.insert(group);
        return group;
    }

    public void update(ObjectId id, InsuranceLiabilityGroupAdminRequest request) {
        InsuranceLiabilityGroup origin = get(id);
        origin.name = request.name;
        origin.priority = request.priority;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public InsuranceLiabilityGroup get(ObjectId id) {
        return repository.get(id);
    }

    public FindView<InsuranceLiabilityGroup> find(InsuranceLiabilityGroupQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", new Document("$regex", query.name));
        }
        if (!Strings.isNullOrEmpty(query.order)) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void delete(ObjectId id) {
        repository.delete(id);
    }
}
