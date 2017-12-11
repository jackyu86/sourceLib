package com.caej.site.ticket;

import javax.inject.Inject;

import com.caej.ticket.api.TicketRequest;
import com.caej.ticket.api.TicketWebService;

import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author Hubery.Chen
 */
public class TicketAJAXController {
    @Inject
    TicketWebService ticketWebService;

    @Path("/ajax/ticket")
    @POST
    public void create(Request request) {
        ticketWebService.create(request.body(TicketRequest.class));
    }

}
