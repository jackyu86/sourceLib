package com.caej.insurance.api;

import java.util.List;

import com.caej.insurance.api.job.BatchJobRequest;
import com.caej.insurance.api.job.JobNodeResponse;
import com.caej.insurance.api.job.JobQuery;
import com.caej.insurance.api.job.JobRequest;
import com.caej.insurance.api.job.JobResponse;
import com.caej.insurance.api.job.JobResponseList;

import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceJobWebService {
    @Path("/api/job")
    @POST
    JobResponse create(JobRequest request);

    @Path("/api/job/:id")
    @GET
    JobResponse get(@PathParam("id") String id);

    @Path("/api/job/tree/:treeId/first-level")
    @GET
    JobResponseList firstLevel(@PathParam("treeId") String treeId);

    @Path("/api/job/:id/children")
    @GET
    List<JobResponse> children(@PathParam("id") String id);

    @Path("/api/job/:id/ancestor")
    @GET
    List<JobResponse> ancestor(@PathParam("id") String id);

    @Path("/api/job/tree/:treeId")
    @GET
    List<JobNodeResponse> tree(@PathParam("treeId") String treeId);

    @Path("/api/job/find/:treeId")
    @PUT
    List<JobResponse> findJob(@PathParam("treeId") String treeId, JobQuery jobQuery);

    @Path("/api/job/:id")
    @PUT
    void update(@PathParam("id") String id, JobRequest request);

    @Path("/api/job/:id")
    @DELETE
    void delete(@PathParam("id") String id);

    @Path("/api/job/batch/list")
    @PUT
    List<JobResponse> batchList(BatchJobRequest request);
}
