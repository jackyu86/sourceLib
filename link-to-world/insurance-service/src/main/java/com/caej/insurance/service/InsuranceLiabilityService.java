package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceLiabilityQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiveBenefitView;
import com.caej.insurance.api.insurance.InsuranceRiskProtectionView;
import com.caej.insurance.domain.InsuranceLiability;
import com.caej.insurance.domain.InsuranceLiveBenefit;
import com.caej.insurance.domain.InsuranceRiskProtection;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class InsuranceLiabilityService {
    @Inject
    MongoRepository<InsuranceLiability> repository;

    public List<InsuranceLiability> batchGet(List<ObjectId> ids) {
        return repository.batchGet(ids);
    }

    public InsuranceLiability create(InsuranceLiabilityRequest request) {
        InsuranceLiability insuranceLiability = new InsuranceLiability();
        insuranceLiability.groupId = request.groupId;
        insuranceLiability.name = request.name;
        insuranceLiability.description = request.description;
        insuranceLiability.priority = request.priority;
        insuranceLiability.type = request.type;
        insuranceLiability.liveBenefit = liveBenefit(request.liveBenefit);
        insuranceLiability.riskProtection = riskProtection(request.riskProtection);
        insuranceLiability.createdTime = LocalDateTime.now();
        insuranceLiability.createdBy = request.requestBy;
        insuranceLiability.updatedTime = LocalDateTime.now();
        insuranceLiability.updatedBy = request.requestBy;
        repository.insert(insuranceLiability);
        return insuranceLiability;
    }

    private InsuranceLiveBenefit liveBenefit(InsuranceLiveBenefitView view) {
        InsuranceLiveBenefit liveBenefit = new InsuranceLiveBenefit();
        if (view != null) {
            liveBenefit.receiveType = view.receiveType;
            liveBenefit.receiveFrequencyType = view.receiveFrequencyType;
            liveBenefit.receiveTimes = view.receiveTimes;
            liveBenefit.receiveTimeType = view.receiveTimeType;
            liveBenefit.proportionEnabled = view.proportionEnabled;
            liveBenefit.proportion = view.proportion;
            liveBenefit.startTime = view.startTime;
            liveBenefit.createdTime = view.createdTime;
            liveBenefit.createdBy = view.createdBy;
            liveBenefit.updatedTime = view.updatedTime;
            liveBenefit.updatedBy = view.updatedBy;
        }
        return liveBenefit;
    }

    private InsuranceRiskProtection riskProtection(InsuranceRiskProtectionView view) {
        InsuranceRiskProtection riskProtection = new InsuranceRiskProtection();
        if (view != null) {
            riskProtection.type = view.type;
            riskProtection.createdTime = view.createdTime;
            riskProtection.createdBy = view.createdBy;
        }
        return riskProtection;
    }

    public FindView<InsuranceLiability> find(InsuranceLiabilityQuery query) {
        Document filter = new Document();
        if (query.groupId != null) {
            filter.put("group_id", query.groupId);
        }
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", new Document("$regex", query.name));
        }
        if (!Strings.isNullOrEmpty(query.order)) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void update(ObjectId id, InsuranceLiabilityRequest request) {
        InsuranceLiability origin = get(id);
        origin.name = request.name;
        origin.description = request.description;
        origin.priority = request.priority;
        origin.type = request.type;
        origin.liveBenefit = liveBenefit(request.liveBenefit);
        origin.riskProtection = riskProtection(request.riskProtection);
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public InsuranceLiability get(ObjectId id) {
        return repository.get(id);
    }

    public void delete(ObjectId id) {
        repository.delete(id);
    }

    public Long groupCount(ObjectId groupId) {
        return repository.query(new Document("group_id", groupId)).count();
    }
}
