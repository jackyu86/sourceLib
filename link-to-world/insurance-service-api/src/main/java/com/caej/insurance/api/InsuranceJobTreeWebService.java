package com.caej.insurance.api;

import com.caej.insurance.api.job.JobTreeQuery;
import com.caej.insurance.api.job.JobTreeRequest;
import com.caej.insurance.api.job.JobTreeResponse;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public interface InsuranceJobTreeWebService {
    @Path("/api/insurance/job-tree")
    @POST
    JobTreeResponse create(JobTreeRequest request);

    @Path("/api/insurance/job-tree/find")
    @PUT
    FindView<JobTreeResponse> find(JobTreeQuery query);

    @Path("/api/insurance/job-tree/:id")
    @GET
    JobTreeResponse get(@PathParam("id") String id);

    @Path("/api/insurance/job-tree/:id")
    @PUT
    void update(@PathParam("id") String id, JobTreeRequest request);

    @Path("/api/insurance/job-tree/:id")
    @DELETE
    void delete(@PathParam("id") String id);
}
