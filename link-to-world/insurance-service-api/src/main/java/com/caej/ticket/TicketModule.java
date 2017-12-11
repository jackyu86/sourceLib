package com.caej.ticket;

import com.caej.ticket.api.TicketWebService;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author chi
 */
@ModuleInfo(name = "ticket.api", require = APIModule.class)
public class TicketModule extends Module {
    @Override
    protected void configure() throws Exception {
        require(APIConfig.class).client(TicketWebService.class, options(TicketOptions.class).url);
    }
}
