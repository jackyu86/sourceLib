package com.caej.underwriting.web;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.api.underwritting.UnderwritingResponse;
import com.caej.order.OrderWebService;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.UpdateOrderItemUnderwritingStatusRequest;
import com.caej.order.order.UpdateOrderUnderwritingStatusRequest;
import com.caej.underwriting.api.UnderwritingWebService;
import com.caej.underwriting.api.underwriting.DischargeRequest;
import com.caej.underwriting.api.underwriting.DischargeResponse;
import com.caej.underwriting.domain.UnderwritingRecord;
import com.caej.underwriting.service.UnderwritingRecordService;
import com.caej.underwriting.service.UnderwritingRequestBuildService;
import com.caej.underwriting.service.UnderwritingService;
import com.google.common.collect.Lists;

import io.sited.util.JSON;

/**
 * @author miller
 */
public class UnderwritingWebServiceImpl implements UnderwritingWebService {
    private final Logger logger = LoggerFactory.getLogger(UnderwritingWebServiceImpl.class);
    @Inject
    UnderwritingService underwritingService;
    @Inject
    UnderwritingRequestBuildService underwritingRequestBuildService;
    @Inject
    OrderWebService orderWebService;
    @Inject
    UnderwritingRecordService underwritingRecordService;

    @Override
    public DischargeResponse discharge(DischargeRequest request) {
        OrderView orderView = orderWebService.get(request.orderId);
        UnderwritingRecord underwritingRecord = new UnderwritingRecord();
        underwritingRecord.orderId = orderView.id;
        DischargeResponse dischargeResponse = new DischargeResponse();
        try {
            //todo 是否使用承保时的值
            UnderwritingRequest underwritingRequest = underwritingRequestBuildService.buildRequest(orderView, "201");
            underwritingRecord.request = JSON.toJSON(underwritingRequest);
            underwritingRecord.transNo = underwritingRequest.main.transNo;
            underwritingRecord.transType = underwritingRequest.main.transType;
            underwritingRecord.transDate = underwritingRequest.main.transDate;
            underwritingRecord.transTime = underwritingRequest.main.transTime;
            UnderwritingResponse underwritingResponse = underwritingService.callWebService(underwritingRequest);
            underwritingRecord.response = JSON.toJSON(underwritingResponse);
            updateOrderDischarge(orderView, underwritingResponse);
            underwritingRecord.tradeStatus = underwritingResponse.tradeStaus;
            dischargeResponse.tradeStatus = underwritingResponse.tradeStaus;
            underwritingRecord.error = JSON.toJSON(underwritingResponse.errors);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            underwritingRecord.error = JSON.toJSON(e);
            dischargeResponse.tradeStatus = "0";
            dischargeResponse.error = underwritingRecord.error;
        }
        underwritingRecordService.create(underwritingRecord);
        return dischargeResponse;
    }

    private void updateOrderDischarge(OrderView orderView, UnderwritingResponse underwritingResponse) {
        UpdateOrderUnderwritingStatusRequest request = new UpdateOrderUnderwritingStatusRequest();
        request.status = ("1".equals(underwritingResponse.tradeStaus) || "2".equals(underwritingResponse.tradeStaus)) ? OrderStatusView.SURRENDERED : OrderStatusView.SURRENDER_FAILED;
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
        orderWebService.updateUnderwriting(orderView.id, request);
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
