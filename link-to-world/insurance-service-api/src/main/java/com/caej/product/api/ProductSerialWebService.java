package com.caej.product.api;

import com.caej.product.api.serial.ProductSerialProductRequest;
import com.caej.product.api.serial.ProductSerialQuery;
import com.caej.product.api.serial.ProductSerialRequest;
import com.caej.product.api.serial.ProductSerialResponse;

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
public interface ProductSerialWebService {
    @Path("/api/product/serial/:id")
    @GET
    ProductSerialResponse get(@PathParam("id") String id);

    @Path("/api/product/serial/find")
    @PUT
    FindView<ProductSerialResponse> find(ProductSerialQuery query);

    @Path("/api/product/serial")
    @POST
    ProductSerialResponse create(ProductSerialRequest request);

    @Path("/api/product/serial/:id")
    @PUT
    void update(@PathParam("id") String id, ProductSerialRequest request);

    @Path("/api/product/serial/:id/name")
    @PUT
    void updateName(@PathParam("id") String id, ProductSerialRequest request);

    @Path("/api/product/serial/:id")
    @DELETE
    void delete(@PathParam("id") String id);

    @Path("/api/product/serial/:id/add")
    @PUT
    void addProduct(@PathParam("id") String id, ProductSerialProductRequest request);

    @Path("/api/product/serial/:id/remove")
    @PUT
    void removeProduct(@PathParam("id") String id, ProductSerialProductRequest request);

    @Path("/api/product/serial/:id/product")
    @PUT
    void updateProduct(@PathParam("id") String id, ProductSerialProductRequest request);
}
