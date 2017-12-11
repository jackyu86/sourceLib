package com.caej.product.api;

import com.caej.order.order.OrderView;

import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public interface ArchiveWebService {
    @Path("/api/archive/order/:id")
    @PUT
    String archiveOrder(@PathParam("id") String id, OrderView orderView);
}
