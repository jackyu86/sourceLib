package io.sited.email.controller;

import io.sited.email.EmailClient;
import io.sited.email.api.EmailWebService;
import io.sited.email.api.email.EmailRequest;
import io.sited.email.api.email.EmailResponse;
import io.sited.email.domain.EmailTracking;
import io.sited.email.service.EmailTrackingService;
import io.sited.queue.Queue;

import javax.inject.Inject;

/**
 * @author chi
 */
public class EmailWebServiceImpl implements EmailWebService {
    @Inject
    EmailClient emailClient;

    @Inject
    EmailTrackingService emailTrackingService;

    @Inject
    Queue<EmailRequest> queue;

    @Override
    public EmailResponse send(EmailRequest request) {
        boolean sent = emailClient.send(request);
        if (sent) {
            EmailTracking emailTracking = emailTrackingService.success(request);
            return response(emailTracking);
        } else {
            EmailTracking emailTracking = emailTrackingService.failed(request);
            return response(emailTracking);
        }
    }

    @Override
    public void asyncSend(EmailRequest request) {
        queue.publish(request);
    }

    private EmailResponse response(EmailTracking emailTracking) {
        EmailResponse response = new EmailResponse();
        response.id = emailTracking.id.toHexString();
        response.status = emailTracking.status;
        return response;
    }
}
