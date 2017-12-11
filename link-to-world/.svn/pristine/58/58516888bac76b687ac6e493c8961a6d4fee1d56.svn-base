package com.caej.admin.insurancejob;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceJobTreeWebService;
import com.caej.insurance.api.InsuranceJobWebService;
import com.caej.insurance.api.job.BatchJobRequest;
import com.caej.insurance.api.job.JobNodeResponse;
import com.caej.insurance.api.job.JobQuery;
import com.caej.insurance.api.job.JobRequest;
import com.caej.insurance.api.job.JobResponse;
import com.caej.insurance.api.job.JobTreeQuery;
import com.caej.insurance.api.job.JobTreeRequest;
import com.caej.insurance.api.job.JobTreeResponse;

import io.sited.admin.AdminUser;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author miller
 */
public class InsuranceJobAdminController {
    @Inject
    InsuranceJobTreeWebService insuranceJobTreeWebService;
    @Inject
    InsuranceJobWebService insuranceJobWebService;

    @Path("/admin/ajax/job-tree")
    @POST
    public JobTreeResponse create(Request request) {
        JobTreeRequest jobTreeRequest = request.body(JobTreeRequest.class);
        AdminUser current = request.require(AdminUser.class);
        jobTreeRequest.requestBy = current.username;
        return insuranceJobTreeWebService.create(request.body(JobTreeRequest.class));
    }

    @Path("/admin/ajax/job-tree/find")
    @PUT
    public FindView<JobTreeResponse> find(Request request) {
        JobTreeAdminAJAXRequest jobTreeAdminAJAXRequest = request.body(JobTreeAdminAJAXRequest.class);
        JobTreeQuery query = new JobTreeQuery();
        query.page = jobTreeAdminAJAXRequest.page;
        query.limit = jobTreeAdminAJAXRequest.limit;
        query.name = jobTreeAdminAJAXRequest.name;
        if (jobTreeAdminAJAXRequest.order != null && jobTreeAdminAJAXRequest.order.contains("-")) {
            query.order = jobTreeAdminAJAXRequest.order.replace("-", "");
            query.desc = true;
        } else {
            query.order = jobTreeAdminAJAXRequest.order;
            query.desc = false;
        }
        return insuranceJobTreeWebService.find(query);
    }

    @Path("/admin/ajax/job-tree/:id")
    @GET
    public JobTreeResponse get(Request request) {
        String id = request.pathParam("id");
        return insuranceJobTreeWebService.get(id);
    }

    @Path("/admin/ajax/job-tree/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        JobTreeRequest jobTreeRequest = request.body(JobTreeRequest.class);
        AdminUser current = request.require(AdminUser.class);
        jobTreeRequest.requestBy = current.username;
        insuranceJobTreeWebService.update(id, jobTreeRequest);
    }

    @Path("/admin/ajax/job-tree/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        insuranceJobTreeWebService.delete(id);
    }

    @Path("/admin/ajax/job")
    @POST
    public JobResponse createJob(Request request) {
        JobRequest jobRequest = request.body(JobRequest.class);
        AdminUser current = request.require(AdminUser.class);
        jobRequest.requestBy = current.username;
        return insuranceJobWebService.create(jobRequest);
    }

    @Path("/admin/ajax/job/:id")
    @GET
    public JobResponse getJob(Request request) {
        String id = request.pathParam("id");
        return insuranceJobWebService.get(id);
    }

    @Path("/admin/ajax/job/tree/:treeId")
    @GET
    public List<JobNodeResponse> tree(Request request) {
        String treeId = request.pathParam("treeId");
        return insuranceJobWebService.tree(treeId);
    }

    @Path("/admin/ajax/job/find/:treeId")
    @PUT
    public List<JobResponse> findJob(Request request) {
        String treeId = request.pathParam("treeId");
        JobQuery jobQuery = request.body(JobQuery.class);
        return insuranceJobWebService.findJob(treeId, jobQuery);
    }

    @Path("/admin/ajax/job/:id")
    @PUT
    public void updateJob(Request request) {
        String id = request.pathParam("id");
        JobRequest jobRequest = request.body(JobRequest.class);
        AdminUser current = request.require(AdminUser.class);
        jobRequest.requestBy = current.username;
        insuranceJobWebService.update(id, jobRequest);
    }

    @Path("/admin/ajax/job/:id")
    @DELETE
    public void deleteJob(Request request) {
        String id = request.pathParam("id");
        insuranceJobWebService.delete(id);
    }

    @Path("/admin/ajax/job/batch/find")
    @PUT
    public List<JobResponse> batch(Request request) {
        BatchJobRequest batchJobRequest = request.body(BatchJobRequest.class);
        return insuranceJobWebService.batchList(batchJobRequest);
    }
}
