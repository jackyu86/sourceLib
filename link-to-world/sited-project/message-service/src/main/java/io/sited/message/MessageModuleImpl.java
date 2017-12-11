package io.sited.message;

import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.message.api.MessageModule;
import io.sited.message.api.MessageWebService;
import io.sited.message.domain.Message;
import io.sited.message.service.MessageService;
import io.sited.message.web.MessageWebServiceImpl;

/**
 * @author chi
 */
@ModuleInfo(name = "message", require = {APIModule.class, DBModule.class})
public class MessageModuleImpl extends MessageModule {
    @Override
    protected void configure() throws Exception {
        require(MongoConfig.class)
            .entity(Message.class);

        bind(MessageService.class);
        require(APIConfig.class).service(MessageWebService.class, MessageWebServiceImpl.class);
    }
}
