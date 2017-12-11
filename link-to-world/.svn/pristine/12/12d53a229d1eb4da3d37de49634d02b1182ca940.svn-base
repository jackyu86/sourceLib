package com.caej.admin;

import com.caej.admin.ticket.TicketAdminController;
import com.caej.ticket.TicketModule;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.admin.ConsoleOptions;
import io.sited.i18n.I18nModule;

/**
 * Created by hubery.chen on 2017/1/3.
 */
@ModuleInfo(name = "ticket.admin", require = {AdminModule.class, I18nModule.class, TicketModule.class})
public class TicketAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(TicketAdminController.class);
        adminConfig.console().install("ticket", new ConsoleOptions());
    }
}
