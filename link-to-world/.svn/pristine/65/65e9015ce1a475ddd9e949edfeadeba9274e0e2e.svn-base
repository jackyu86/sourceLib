package io.sited.customer.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "customer_identification")
public class CustomerIdentification {
    @Id
    public ObjectId id;
    @Field(name = "customer_id")
    public String customerId;
    @Field(name = "type")
    public String type;
    @Field(name = "number")
    public String number;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
