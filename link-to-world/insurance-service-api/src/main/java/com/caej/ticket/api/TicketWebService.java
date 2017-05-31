package com.caej.ticket.api;

import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface TicketWebService {
    @Path("/api/ticket/:id")
    @GET
    TicketResponse get(@PathParam("id") String id);

    @Path("/api/ticket/query")
    @PUT
    FindView<TicketResponse> query(TicketQuery query);

    @Path("/api/ticket")
    @POST
    TicketResponse create(TicketRequest request);


    @Path("/api/ticket/:id/close")
    @POST
    void close(@PathParam("id") String id, CloseTicketRequest request);
}
