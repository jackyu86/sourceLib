package com.caej.underwriting.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.api.underwritting.UnderwritingResponse;
import com.caej.order.OrderArchiveRecordWebService;
import com.caej.order.OrderWebService;
import com.caej.order.archive.OrderArchiveRecordRequest;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.UpdateOrderItemUnderwritingStatusRequest;
import com.caej.order.order.UpdateOrderUnderwritingStatusRequest;
import com.caej.underwriting.domain.UnderwritingRecord;
import com.caej.underwriting.job.UnderwritingJob;
import com.google.common.collect.Lists;

import io.sited.util.JSON;

/**
 * @author chi
 */
public class KdlinsUnderWritingClient implements UnderWritingClient {
    private final Logger logger = LoggerFactory.getLogger(UnderwritingJob.class);
    @Inject
    OrderWebService orderWebService;
    @Inject
    UnderwritingRequestBuildService underwritingRequestBuildService;
    @Inject
    UnderwritingRecordService underwritingRecordService;
    @Inject
    UnderwritingService underwritingService;
    @Inject
    OrderArchiveRecordWebService orderArchiveRecordWebService;

    @Override
    public void underwriting(OrderView orderView) {
        UnderwritingRecord underwritingRecord = new UnderwritingRecord();
        underwritingRecord.orderId = orderView.id;
        try {
            UnderwritingRequest underwritingRequest = underwritingRequestBuildService.buildRequest(orderView, "102");
            underwritingRecord.request = JSON.toJSON(underwritingRequest);
            underwritingRecord.transNo = underwritingRequest.main.transNo;
            underwritingRecord.transType = underwritingRequest.main.transType;
            underwritingRecord.transDate = underwritingRequest.main.transDate;
            underwritingRecord.transTime = underwritingRequest.main.transTime;
            UnderwritingResponse underwritingResponse = underwritingService.callWebService(underwritingRequest);
            underwritingRecord.response = JSON.toJSON(underwritingResponse);
            updateOrder(orderView, underwritingRequest, underwritingResponse);
            createArchiveRecord(orderView.id);
            underwritingRecord.tradeStatus = underwritingResponse.tradeStaus;
            underwritingRecord.error = JSON.toJSON(underwritingResponse.errors);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            underwritingRecord.error = JSON.toJSON(e);
        }
        underwritingRecordService.create(underwritingRecord);
    }


    private void updateOrder(OrderView orderView, UnderwritingRequest underwritingRequest, UnderwritingResponse underwritingResponse) {
        UpdateOrderUnderwritingStatusRequest request = new UpdateOrderUnderwritingStatusRequest();
        request.status = ("1".equals(underwritingResponse.tradeStaus) || "2".equals(underwritingResponse.tradeStaus)) ? OrderStatusView.VENDOR_INSURED : OrderStatusView.VENDOR_REJECT;
        request.transNo = underwritingResponse.main.transNo;
        request.transType = underwritingResponse.main.transType;
        request.transDate = underwritingResponse.main.transDate;
        request.transTime = underwritingResponse.main.transTime;
        request.errors = getErrorMessage(underwritingResponse.errors);
        request.itemStatusList = Lists.newArrayList();
        if (underwritingResponse.insureds != null) {
            underwritingResponse.insureds.forEach(underwritingResponseInsured -> {
                UpdateOrderItemUnderwritingStatusRequest updateOrderItemUnderwritingStatusRequest = new UpdateOrderItemUnderwritingStatusRequest();
                updateOrderItemUnderwritingStatusRequest.id = orderItemId(orderView.id, underwritingResponseInsured.insuredNum);
                updateOrderItemUnderwritingStatusRequest.orderStatus = "1".equals(underwritingResponseInsured.staus) ? OrderStatusView.VENDOR_INSURED : OrderStatusView.VENDOR_REJECT;
                updateOrderItemUnderwritingStatusRequest.policyCode = underwritingResponseInsured.policyCode;
                updateOrderItemUnderwritingStatusRequest.policyAddress = underwritingResponseInsured.policyAddress;
                request.itemStatusList.add(updateOrderItemUnderwritingStatusRequest);
            });
        }
        request.applyCode = underwritingRequest.application.applyCode;
        orderWebService.updateUnderwriting(orderView.id, request);
    }

    private void createArchiveRecord(String orderId) {
        OrderArchiveRecordRequest request = new OrderArchiveRecordRequest();
        request.orderId = orderId;
        orderArchiveRecordWebService.create(request);
    }

    private String getErrorMessage(List<UnderwritingResponse.UnderwritingResponseError> errors) {
        if (errors == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        errors.forEach(error -> {
            stringBuilder.append(error.errorMsg).append(';');
        });
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    private String orderItemId(String orderId, Integer num) {
        return orderId + "-" + num;
    }
}
