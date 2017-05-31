package com.caej.site.insurance.web.ajax;

import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.insurance.api.category.InsuranceCategoryResponse;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author chi
 */
public class InsuranceCategoryAJAXController {
    @Inject
    InsuranceCategoryWebService insuranceCategoryWebService;

    @GET
    @Path("/ajax/product/category/top")
    public List<InsuranceCategoryResponse> top() {
        return insuranceCategoryWebService.firstLevel();
    }

    @GET
    @Path("/ajax/product/category/:id/children")
    public List<InsuranceCategoryResponse> children(Request request) {
        ObjectId id = request.pathParam("id", ObjectId.class);
        return insuranceCategoryWebService.children(id);
    }

    @GET
    @Path("/ajax/product/category/tree")
    public List<InsuranceCategoryNodeResponse> tree() {
        return insuranceCategoryWebService.tree();
    }
}
