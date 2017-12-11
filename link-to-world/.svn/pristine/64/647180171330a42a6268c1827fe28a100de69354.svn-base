package com.caej.order;

import com.caej.order.archive.OrderArchiveRecordQuery;
import com.caej.order.archive.OrderArchiveRecordRequest;
import com.caej.order.archive.OrderArchiveRecordResponse;
import com.caej.order.archive.UpdateOrderArchiveRecordRequest;

import io.sited.db.FindView;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public interface OrderArchiveRecordWebService {
    @Path("/api/order/archive/record")
    @POST
    OrderArchiveRecordResponse create(OrderArchiveRecordRequest request);

    @Path("/api/order/archive/record")
    @PUT
    FindView<OrderArchiveRecordResponse> find(OrderArchiveRecordQuery query);

    @Path("/api/order/archive/record/:id")
    @PUT
    void update(@PathParam("id") String id, UpdateOrderArchiveRecordRequest request);
}
