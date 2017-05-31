package com.caej.esb.web;

import java.util.Optional;
import javax.inject.Inject;
import com.caej.esb.api.CreateESBRecordRequest;
import com.caej.esb.api.ESBRecordResponse;
import com.caej.esb.api.ESBRecordWebService;
import com.caej.esb.domain.ESBRecord;
import com.caej.esb.service.ESBRecordService;

import io.sited.http.PathParam;
import io.sited.http.exception.NotFoundException;

/**
 * @author miller
 */
public class ESBRecordWebServiceImpl implements ESBRecordWebService {
    @Inject
    ESBRecordService esbRecordService;

    @Override
    public void create(CreateESBRecordRequest request) {
        esbRecordService.save(request);
    }

    @Override
    public ESBRecordResponse get(@PathParam("serialNumber") String serialNumber) {
        Optional<ESBRecord> esbRecord = esbRecordService.get(serialNumber);
        if (!esbRecord.isPresent()) {
            throw new NotFoundException("esbRecord not found.serialNumber={}", serialNumber);
        }
        return response(esbRecord.get());
    }

    private ESBRecordResponse response(ESBRecord esbRecord) {
        ESBRecordResponse response = new ESBRecordResponse();
        response.id = esbRecord.id;
        response.serialNumber = esbRecord.serialNumber;
        response.reqTime = esbRecord.reqTime;
        response.items = esbRecord.items;
        response.status = esbRecord.status;
        response.errors = esbRecord.errors;
        response.createdTime = esbRecord.createdTime;
        return response;
    }
}
