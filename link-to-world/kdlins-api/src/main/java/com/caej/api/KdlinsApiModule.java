package com.caej.api;

import com.caej.api.esb.KdlinsESBWebService;
import com.caej.api.pay.KdlinsPayWebService;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author miller
 */
@ModuleInfo(name = "kdlins.api", require = {APIModule.class})
public class KdlinsApiModule extends Module {
    @Override
    protected void configure() throws Exception {
        KdlinsOptions options = options(KdlinsOptions.class);
        require(APIConfig.class).client(KdlinsESBWebService.class, options.esbServiceURL, true);
        require(APIConfig.class).client(KdlinsPayWebService.class, options.payServiceURL, false);
    }
}