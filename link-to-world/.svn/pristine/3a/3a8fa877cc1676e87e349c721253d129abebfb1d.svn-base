package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.job.JobQuery;
import com.caej.insurance.api.job.JobRequest;
import com.caej.insurance.domain.InsuranceJob;
import com.google.common.base.Strings;

import io.sited.db.MongoRepository;
import io.sited.http.exception.BadRequestException;

/**
 * @author miller
 */
public class InsuranceJobService {
    @Inject
    MongoRepository<InsuranceJob> repository;

    public InsuranceJob create(JobRequest request) {
        if (!isCodeValid(request.jobTreeId, request.parentId, request.code)) {
            throw new BadRequestException("code", "job.error.duplicateCode");
        }
        InsuranceJob insuranceJob = new InsuranceJob();
        insuranceJob.jobTreeId = request.jobTreeId;
        insuranceJob.parentId = request.parentId;
        insuranceJob.displayName = request.displayName;
        insuranceJob.code = request.code;
        insuranceJob.riskLevel = request.riskLevel;
        insuranceJob.createdTime = LocalDateTime.now();
        insuranceJob.createdBy = request.requestBy;
        insuranceJob.updatedTime = LocalDateTime.now();
        insuranceJob.updatedBy = request.requestBy;
        repository.insert(insuranceJob);
        return insuranceJob;
    }

    private boolean isCodeValid(ObjectId treeId, ObjectId parentId, String code) {
        Document filter = new Document("job_tree_id", treeId)
            .append("parent_id", parentId)
            .append("code", code);
        return repository.query(filter).count() <= 0;
    }

    public InsuranceJob get(ObjectId id) {
        return repository.get(id);
    }

    public void update(ObjectId id, JobRequest request) {
        InsuranceJob origin = repository.get(id);
        origin.displayName = request.displayName;
        origin.code = request.code;
        origin.riskLevel = request.riskLevel;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public void delete(ObjectId id) {
        if (childrenCount(id) > 0) {
            throw new BadRequestException("id", "children exist");
        }
        repository.delete(id);
    }

    private long childrenCount(ObjectId parentId) {
        return repository.query(new Document("parent_id", parentId)).count();
    }

    public List<InsuranceJob> firstLevel(ObjectId jobTreeId) {
        return repository.query(new Document("job_tree_id", jobTreeId).append("parent_id", null)).findMany();
    }

    public List<InsuranceJob> children(ObjectId id) {
        return repository.query(new Document("parent_id", id)).findMany();
    }

    public void ancestor(ObjectId id, List<InsuranceJob> list) {
        InsuranceJob job = repository.get(id);
        list.add(job);
        if (job.parentId != null) {
            ancestor(job.parentId, list);
        }
    }

    public long count(ObjectId parentId) {
        return repository.query(new Document("parent_id", parentId)).count();
    }

    public List<InsuranceJob> findTreeAll(ObjectId jobTreeId) {
        return repository.query(new Document("job_tree_id", jobTreeId)).sort("code").findMany();
    }

    public List<InsuranceJob> find(ObjectId jobTreeId, JobQuery jobQuery) {
        Document query = new Document("job_tree_id", jobTreeId);
        if (!Strings.isNullOrEmpty(jobQuery.displayName)) {
            query.append("display_name", new Document("$regex", jobQuery.displayName));
        }
        if (jobQuery.levels != null && jobQuery.levels.size() > 0) {
            query.append("risk_level", new Document("$in", jobQuery.levels));
        }
        return repository.query(query).page(jobQuery.page).limit(jobQuery.limit).findMany();
    }

    public List<InsuranceJob> batchList(List<ObjectId> jobIds) {
        Document filter = new Document("_id", new Document("$in", jobIds));
        return repository.query(filter).sort("code").limit(Integer.MAX_VALUE).findMany();
    }
}
