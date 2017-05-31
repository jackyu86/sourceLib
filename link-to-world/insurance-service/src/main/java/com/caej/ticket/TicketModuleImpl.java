package com.caej.ticket;

import com.caej.ticket.api.TicketWebService;
import com.caej.ticket.domain.Ticket;
import com.caej.ticket.service.TicketService;
import com.caej.ticket.web.TicketWebServiceImpl;

import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;

/**
 * @author chi
 */
@ModuleInfo(name = "ticket", require = {DBModule.class, APIModule.class})
public class TicketModuleImpl extends TicketModule {
    @Override
    protected void configure() throws Exception {
        require(MongoConfig.class)
            .entity(Ticket.class);

        bind(TicketService.class);
        require(APIConfig.class)
            .service(TicketWebService.class, TicketWebServiceImpl.class);
    }
}
