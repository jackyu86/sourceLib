package com.caej.order.domain;

import java.time.LocalDateTime;

import com.caej.order.order.OrderStatusView;

import io.sited.db.Field;

/**
 * Created by hubery.chen on 2016/12/27.
 */
public class SearchSettlementResult {
    @Field(name = "dealer_id")
    public String dealerId;
    @Field(name = "dealer_name")
    public String dealerName;
    @Field(name = "commission_rate")
    public Integer commissionRate;
    @Field(name = "product_name")
    public String productName;
    @Field(name = "order_id")
    public String orderId;
    @Field(name = "policy_code")
    public String policyCode;
    @Field(name = "policy_holder_name")
    public String policyHolderName;
    @Field(name = "insured_name")
    public String insuredName;
    @Field(name = "insured_id_number")
    public String insuredIdNumber;
    @Field(name = "status")
    public OrderStatusView status;
    @Field(name = "order_date")
    public LocalDateTime orderDate;
}
