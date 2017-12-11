package com.caej.underwriting.service;

import javax.inject.Inject;

import com.caej.underwriting.domain.UnderwritingRecord;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class UnderwritingRecordService {
    @Inject
    MongoRepository<UnderwritingRecord> repository;

    public void create(UnderwritingRecord underwritingRecord) {
        repository.insert(underwritingRecord);
    }
}
