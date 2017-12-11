package io.sited.customer.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author miller
 */
@ModuleInfo(name = "customer.api", require = APIModule.class)
public class CustomerModule extends Module {
    @Override
    protected void configure() throws Exception {
        CustomerOptions options = options(CustomerOptions.class);
        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(CustomerWebService.class, options.url);
        apiConfig.client(AddressWebService.class, options.url);
        apiConfig.client(CountryWebService.class, options.url);
        apiConfig.client(CustomerIdentificationWebService.class, options.url);
    }
}
