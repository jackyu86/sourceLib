package com.caej.ticket.service;

import static java.time.LocalDateTime.now;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.ticket.api.CloseTicketRequest;
import com.caej.ticket.api.TicketQuery;
import com.caej.ticket.api.TicketRequest;
import com.caej.ticket.domain.Ticket;
import com.caej.ticket.domain.TicketStatus;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class TicketService {
    @Inject
    MongoRepository<Ticket> repository;

    public Ticket create(TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.fullName = request.fullName;
        ticket.phone = request.phone;
        ticket.content = request.content;
        ticket.createdBy = request.requestBy;
        ticket.createdTime = now();
        repository.insert(ticket);
        return ticket;
    }

    public Ticket get(ObjectId id) {
        return repository.get(id);
    }

    public void close(ObjectId id, CloseTicketRequest request) {
        Ticket ticket = get(id);
        ticket.comment = request.comment;
        ticket.status = TicketStatus.CLOSED;
        ticket.updatedTime = now();
        ticket.updatedBy = request.requestBy;
        repository.update(ticket.id, ticket);
    }

    public FindView<Ticket> find(TicketQuery query) {
        Document document = new Document();
        if (!Strings.isNullOrEmpty(query.fullName)) {
            document.append("full_name", query.fullName);
        }
        if (!Strings.isNullOrEmpty(query.phone)) {
            document.append("phone", query.phone);
        }
        int page = query.page == null ? 1 : query.page;
        int limit = query.limit == null ? 10 : query.limit;
        return repository.query(document).page(page).limit(limit).find();
    }
}
