package com.caej.order.domain;

import java.time.LocalDateTime;

import com.caej.order.order.OrderStatusView;
import com.caej.order.order.ShippingStatus;
import com.caej.order.payment.PaymentMethodView;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author chi
 */
@Entity(name = "orders")
public class Order {
    @Id(autoGenerated = false)
    public String id;
    @Field(name = "payment_id")
    public String paymentId;
    @Field(name = "product_id")
    public String productId;
    @Field(name = "vendor_id")
    public String vendorId;
    @Field(name = "product_name")
    public String productName;
    @Field(name = "product_display_name")
    public String productDisplayName;
    @Field(name = "product_description")
    public String productDescription;
    @Field(name = "customer_id")
    public String customerId;
    @Field(name = "customer_name")
    public String customerName;
    @Field(name = "dealer_id")
    public String dealerId;
    @Field(name = "dealer_name")
    public String dealerName;
    @Field(name = "commission_rate")
    public Integer commissionRate;
    @Field(name = "form")
    public String form;
    @Field(name = "price")
    public Double price;
    @Field(name = "shipping_fee")
    public Double shippingFee;
    @Field(name = "discount")
    public Double discount;
    @Field(name = "total")
    public Double total;

    @Field(name = "status")
    public OrderStatusView orderStatus;
    @Field(name = "shipping_status")
    public ShippingStatus shippingStatus;

    @Field(name = "plan_start_time")
    public LocalDateTime planStartTime;
    @Field(name = "period_display_name")
    public String periodDisplayName;
    @Field(name = "period_unit")
    public String periodUnit;
    @Field(name = "period_value")
    public Integer periodValue;
    @Field(name = "unit")
    public String unit;

    @Field(name = "order_date")
    public LocalDateTime orderDate;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "created_by")
    public String createdBy;

    @Field(name = "trans_no")
    public String transNo;
    @Field(name = "trans_type")
    public String transType;
    @Field(name = "refund_id")
    public String refundId;
    @Field(name = "errors")
    public String errors;

    @Field(name = "out_trade_no")
    public String outTradeNo;
    @Field(name = "apply_code")
    public String applyCode;
    @Field(name = "payment_method")
    public PaymentMethodView paymentMethod;
    @Field(name = "insured_name")
    public String insuredName;
    @Field(name = "insured_id_type")
    public String insuredIdType;
    @Field(name = "insured_id_number")
    public String insuredIdNumber;
    @Field(name = "insured_phone")
    public String insuredPhone;
    @Field(name = "insured_email")
    public String insuredEmail;
    @Field(name = "policy_holder_name")
    public String policyHolderName;
    @Field(name = "policy_holder_id_type")
    public String policyHolderIdType;
    @Field(name = "policy_holder_id_number")
    public String policyHolderIdNumber;
    @Field(name = "policy_holder_phone")
    public String policyHolderPhone;
    @Field(name = "policy_holder_email")
    public String policyHolderEmail;
    @Field(name = "archive_url")
    public String archiveUrl;
}
