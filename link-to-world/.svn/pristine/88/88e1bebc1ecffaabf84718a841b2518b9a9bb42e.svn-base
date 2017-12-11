package com.caej.ticket.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "ticket")
public class Ticket {
    @Id
    public ObjectId id;
    @Field(name = "full_name")
    public String fullName;
    @Field(name = "phone")
    public String phone;
    @Field(name = "content")
    public String content;
    @Field(name = "comment")
    public String comment;
    @Field(name = "status")
    public TicketStatus status;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
