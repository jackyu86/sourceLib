package com.caej.esb.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "esb_record")
public class ESBRecord {
    @Id
    public ObjectId id;
    @Field(name = "serial_number")
    public String serialNumber;
    @Field(name = "req_time")
    public String reqTime;
    @Field(name = "items")
    public String items;
    @Field(name = "status")
    public String status;
    @Field(name = "errors")
    public String errors;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
}
