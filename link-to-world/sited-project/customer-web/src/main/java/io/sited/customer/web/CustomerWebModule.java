package io.sited.customer.web;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.customer.api.CustomerModule;
import io.sited.customer.web.controller.AddressAJAXController;
import io.sited.customer.web.controller.CountryAJAXController;
import io.sited.customer.web.controller.CustomerAJAXController;
import io.sited.customer.web.controller.CustomerIdentificationAJAXController;
import io.sited.customer.web.interceptor.UserRegisterInterceptor;
import io.sited.http.HttpConfig;
import io.sited.user.web.UserWebModule;
import io.sited.web.WebConfig;
import io.sited.web.WebModule;

/**
 * @author chi
 */
@ModuleInfo(name = "customer.web", require = {CustomerModule.class, WebModule.class, UserWebModule.class})
public class CustomerWebModule extends Module {
    @Override
    protected void configure() throws Exception {
        require(WebConfig.class)
            .controller(AddressAJAXController.class)
            .controller(CountryAJAXController.class)
            .controller(CustomerAJAXController.class)
            .controller(CustomerIdentificationAJAXController.class);

        require(HttpConfig.class)
            .interceptor("/ajax/user/register", bind(UserRegisterInterceptor.class));
    }
}
