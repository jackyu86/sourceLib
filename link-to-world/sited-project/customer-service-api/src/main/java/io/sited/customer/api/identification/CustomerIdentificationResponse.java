package io.sited.customer.api.identification;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class CustomerIdentificationResponse {
    public String id;
    public String customerId;
    public String type;
    public String number;
    public LocalDateTime createdTime;
    public String createdBy;
}
