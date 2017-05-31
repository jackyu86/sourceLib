package com.caej.site.sms.queue;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.api.esb.ESBRequest;
import com.caej.api.esb.ESBResponse;
import com.caej.esb.api.CreateESBRecordRequest;
import com.caej.esb.api.ESBRecordWebService;
import com.caej.site.esb.service.ESBService;

import io.sited.queue.QueueHandler;
import io.sited.user.web.message.CreatePinCodeEvent;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class SMSPinCodeQueueHandler implements QueueHandler<CreatePinCodeEvent> {
    private final Logger logger = LoggerFactory.getLogger(SMSPinCodeQueueHandler.class);
    @Inject
    ESBService esbService;
    @Inject
    ESBRecordWebService esbRecordWebService;

    @Override
    public void handle(CreatePinCodeEvent event) throws Throwable {
        if (event.phone != null) {
            logger.info("send sms message, phone={}, code={}", event.phone, event.code);
            CreateESBRecordRequest createESBRecordRequest = new CreateESBRecordRequest();
            ESBRequest esbRequest = esbService.buildSingleRequest(event.phone, event.code);
            createESBRecordRequest.serialNumber = esbRequest.header.request.serialNumber;
            createESBRecordRequest.reqTime = esbRequest.header.request.reqTime;
            createESBRecordRequest.items = JSON.toJSON(esbRequest.body.request.requestSms.items);
            ESBResponse esbResponse = new ESBResponse();
            try {
                esbResponse = esbService.callWebService(esbRequest);
                createESBRecordRequest.status = esbResponse.body.response.responseSms.status;
                createESBRecordRequest.errors = JSON.toJSON(esbResponse.body.response.responseSms.errors);
            } catch (Exception e) {
                esbResponse.body = new ESBResponse.ESBResponseBody();
                esbResponse.body.response = new ESBResponse.ESBResponseBodyResponse();
                esbResponse.body.response.responseSms = new ESBResponse.ESBResponseBodyResponseSms();
                esbResponse.body.response.responseSms.status = "-2";
                createESBRecordRequest.status = "-2";
                createESBRecordRequest.errors = e.getMessage();
            }
            esbRecordWebService.create(createESBRecordRequest);
        }
    }
}
