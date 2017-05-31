package com.caej.admin;

import com.caej.admin.country.CountryAdminController;
import com.caej.admin.dealer.DealerAdminController;
import com.caej.admin.dealer.DealerProductAdminController;
import com.caej.insurance.api.InsuranceModule;
import com.caej.order.OrderModule;
import com.caej.product.api.ProductModule;

import app.dealer.api.DealerModule;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.admin.ConsoleOptions;
import io.sited.customer.api.CustomerModule;
import io.sited.i18n.I18nModule;
import io.sited.user.api.UserModule;

/**
 * Created by hubery.chen on 2017/1/3.
 */
@ModuleInfo(name = "dealer.admin", require = {AdminModule.class, I18nModule.class,
        DealerModule.class, ProductModule.class, UserModule.class, CustomerModule.class, InsuranceModule.class, OrderModule.class})
public class DealerAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(DealerAdminController.class);
        adminConfig.controller(DealerProductAdminController.class);
        adminConfig.controller(CountryAdminController.class);
        adminConfig.console().install("dealer", new ConsoleOptions());
    }
}
