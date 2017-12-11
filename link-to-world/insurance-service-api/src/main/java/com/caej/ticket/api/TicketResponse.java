package com.caej.ticket.api;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class TicketResponse {
    public String id;
    public String fullName;
    public String phone;
    public String content;
    public String comment;
    public TicketStatusView status;
    public LocalDateTime createdTime;
    public String createdBy;
}
