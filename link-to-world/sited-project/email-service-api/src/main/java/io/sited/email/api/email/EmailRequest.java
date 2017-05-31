package io.sited.email.api.email;

import java.util.List;

/**
 * @author chi
 */
public class EmailRequest {
    public String from;
    public List<String> to;
    public String replyTo;
    public String subject;
    public String content;
    public String requestBy;
}
