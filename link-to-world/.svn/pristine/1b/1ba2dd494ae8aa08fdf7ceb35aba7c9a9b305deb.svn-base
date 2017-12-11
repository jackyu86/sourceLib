package com.caej.cart.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author chi
 */
@ModuleInfo(name = "cart.api", require = {APIModule.class})
public class CartModule extends Module {
    @Override
    protected void configure() throws Exception {
        CartOptions options = options(CartOptions.class);
        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(CartWebService.class, options.url);
    }
}
