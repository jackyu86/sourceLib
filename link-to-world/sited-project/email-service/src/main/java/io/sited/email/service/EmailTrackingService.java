package io.sited.email.service;

import io.sited.db.MongoRepository;
import io.sited.email.api.email.EmailRequest;
import io.sited.email.api.email.EmailStatus;
import io.sited.email.domain.EmailTracking;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * @author chi
 */
public class EmailTrackingService {
    @Inject
    MongoRepository<EmailTracking> repository;

    public EmailTracking success(EmailRequest request) {
        EmailTracking emailTracking = emailTracking(request);
        emailTracking.status = EmailStatus.SUCCESS;

        repository.insert(emailTracking);
        return emailTracking;
    }

    public EmailTracking failed(EmailRequest request) {
        EmailTracking emailTracking = emailTracking(request);
        emailTracking.status = EmailStatus.FAILED;

        repository.insert(emailTracking);
        return emailTracking;
    }

    private EmailTracking emailTracking(EmailRequest request) {
        EmailTracking emailTracking = new EmailTracking();
        emailTracking.from = request.from;
        emailTracking.to = request.to;
        emailTracking.replyTo = request.replyTo;
        emailTracking.subject = request.subject;
        emailTracking.content = request.content;
        emailTracking.status = EmailStatus.FAILED;
        emailTracking.createdBy = request.requestBy;
        emailTracking.createdTime = LocalDateTime.now();
        return emailTracking;
    }
}
