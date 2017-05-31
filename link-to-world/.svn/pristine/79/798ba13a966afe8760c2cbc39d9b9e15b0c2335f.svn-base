package com.caej.product.api;

import java.util.Map;

import com.caej.product.api.product.FormView;

import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface ProductFormWebService {
    @Path("/api/product/form/:productId")
    @PUT
    FormView pdp(@PathParam("productId") String productId, Map<String, Object> value);

    @Path("/api/product/form/:productId/dealer/:dealerId")
    @PUT
    @Deprecated
    FormView dealerPdp(@PathParam("productId") String productId, @PathParam("dealerId") String dealerId, Map<String, Object> value);

    @Path("/api/product/form/:productId/checkout")
    @PUT
    FormView checkout(@PathParam("productId") String productId, Map<String, Object> value);

    @Path("/api/product/form/:productId/place-order")
    @PUT
    FormView validate(@PathParam("productId") String productId, Map<String, Object> value);

    @Path("/api/product/form/:productId/checkout/preview")
    @PUT
    FormView checkoutPreview(@PathParam("productId") String productId, Map<String, Object> value);
}
