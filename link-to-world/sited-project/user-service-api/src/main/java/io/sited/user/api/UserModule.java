package io.sited.user.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author chi
 */
@ModuleInfo(name = "user.api", require = APIModule.class)
public class UserModule extends Module {
    @Override
    protected void configure() throws Exception {
        UserOptions options = options(UserOptions.class);

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(RoleWebService.class, options.url);
        apiConfig.client(UserWebService.class, options.url);
        apiConfig.client(PinCodeWebService.class, options.url);
    }
}
