package io.sited.email.api;

import io.sited.email.api.email.EmailRequest;
import io.sited.email.api.email.EmailResponse;
import io.sited.http.POST;
import io.sited.http.Path;

/**
 * @author chi
 */
public interface EmailWebService {
    @Path("/api/email")
    @POST
    EmailResponse send(EmailRequest request);

    @Path("/api/email/async")
    @POST
    void asyncSend(EmailRequest request);
}
