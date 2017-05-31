package com.caej.insurance.web;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceJobTreeWebService;
import com.caej.insurance.api.job.JobTreeQuery;
import com.caej.insurance.api.job.JobTreeRequest;
import com.caej.insurance.api.job.JobTreeResponse;
import com.caej.insurance.domain.InsuranceJobTree;
import com.caej.insurance.service.InsuranceJobService;
import com.caej.insurance.service.InsuranceJobTreeService;

import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.exception.BadRequestException;

/**
 * @author miller
 */
public class InsuranceJobTreeWebServiceImpl implements InsuranceJobTreeWebService {
    @Inject
    InsuranceJobTreeService insuranceJobTreeService;
    @Inject
    InsuranceJobService insuranceJobService;

    @Override
    public JobTreeResponse create(JobTreeRequest request) {
        return response(insuranceJobTreeService.create(request));
    }

    @Override
    public FindView<JobTreeResponse> find(JobTreeQuery query) {
        return FindView.map(insuranceJobTreeService.find(query), this::response);
    }

    @Override
    public JobTreeResponse get(@PathParam("id") String id) {
        return response(insuranceJobTreeService.get(new ObjectId(id)));
    }

    @Override
    public void update(String id, JobTreeRequest request) {
        insuranceJobTreeService.update(new ObjectId(id), request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        if (insuranceJobService.count(new ObjectId(id)) > 0) {
            throw new BadRequestException("insuranceJobTreeId", "jobTree.error.childrenExist");
        }
        insuranceJobTreeService.delete(new ObjectId(id));
    }

    private JobTreeResponse response(InsuranceJobTree insuranceJobTree) {
        JobTreeResponse response = new JobTreeResponse();
        response.id = insuranceJobTree.id;
        response.name = insuranceJobTree.name;
        response.description = insuranceJobTree.description;
        response.createdTime = insuranceJobTree.createdTime;
        response.createdBy = insuranceJobTree.createdBy;
        response.updatedTime = insuranceJobTree.updatedTime;
        response.updatedBy = insuranceJobTree.updatedBy;
        return response;
    }
}
