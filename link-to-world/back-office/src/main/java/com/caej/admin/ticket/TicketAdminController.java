package com.caej.admin.ticket;

import javax.inject.Inject;

import com.caej.ticket.api.TicketQuery;
import com.caej.ticket.api.TicketResponse;
import com.caej.ticket.api.TicketWebService;

import io.sited.db.FindView;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author Hubery.Chen
 */
public class TicketAdminController {
    @Inject
    TicketWebService ticketWebService;

    @Path("/admin/ajax/ticket")
    @PUT
    public FindView<TicketResponse> find(Request request) {
        TicketQuery query = request.body(TicketQuery.class);
        return ticketWebService.query(query);
    }
}
