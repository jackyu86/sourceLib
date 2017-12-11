package com.caej.order;

import com.caej.order.order.CreateRefundTrackingRequest;

import io.sited.http.POST;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface RefundTrackingWebService {
    @Path("/api/refund-tracking")
    @POST
    void create(CreateRefundTrackingRequest request);
}
