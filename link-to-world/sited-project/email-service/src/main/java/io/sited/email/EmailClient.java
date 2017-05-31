package io.sited.email;

import io.sited.email.api.email.EmailRequest;

/**
 * @author chi
 */
public interface EmailClient {
    boolean send(EmailRequest request);
}
