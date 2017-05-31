package com.caej.esb.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author miller
 */
@ModuleInfo(name = "esb.api", require = {APIModule.class})
public class ESBModule extends Module {
    @Override
    protected void configure() throws Exception {
        ESBOptions options = options(ESBOptions.class);
        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(ESBRecordWebService.class, options.url);
    }
}
