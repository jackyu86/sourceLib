package com.caej.product.service.client;

import java.util.List;
import java.util.Optional;

import com.caej.insurance.api.InsuranceJobWebService;
import com.caej.insurance.api.job.BatchJobRequest;
import com.caej.insurance.api.job.JobNodeResponse;
import com.caej.insurance.api.job.JobQuery;
import com.caej.insurance.api.job.JobRequest;
import com.caej.insurance.api.job.JobResponse;
import com.caej.insurance.api.job.JobResponseList;

import io.sited.cache.Cache;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceJobWebServiceClient implements InsuranceJobWebService {
    private final Cache<JobResponseList> cache;
    private final InsuranceJobWebService insuranceJobWebService;

    public InsuranceJobWebServiceClient(Cache<JobResponseList> jobCache, InsuranceJobWebService insuranceJobWebService) {
        this.cache = jobCache;
        this.insuranceJobWebService = insuranceJobWebService;
    }

    @Override
    public JobResponse create(JobRequest request) {
        return insuranceJobWebService.create(request);
    }

    @Override
    public JobResponse get(@PathParam("id") String id) {
        return insuranceJobWebService.get(id);
    }

    @Override
    public JobResponseList firstLevel(@PathParam("treeId") String treeId) {
        final String prefix = "firstLevel$";
        Optional<JobResponseList> optional = cache.get(prefix + treeId);
        if (optional.isPresent()) return optional.get();
        JobResponseList jobResponseList = insuranceJobWebService.firstLevel(treeId);
        cache.put(prefix + treeId, jobResponseList);
        return jobResponseList;
    }

    @Override
    public List<JobResponse> children(@PathParam("id") String id) {
        return insuranceJobWebService.children(id);
    }

    @Override
    public List<JobResponse> ancestor(@PathParam("id") String id) {
        return insuranceJobWebService.ancestor(id);
    }

    @Override
    public List<JobNodeResponse> tree(@PathParam("treeId") String treeId) {
        return insuranceJobWebService.tree(treeId);
    }

    @Override
    public List<JobResponse> findJob(@PathParam("treeId") String treeId, JobQuery jobQuery) {
        return insuranceJobWebService.findJob(treeId, jobQuery);
    }

    @Override
    public void update(@PathParam("id") String id, JobRequest request) {
        insuranceJobWebService.update(id, request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceJobWebService.delete(id);
    }

    @Override
    public List<JobResponse> batchList(BatchJobRequest request) {
        return insuranceJobWebService.batchList(request);
    }
}
