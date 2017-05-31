package io.sited.email.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author chi
 */
@ModuleInfo(name = "email.api", require = APIModule.class)
public class EmailModule extends Module {
    @Override
    protected void configure() throws Exception {
        require(APIConfig.class).client(EmailWebService.class, options(EmailAPIOptions.class).url);
    }
}
