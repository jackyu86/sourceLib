package com.caej.order.service;

import javax.inject.Inject;

import com.caej.order.domain.RefundTracking;

import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class RefundTrackingService {
    @Inject
    MongoRepository<RefundTracking> repository;

    public void insert(RefundTracking refundTracking) {
        repository.insert(refundTracking);
    }
}
