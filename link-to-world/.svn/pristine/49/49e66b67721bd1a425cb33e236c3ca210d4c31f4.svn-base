package com.caej.order.web;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.order.OrderArchiveRecordWebService;
import com.caej.order.archive.OrderArchiveRecordQuery;
import com.caej.order.archive.OrderArchiveRecordRequest;
import com.caej.order.archive.OrderArchiveRecordResponse;
import com.caej.order.archive.UpdateOrderArchiveRecordRequest;
import com.caej.order.domain.OrderArchiveRecord;
import com.caej.order.service.OrderArchiveRecordService;

import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class OrderArchiveRecordWebServiceImpl implements OrderArchiveRecordWebService {
    @Inject
    OrderArchiveRecordService orderArchiveRecordService;

    @Override
    public OrderArchiveRecordResponse create(OrderArchiveRecordRequest request) {
        return response(orderArchiveRecordService.create(request));
    }

    @Override
    public FindView<OrderArchiveRecordResponse> find(OrderArchiveRecordQuery query) {
        return FindView.map(orderArchiveRecordService.find(query), this::response);
    }

    @Override
    public void update(@PathParam("id") String id, UpdateOrderArchiveRecordRequest request) {
        orderArchiveRecordService.update(new ObjectId(id), request);
    }


    private OrderArchiveRecordResponse response(OrderArchiveRecord record) {
        OrderArchiveRecordResponse response = new OrderArchiveRecordResponse();
        response.id = record.id;
        response.orderId = record.orderId;
        response.status = record.status;
        response.createdTime = record.createdTime;
        response.updatedTime = record.updatedTime;
        response.count = record.count;
        response.lastError = record.lastError;
        return response;
    }
}
