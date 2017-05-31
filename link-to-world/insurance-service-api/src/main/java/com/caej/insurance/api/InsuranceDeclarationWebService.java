package com.caej.insurance.api;

import org.bson.types.ObjectId;

import com.caej.insurance.api.infomation.InsuranceInformationQuestionResponse;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceDeclarationWebService {
    @Path("/api/insurance/declaration/:id")
    @GET
    InsuranceInformationQuestionResponse get(@PathParam("id") ObjectId id);
}
