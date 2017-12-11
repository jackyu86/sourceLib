package io.sited.message.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "message_message")
public class Message {
    @Id
    public ObjectId id;
    @Field(name = "from")
    public String from;
    @Field(name = "to")
    public String to;
    @Field(name = "title")
    public String title;
    @Field(name = "content")
    public String content;
    @Field(name = "type")
    public String type;
    @Field(name = "status")
    public MessageStatus status;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_by")
    public String updatedBy;
}
