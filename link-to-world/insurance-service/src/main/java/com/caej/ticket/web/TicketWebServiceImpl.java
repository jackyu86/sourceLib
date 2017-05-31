package com.caej.ticket.web;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.ticket.api.CloseTicketRequest;
import com.caej.ticket.api.TicketQuery;
import com.caej.ticket.api.TicketRequest;
import com.caej.ticket.api.TicketResponse;
import com.caej.ticket.api.TicketWebService;
import com.caej.ticket.domain.Ticket;
import com.caej.ticket.service.TicketService;

import io.sited.db.FindView;

/**
 * @author chi
 */
public class TicketWebServiceImpl implements TicketWebService {
    @Inject
    TicketService ticketService;

    @Override
    public TicketResponse get(String id) {
        Ticket ticket = ticketService.get(new ObjectId(id));
        return response(ticket);
    }

    @Override
    public FindView<TicketResponse> query(TicketQuery query) {
        FindView<Ticket> tickets = ticketService.find(query);
        return FindView.map(tickets, this::response);
    }

    @Override
    public TicketResponse create(TicketRequest request) {
        Ticket ticket = ticketService.create(request);
        return response(ticket);
    }

    @Override
    public void close(String id, CloseTicketRequest request) {
        ticketService.close(new ObjectId(id), request);
    }

    private TicketResponse response(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.id = ticket.id.toHexString();
        response.fullName = ticket.fullName;
        response.phone = ticket.phone;
        response.content = ticket.content;
        response.comment = ticket.comment;
        response.createdTime = ticket.createdTime;
        response.createdBy = ticket.createdBy;
        return response;
    }
}
