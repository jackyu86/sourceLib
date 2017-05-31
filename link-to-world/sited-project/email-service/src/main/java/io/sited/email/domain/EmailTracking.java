package io.sited.email.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import io.sited.email.api.email.EmailStatus;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chi
 */
@Entity(name = "email_tracking")
public class EmailTracking {
    @Id
    public ObjectId id;
    @Field(name = "from")
    public String from;
    @Field(name = "reply_to")
    public String replyTo;
    @Field(name = "to")
    public List<String> to;
    @Field(name = "subject")
    public String subject;
    @Field(name = "content")
    public String content;
    @Field(name = "status")
    public EmailStatus status;
    @Field(name = "error_message")
    public String errorMessage;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
