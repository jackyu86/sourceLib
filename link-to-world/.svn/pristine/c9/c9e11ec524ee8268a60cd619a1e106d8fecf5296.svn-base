package com.caej.product.api;

import com.caej.product.api.product.BatchGetProductRequest;
import com.caej.product.api.product.ProductSearchCategoryTreeResponse;
import com.caej.product.api.product.ProductSearchFirstLevelCategoryResponse;
import com.caej.product.api.product.SearchProductRequest;
import com.caej.product.api.product.SearchProductResponse;

import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;

/**
 * @author chi
 */
public interface ProductSearchWebService {
    @Path("/api/product")
    @PUT
    FindView<SearchProductResponse> search(SearchProductRequest request);

    @Path("/v2/api/product")
    @PUT
    FindView<String> searchV2(SearchProductRequest request);

    @Path("/api/product/batch")
    @POST
    FindView<SearchProductResponse> batchGet(BatchGetProductRequest request);

    @Path("/api/product/category/tree")
    @GET
    ProductSearchCategoryTreeResponse categoryTree();

    @Path("/api/product/category/tree/first-level")
    @GET
    ProductSearchFirstLevelCategoryResponse firstLevelCategory();
}
