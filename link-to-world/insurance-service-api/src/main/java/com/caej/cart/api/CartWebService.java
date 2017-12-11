package com.caej.cart.api;


import java.util.Optional;
import com.caej.cart.api.cart.AddCartRequest;
import com.caej.cart.api.cart.BatchGetCartItemRequest;
import com.caej.cart.api.cart.BatchGetCartItemResponse;
import com.caej.cart.api.cart.CartCountResponse;
import com.caej.cart.api.cart.CartResponse;
import com.caej.cart.api.cart.DeleteCartRequest;
import com.caej.cart.api.cart.MergeCartRequest;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface CartWebService {
    @POST
    @Path("/api/cart/:id/item")
    void add(@PathParam("id") String id, AddCartRequest request);

    @GET
    @Path("/api/cart/:id")
    Optional<CartResponse> get(@PathParam("id") String id);

    @GET
    @Path("/api/cart/:id/count")
    CartCountResponse count(@PathParam("id") String id);

    @POST
    @Path("/api/cart/:id/merge")
    void merge(@PathParam("id") String id, MergeCartRequest request);

    @PUT
    @Path("/api/cart/:id/delete-item")
    void delete(@PathParam("id") String id, DeleteCartRequest request);

    @POST
    @Path("/api/cart/items")
    BatchGetCartItemResponse batchGet(BatchGetCartItemRequest batchGetCartItemRequest);
}
