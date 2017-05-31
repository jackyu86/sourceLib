package io.sited.email.service;

import io.sited.StandardException;
import io.sited.email.EmailClient;
import io.sited.email.SMTPOptions;
import io.sited.email.api.email.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;


/**
 * @author chi
 */
public class SMTPEmailClientImpl implements EmailClient {
    private final Logger logger = LoggerFactory.getLogger(SMTPEmailClientImpl.class);
    private final Session mailSession;

    public SMTPEmailClientImpl(SMTPOptions options) {
        Properties properties = new Properties();
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.host", options.host);
        properties.setProperty("mail.port", options.port);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.mime.charset", "UTF-8");

        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", "javax.net.ssl.SSLSocketFactory");

        mailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(options.username, options.password);
            }
        });
    }

    @Override
    public boolean send(EmailRequest request) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setSubject(request.subject);
            message.setText(request.content);
            message.setFrom(new InternetAddress(request.from));

            Transport transport = mailSession.getTransport();
            transport.connect();
            transport.sendMessage(message, addresses(request.to));
            transport.close();
            return true;
        } catch (MessagingException e) {
            logger.error("failed to send email, from={}, to={}, subject={}", request.from, request.to, request.subject, e);
            throw new StandardException(e);
        }
    }

    private Address[] addresses(List<String> to) throws AddressException {
        Address[] addresses = new Address[to.size()];
        for (int i = 0; i < to.size(); i++) {
            addresses[i] = new InternetAddress(to.get(i));
        }
        return addresses;
    }
}
