package com.caej.insurance.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.insurance.api.InsuranceJobWebService;
import com.caej.insurance.api.job.BatchJobRequest;
import com.caej.insurance.api.job.JobNodeResponse;
import com.caej.insurance.api.job.JobQuery;
import com.caej.insurance.api.job.JobRequest;
import com.caej.insurance.api.job.JobResponse;
import com.caej.insurance.api.job.JobResponseList;
import com.caej.insurance.domain.InsuranceJob;
import com.caej.insurance.service.InsuranceJobService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceJobWebServiceImpl implements InsuranceJobWebService {
    private final Logger logger = LoggerFactory.getLogger(InsuranceJobWebServiceImpl.class);
    @Inject
    InsuranceJobService insuranceJobService;

    @Override
    public JobResponse create(JobRequest request) {
        return response(insuranceJobService.create(request));
    }

    @Override
    public JobResponse get(@PathParam("id") String id) {
        return response(insuranceJobService.get(new ObjectId(id)));
    }

    @Override
    public JobResponseList firstLevel(String treeId) {
        JobResponseList list = new JobResponseList();
        list.list = insuranceJobService.firstLevel(new ObjectId(treeId)).stream().map(this::response).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<JobResponse> children(String id) {
        return insuranceJobService.children(new ObjectId(id)).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> ancestor(@PathParam("id") String id) {
        List<InsuranceJob> list = Lists.newArrayList();
        insuranceJobService.ancestor(new ObjectId(id), list);
        return Lists.reverse(list).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public List<JobNodeResponse> tree(@PathParam("treeId") String treeId) {
        List<InsuranceJob> jobs = insuranceJobService.findTreeAll(new ObjectId(treeId));
        Map<ObjectId, JobNodeResponse> index = Maps.newHashMap();
        List<JobNodeResponse> firstLevels = Lists.newArrayList();
        tree(jobs, index, firstLevels);
        return firstLevels;
    }

    @Override
    public List<JobResponse> findJob(@PathParam("treeId") String treeId, JobQuery jobQuery) {
        return insuranceJobService.find(new ObjectId(treeId), jobQuery).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public void update(@PathParam("id") String id, JobRequest request) {
        insuranceJobService.update(new ObjectId(id), request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceJobService.delete(new ObjectId(id));
    }

    @Override
    public List<JobResponse> batchList(BatchJobRequest request) {
        return insuranceJobService.batchList(request.jobIds).stream().map(this::response).collect(Collectors.toList());
    }

    private void tree(List<InsuranceJob> jobs, Map<ObjectId, JobNodeResponse> index, List<JobNodeResponse> firstLevels) {
        jobs.forEach(job -> {
            JobNodeResponse node = new JobNodeResponse();
            node.job = response(job);
            node.children = Lists.newArrayList();
            if (job.parentId == null) {
                firstLevels.add(node);
            }
            index.put(job.id, node);
        });

        jobs.forEach(job -> {
            if (job.parentId != null) {
                JobNodeResponse parent = index.get(job.parentId);
                if (parent == null) {
                    logger.info("missing parent job,id={},parentId={}", job.id, job.parentId);
                } else {
                    parent.children.add(index.get(job.id));
                }
            }
        });
    }

    private JobResponse response(InsuranceJob insuranceJob) {
        JobResponse response = new JobResponse();
        response.id = insuranceJob.id;
        response.jobTreeId = insuranceJob.jobTreeId;
        response.parentId = insuranceJob.parentId;
        response.displayName = insuranceJob.displayName;
        response.code = insuranceJob.code;
        response.riskLevel = insuranceJob.riskLevel;
        response.createdTime = insuranceJob.createdTime;
        response.createdBy = insuranceJob.createdBy;
        response.updatedTime = insuranceJob.updatedTime;
        response.updatedBy = insuranceJob.updatedBy;
        return response;
    }
}
