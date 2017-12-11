package com.caej.underwriting.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

/**
 * @author miller
 */
@Entity(name = "underwriting_record")
public class UnderwritingRecord {
    @Id
    public ObjectId id;
    @Field(name = "order_id")
    public String orderId;
    @Field(name = "request")
    public String request;
    @Field(name = "response")
    public String response;
    @Field(name = "trade_status")
    public String tradeStatus;
    @Field(name = "error")
    public String error;
    @Field(name = "trans_no")
    public String transNo;
    @Field(name = "trans_type")
    public String transType;
    @Field(name = "trans_date")
    public String transDate;
    @Field(name = "trans_time")
    public String transTime;
}
