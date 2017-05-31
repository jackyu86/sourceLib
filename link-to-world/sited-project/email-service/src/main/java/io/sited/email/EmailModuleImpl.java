package io.sited.email;

import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.email.api.EmailModule;
import io.sited.email.api.EmailWebService;
import io.sited.email.api.email.EmailRequest;
import io.sited.email.controller.EmailWebServiceImpl;
import io.sited.email.domain.EmailTracking;
import io.sited.email.service.EmailQueueHandler;
import io.sited.email.service.EmailTrackingService;
import io.sited.email.service.SMTPEmailClientImpl;
import io.sited.queue.Queue;
import io.sited.queue.QueueConfig;
import io.sited.queue.QueueModule;

/**
 * @author chi
 */
@ModuleInfo(name = "email", require = {DBModule.class, QueueModule.class, APIModule.class})
public class EmailModuleImpl extends EmailModule {
    @Override
    protected void configure() throws Exception {
        EmailOptions options = options(EmailOptions.class);
        if (options.smtp != null) {
            bind(EmailClient.class, bind(new SMTPEmailClientImpl(options.smtp)));
        } else {
            throw new StandardException("missing smtp options");
        }

        require(MongoConfig.class)
            .entity(EmailTracking.class);

        bind(EmailTrackingService.class);

        Queue<EmailRequest> queue = require(QueueConfig.class).create(EmailRequest.class);
        queue.addHandler(bind(EmailQueueHandler.class));

        require(APIConfig.class)
            .service(EmailWebService.class, EmailWebServiceImpl.class);
    }
}
