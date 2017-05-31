package io.sited.email.service;

import io.sited.email.EmailClient;
import io.sited.email.api.email.EmailRequest;
import io.sited.queue.QueueHandler;

import javax.inject.Inject;

/**
 * @author chi
 */
public class EmailQueueHandler implements QueueHandler<EmailRequest> {
    @Inject
    EmailClient emailClient;
    @Inject
    EmailTrackingService emailTrackingService;

    @Override
    public void handle(EmailRequest event) throws Throwable {
        boolean sent = emailClient.send(event);
        if (sent) {
            emailTrackingService.success(event);
        } else {
            emailTrackingService.failed(event);
        }
    }
}
