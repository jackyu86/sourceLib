package com.caej.insurance.api;

import java.util.List;

import com.caej.insurance.api.clause.BatchGetInsuranceClauseRequest;
import com.caej.insurance.api.clause.CreateInsuranceClauseRequest;
import com.caej.insurance.api.clause.InsuranceClauseQuery;
import com.caej.insurance.api.clause.InsuranceClauseResponse;
import com.caej.insurance.api.clause.UpdateInsuranceClauseRequest;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceClauseWebService {
    @Path("/api/insurance/clause/batch")
    @PUT
    List<InsuranceClauseResponse> batch(BatchGetInsuranceClauseRequest request);

    @Path("/api/insurance/clause/:id")
    @GET
    InsuranceClauseResponse get(@PathParam("id") String id);

    @Path("/api/insurance/clause/find")
    @POST
    FindView<InsuranceClauseResponse> find(InsuranceClauseQuery query);

    @Path("/api/insurance/clause/:id")
    @DELETE
    void delete(@PathParam("id") String id);

    @Path("/api/insurance/clause")
    @POST
    InsuranceClauseResponse create(CreateInsuranceClauseRequest request);

    @Path("/api/insurance/clause/:id")
    @PUT
    void update(@PathParam("id") String id, UpdateInsuranceClauseRequest request);
}
