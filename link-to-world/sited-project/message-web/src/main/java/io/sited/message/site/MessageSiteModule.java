package io.sited.message.site;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.message.api.MessageModule;
import io.sited.web.WebModule;

/**
 * @author chi
 */
@ModuleInfo(name = "message.web", require = {WebModule.class, MessageModule.class})
public class MessageSiteModule extends Module {
    @Override
    protected void configure() throws Exception {
    }
}
