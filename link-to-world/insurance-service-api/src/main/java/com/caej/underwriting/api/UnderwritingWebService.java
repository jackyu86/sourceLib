package com.caej.underwriting.api;

import com.caej.underwriting.api.underwriting.DischargeRequest;
import com.caej.underwriting.api.underwriting.DischargeResponse;

import io.sited.http.PUT;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface UnderwritingWebService {
    @Path("/api/underwriting/discharge")
    @PUT
    DischargeResponse discharge(DischargeRequest request);
}
