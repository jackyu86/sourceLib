package io.sited.message.web;

import io.sited.db.FindView;
import io.sited.message.api.MessageWebService;
import io.sited.message.api.message.BatchDeleteRequest;
import io.sited.message.api.message.BatchReadRequest;
import io.sited.message.api.message.CountMessageResponse;
import io.sited.message.api.message.MessageQuery;
import io.sited.message.api.message.MessageRequest;
import io.sited.message.api.message.MessageResponse;
import io.sited.message.api.message.MessageStatusView;
import io.sited.message.domain.Message;
import io.sited.message.service.MessageService;
import org.bson.types.ObjectId;

import javax.inject.Inject;

/**
 * @author chi
 */
public class MessageWebServiceImpl implements MessageWebService {
    @Inject
    MessageService messageService;

    @Override
    public MessageResponse get(String id) {
        Message message = messageService.get(new ObjectId(id));
        return response(message);
    }

    @Override
    public FindView<MessageResponse> find(MessageQuery query) {
        return FindView.map(messageService.find(query), this::response);
    }

    @Override
    public CountMessageResponse count(String to) {
        CountMessageResponse response = new CountMessageResponse();
        response.total = messageService.total(to);
        response.totalUnread = messageService.totalUnread(to);
        return response;
    }

    @Override
    public MessageResponse create(MessageRequest request) {
        Message message = messageService.create(request);
        return response(message);
    }

    @Override
    public void read(String id) {
        messageService.read(new ObjectId(id));
    }

    @Override
    public void batchRead(BatchReadRequest request) {
        messageService.batchRead(request);
    }

    @Override
    public void batchDelete(BatchDeleteRequest request) {
        messageService.batchDelete(request);
    }

    @Override
    public void delete(String id) {
        messageService.delete(new ObjectId(id));
    }

    private MessageResponse response(Message message) {
        MessageResponse response = new MessageResponse();
        response.id = message.id;
        response.from = message.from;
        response.to = message.to;
        response.title = message.title;
        response.content = message.content;
        response.type = message.type;
        response.status = MessageStatusView.valueOf(message.status.name());
        response.createdTime = message.createdTime;
        response.createdBy = message.createdBy;
        return response;
    }
}
