package io.sited.message.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author chi
 */
@ModuleInfo(name = "message", require = APIModule.class)
public class MessageModule extends Module {
    @Override
    protected void configure() throws Exception {
        MessageOptions options = options(MessageOptions.class);
        require(APIConfig.class).client(MessageWebService.class, options.url);
    }
}
