package io.sited.message.admin;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.I18nModule;
import io.sited.message.admin.web.MessageAdminController;
import io.sited.message.api.MessageModule;

/**
 * @author chi
 */
@ModuleInfo(name = "message.admin", require = {AdminModule.class, MessageModule.class, I18nModule.class})
public class MessageAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        AdminConfig adminConfig = require(AdminConfig.class);

        adminConfig
            .controller(MessageAdminController.class);

        adminConfig.console().install("message");

        require(I18nConfig.class)
            .add("messages/message_en-US.properties")
            .add("messages/message_zh-CN.properties");
    }
}
