package com.caej.insurance.api;

import java.util.List;
import org.bson.types.ObjectId;
import com.caej.insurance.api.category.BatchInsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.insurance.api.category.InsuranceCategoryQuery;
import com.caej.insurance.api.category.InsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryResponse;
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
public interface InsuranceCategoryWebService {
    @Path("/api/insurance/category/find")
    @POST
    FindView<InsuranceCategoryResponse> find(InsuranceCategoryQuery query);

    @Path("/api/insurance/category/first-level")
    @GET
    List<InsuranceCategoryResponse> firstLevel();

    @Path("/api/insurance/category/:id/children")
    @GET
    List<InsuranceCategoryResponse> children(@PathParam("id") ObjectId id);

    @Path("/api/insurance/category/tree")
    @GET
    List<InsuranceCategoryNodeResponse> tree();

    @Path("/api/insurance/category")
    @GET
    List<InsuranceCategoryResponse> list();

    @Path("/api/insurance/category/:id")
    @GET
    InsuranceCategoryResponse get(@PathParam("id") String id);

    @Path("/api/insurance/category")
    @POST
    InsuranceCategoryResponse create(InsuranceCategoryRequest request);

    @Path("/api/insurance/category/:id")
    @PUT
    InsuranceCategoryResponse update(@PathParam("id") String id, InsuranceCategoryRequest request);

    @Path("/api/insurance/category/:id")
    @DELETE
    void delete(@PathParam("id") String id);

    @Path("/api/insurance/category/batch")
    @PUT
    List<InsuranceCategoryNodeResponse> childrenTree(BatchInsuranceCategoryRequest request);

    @Path("/api/insurance/category/batch/first-level")
    @PUT
    List<InsuranceCategoryResponse> firstLevel(BatchInsuranceCategoryRequest request);

    @Path("/api/insurance/category/batch/list")
    @PUT
    List<InsuranceCategoryResponse> batch(BatchInsuranceCategoryRequest request);
}
