package com.caej.insurance.api;

import java.util.List;

import com.caej.insurance.api.area.BatchGetAreaRequest;
import com.caej.insurance.api.area.InsuranceAreaResponse;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;

/**
 * @author chi
 */
public interface InsuranceAreaWebService {
    @Path("/api/area")
    @GET
    List<InsuranceAreaResponse> find();

    @Path("/api/area/batch")
    @PUT
    List<InsuranceAreaResponse> batchGet(BatchGetAreaRequest request);
}
