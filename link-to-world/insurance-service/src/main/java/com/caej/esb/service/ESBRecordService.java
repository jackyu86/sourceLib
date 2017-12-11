package com.caej.esb.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;
import com.caej.esb.api.CreateESBRecordRequest;
import com.caej.esb.domain.ESBRecord;
import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class ESBRecordService {
    @Inject
    MongoRepository<ESBRecord> repository;

    public void save(CreateESBRecordRequest request) {
        ESBRecord esbRecord = new ESBRecord();
        esbRecord.serialNumber = request.serialNumber;
        esbRecord.reqTime = request.reqTime;
        esbRecord.items = request.items;
        esbRecord.status = request.status;
        esbRecord.errors = request.errors;
        esbRecord.createdTime = LocalDateTime.now();
        repository.insert(esbRecord);
    }

    public Optional<ESBRecord> get(String serialNumber) {
        return repository.query(new Document("serial_number", serialNumber)).findOne();
    }

}
