package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.category.InsuranceCategoryQuery;
import com.caej.insurance.api.category.InsuranceCategoryRequest;
import com.caej.insurance.domain.InsuranceCategory;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.NotFoundException;

/**
 * @author miller
 */
public class InsuranceCategoryService {
    @Inject
    MongoRepository<InsuranceCategory> repository;

    public InsuranceCategory create(InsuranceCategoryRequest request) {
        InsuranceCategory category = new InsuranceCategory();
        category.parentId = request.parentId;
        category.name = request.name;
        category.displayOrder = request.displayOrder;
        category.recommended = request.recommended;
        category.description = request.description;
        category.createdBy = request.requestBy;
        category.updatedBy = request.requestBy;
        category.createdTime = LocalDateTime.now();
        category.updatedTime = LocalDateTime.now();
        repository.insert(category);
        return category;
    }

    public InsuranceCategory update(ObjectId id, InsuranceCategoryRequest request) {
        InsuranceCategory category = get(id);
        category.name = request.name;
        category.displayOrder = request.displayOrder;
        category.recommended = request.recommended;
        category.description = request.description;
        category.updatedBy = request.requestBy;
        category.updatedTime = LocalDateTime.now();
        repository.update(id, category);
        return category;
    }

    public InsuranceCategory get(ObjectId id) {
        return repository.get(id);
    }

    public void delete(ObjectId id) {
        if (!isExists(id)) {
            throw new NotFoundException("missing product category, id={}", id);
        }
        repository.delete(id);
    }

    private boolean isExists(ObjectId id) {
        return repository.query("_id", id).findOne().isPresent();
    }

    public List<InsuranceCategory> findChildrenById(ObjectId id) {
        return repository.query("parent_id", id)
            .sort("display_order").findMany();
    }

    public List<InsuranceCategory> findFirstLevel() {
        return repository.query("parent_id", null)
            .sort("display_order").findMany();
    }

    public FindView<InsuranceCategory> find(InsuranceCategoryQuery query) {
        Document filter = new Document();
        if (null != query.parentId) {
            filter.put("parent_id", query.parentId);
        }
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", new Document("$regex", query.name));
        }
        return repository.query(filter).sort("display_order").page(query.page).limit(query.limit).find();
    }

    public List<InsuranceCategory> findAll() {
        return repository.query().sort("display_order").limit(Integer.MAX_VALUE).findMany();
    }

    public List<InsuranceCategory> batch(List<ObjectId> objectIds) {
        Document filter = new Document("_id", new Document("$in", objectIds));
        return repository.query(filter).sort("display_order").limit(Integer.MAX_VALUE).findMany();
    }
}
