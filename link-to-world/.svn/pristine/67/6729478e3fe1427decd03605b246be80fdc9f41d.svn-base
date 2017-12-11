package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.clause.CreateInsuranceClauseRequest;
import com.caej.insurance.api.clause.InsuranceClauseQuery;
import com.caej.insurance.api.clause.UpdateInsuranceClauseRequest;
import com.caej.insurance.domain.InsuranceClause;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.NotFoundException;

/**
 * @author miller
 */
public class InsuranceClauseService {
    @Inject
    MongoRepository<InsuranceClause> repository;

    public List<InsuranceClause> batchGet(List<ObjectId> idList) {
        return repository.batchGet(idList);
    }

    public FindView<InsuranceClause> find(InsuranceClauseQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", new Document("$regex", query.name));
        }
        if (!Strings.isNullOrEmpty(query.serialNumber)) {
            filter.put("serial_number", query.serialNumber);
        }
        return repository.query(filter).page(query.page).limit(query.limit).sort("created_time", true).find();
    }

    public void delete(ObjectId id) {
        if (!isExists(id)) {
            throw new NotFoundException("missing insurance clause, id={}", id);
        }
        repository.delete(id);
    }

    public boolean isExists(ObjectId id) {
        return repository.query("_id", id).findOne().isPresent();
    }

    public InsuranceClause create(CreateInsuranceClauseRequest request) {
        InsuranceClause insuranceClause = new InsuranceClause();
        insuranceClause.name = request.name;
        insuranceClause.type = request.type;
        insuranceClause.serialNumber = request.serialNumber;
        insuranceClause.contentURL = request.contentURL;
        insuranceClause.exclusionsUrl = request.exclusionsUrl;
        insuranceClause.wordURL = request.wordURL;
        insuranceClause.pdfURL = request.pdfURL;
        insuranceClause.createdTime = LocalDateTime.now();
        insuranceClause.updatedTime = LocalDateTime.now();
        insuranceClause.createdBy = request.requestBy;
        insuranceClause.updatedBy = request.requestBy;
        repository.insert(insuranceClause);
        return insuranceClause;
    }

    public void update(ObjectId id, UpdateInsuranceClauseRequest request) {
        InsuranceClause origin = get(id);
        origin.name = request.name;
        origin.type = request.type;
        origin.serialNumber = request.serialNumber;
        origin.contentURL = request.contentURL;
        origin.exclusionsUrl = request.exclusionsUrl;
        origin.wordURL = request.wordURL;
        origin.pdfURL = request.pdfURL;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public InsuranceClause get(ObjectId id) {
        return repository.get(id);
    }
}
