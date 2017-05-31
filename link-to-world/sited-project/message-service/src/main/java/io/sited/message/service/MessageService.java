package io.sited.message.service;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.message.api.message.BatchDeleteRequest;
import io.sited.message.api.message.BatchReadRequest;
import io.sited.message.api.message.MessageQuery;
import io.sited.message.api.message.MessageRequest;
import io.sited.message.domain.Message;
import io.sited.message.domain.MessageStatus;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class MessageService {
    @Inject
    MongoRepository<Message> repository;

    public Message create(MessageRequest request) {
        Message message = new Message();
        message.from = request.from;
        message.to = request.to;
        message.title = request.title;
        message.content = request.content;
        message.type = request.type;
        message.status = MessageStatus.NEW;
        message.createdBy = request.requestBy;
        message.updatedBy = request.requestBy;
        message.createdTime = LocalDateTime.now();
        message.updatedTime = LocalDateTime.now();
        repository.insert(message);
        return message;
    }

    public void read(ObjectId id) {
        Message message = get(id);
        message.status = MessageStatus.READ;
        message.updatedTime = LocalDateTime.now();
        repository.update(id, message);
    }

    public Message get(ObjectId id) {
        return repository.get(id);
    }

    public FindView<Message> find(MessageQuery query) {
        return repository.query(new Document("to", query.to)).page(query.page).limit(query.limit).sort("created_time", true).find();
    }

    public long total(String to) {
        return repository.query(new Document("to", to)).count();
    }

    public long totalUnread(String to) {
        return repository.query(new Document("to", to).append("status", MessageStatus.NEW)).count();
    }

    public void delete(ObjectId id) {
        repository.delete(id);
    }

    public void batchRead(BatchReadRequest request) {
        List<ObjectId> ids = request.ids.stream().map(ObjectId::new).collect(Collectors.toList());
        repository.collection()
            .updateMany(new Document("to", request.to).append("_id", new Document("$in", ids)), new Document("$set", new Document("status", MessageStatus.READ)));
    }

    public void batchDelete(BatchDeleteRequest request) {
        List<ObjectId> ids = request.ids.stream().map(ObjectId::new).collect(Collectors.toList());
        repository.collection()
            .deleteMany(new Document("to", request.to).append("_id", new Document("$in", ids)));
    }
}
