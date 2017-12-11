package io.sited.email.service;

import com.google.common.collect.Lists;
import io.sited.email.SMTPOptions;
import io.sited.email.api.email.EmailRequest;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author chi
 */
@Ignore
public class SMTPEmailClientImplTest {
    @Test
    public void send() {
        SMTPOptions smtpOptions = new SMTPOptions();
        smtpOptions.host = "smtp.qq.com";
        smtpOptions.port = "587";
        smtpOptions.username = "3262866804@qq.com";
        smtpOptions.password = "ebtutaqbvcsecjfd";

        SMTPEmailClientImpl emailClient = new SMTPEmailClientImpl(smtpOptions);
        EmailRequest request = new EmailRequest();
        request.from = "3262866804@qq.com";
        request.to = Lists.newArrayList("chi@app4j.rog");
        request.subject = "test";
        request.content = "test";
        emailClient.send(request);
    }
}