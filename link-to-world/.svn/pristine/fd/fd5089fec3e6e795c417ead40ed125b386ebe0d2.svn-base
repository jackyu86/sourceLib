package com.caej.site.user.handler;

import com.google.common.collect.Lists;
import io.sited.email.api.EmailWebService;
import io.sited.email.api.email.EmailRequest;
import io.sited.queue.QueueHandler;
import io.sited.user.web.message.CreatePinCodeEvent;

import javax.inject.Inject;

/**
 * TODO: pinCode template
 *
 * @author chi
 */
public class EmailPinCodeQueueHandler implements QueueHandler<CreatePinCodeEvent> {
    @Inject
    EmailWebService emailWebService;

    @Override
    public void handle(CreatePinCodeEvent event) throws Throwable {
        if (event.email != null) {
            String template = String.format("Your pin code is %s", event.code);
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.subject = "【君龙短意险】验证码" + event.code;
            emailRequest.from = "some@some.com";
            emailRequest.to = Lists.newArrayList(event.email);
            emailRequest.content = template;
            emailWebService.send(emailRequest);
        }
    }
}
