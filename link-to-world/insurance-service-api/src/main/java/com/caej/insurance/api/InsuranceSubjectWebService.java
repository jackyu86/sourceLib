package com.caej.insurance.api;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.caej.insurance.api.subject.InsuranceSubjectQuery;
import com.caej.insurance.api.subject.InsuranceSubjectResponse;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * 标的
 *
 * @author chi
 */
public interface InsuranceSubjectWebService {
    @Path("/api/insurance/subject/:id")
    @GET
    Optional<InsuranceSubjectResponse> get(@PathParam("id") ObjectId id);

    @Path("/api/insurance/subject/find")
    @POST
    FindView<InsuranceSubjectResponse> find(InsuranceSubjectQuery query);

    @Path("/api/insurance/subject/:id")
    @DELETE
    void delete(@PathParam("id") ObjectId id);

}
