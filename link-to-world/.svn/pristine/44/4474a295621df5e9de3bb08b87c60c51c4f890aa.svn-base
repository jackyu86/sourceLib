package com.caej.product.api;

import com.caej.product.api.product.ProductDetailResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface ProductDetailWebService {
    @Path("/api/product/cached/:name")
    @GET
    ProductDetailResponse get(@PathParam("name") String name);
}
