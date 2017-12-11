package com.caej.underwriting.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author miller
 */
@ModuleInfo(name = "underwriting.api", require = {APIModule.class})
public class UnderwritingModule extends Module {
    @Override
    protected void configure() throws Exception {
        UnderwritingAPIOptions options = options(UnderwritingAPIOptions.class);
        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(UnderwritingWebService.class, options.url);
    }
}
