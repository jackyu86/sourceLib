package com.caej.order.service;

import java.time.LocalDateTime;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.order.archive.OrderArchiveRecordQuery;
import com.caej.order.archive.OrderArchiveRecordRequest;
import com.caej.order.archive.OrderArchiveRecordStatus;
import com.caej.order.archive.UpdateOrderArchiveRecordRequest;
import com.caej.order.domain.OrderArchiveRecord;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author miller
 */
public class OrderArchiveRecordService {
    @Inject
    MongoRepository<OrderArchiveRecord> repository;

    public OrderArchiveRecord create(OrderArchiveRecordRequest request) {
        OrderArchiveRecord record = new OrderArchiveRecord();
        record.orderId = request.orderId;
        record.status = OrderArchiveRecordStatus.WAITING;
        record.createdTime = LocalDateTime.now();
        repository.insert(record);
        return record;
    }

    public FindView<OrderArchiveRecord> find(OrderArchiveRecordQuery query) {
        Document filter = new Document();
        if (query.statusList != null && !query.statusList.isEmpty()) {
            filter.put("status", new Document("$in", query.statusList));
        }
        if (query.count != null) {
            filter.put("count", new Document("$lt", query.count));
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void update(ObjectId id, UpdateOrderArchiveRecordRequest request) {
        OrderArchiveRecord origin = repository.get(id);
        origin.status = request.status;
        if (OrderArchiveRecordStatus.FAIL.equals(request.status)) {
            origin.lastError = request.lastError;
        }
        origin.updatedTime = LocalDateTime.now();
        origin.count += 1;
        repository.update(id, origin);
    }
}
