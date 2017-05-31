package com.caej.admin;

import com.caej.admin.order.OrderAdminController;
import com.caej.insurance.api.InsuranceModule;
import com.caej.order.OrderModule;
import com.caej.product.api.ProductModule;
import com.caej.underwriting.api.UnderwritingModule;

import app.dealer.api.DealerModule;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.admin.ConsoleOptions;
import io.sited.customer.api.CustomerModule;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.impl.service.PropertiesMessageRepository;
import io.sited.user.api.UserModule;

/**
 * Created by hubery.chen on 2017/1/3.
 */
@ModuleInfo(name = "order.admin", require = {AdminModule.class, OrderModule.class, ProductModule.class,
        DealerModule.class, UserModule.class, CustomerModule.class, InsuranceModule.class, UnderwritingModule.class})
public class OrderAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(OrderAdminController.class);
        adminConfig.console().install("order", new ConsoleOptions());
        require(I18nConfig.class)
                .add(new PropertiesMessageRepository("messages/order_en-US.properties"))
                .add(new PropertiesMessageRepository("messages/order_zh-CN.properties"));

    }
}
