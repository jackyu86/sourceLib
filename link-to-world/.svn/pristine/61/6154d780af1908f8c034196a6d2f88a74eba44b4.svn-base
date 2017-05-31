package com.caej.admin.customer;

import com.caej.admin.customer.admin.CustomerAdminController;
import com.caej.order.OrderModule;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.customer.api.CustomerModule;
import io.sited.user.api.UserModule;

/**
 * @author chi
 */
@ModuleInfo(name = "customer.admin", require = {UserModule.class, CustomerModule.class, OrderModule.class, AdminModule.class})
public class CustomerAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(CustomerAdminController.class);
        adminConfig.console().install("customer");
    }
}
