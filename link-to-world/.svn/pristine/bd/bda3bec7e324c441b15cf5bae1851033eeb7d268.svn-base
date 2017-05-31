package com.caej.order.web;


import java.time.LocalDateTime;

import javax.inject.Inject;

import com.caej.order.RefundTrackingWebService;
import com.caej.order.domain.RefundTracking;
import com.caej.order.order.CreateRefundTrackingRequest;
import com.caej.order.service.RefundTrackingService;

/**
 * @author miller
 */
public class RefundTrackingWebServiceImpl implements RefundTrackingWebService {
    @Inject
    RefundTrackingService refundTrackingService;

    @Override
    public void create(CreateRefundTrackingRequest request) {
        RefundTracking refundTracking = new RefundTracking();
        refundTracking.oderId = request.orderId;
        refundTracking.orderItemId = request.orderItemId;
        refundTracking.refundId = request.refundId;
        refundTracking.request = request.request;
        refundTracking.status = request.status;
        refundTracking.errors = request.errors;
        refundTracking.createdTime = LocalDateTime.now();
        refundTrackingService.insert(refundTracking);
    }
}
