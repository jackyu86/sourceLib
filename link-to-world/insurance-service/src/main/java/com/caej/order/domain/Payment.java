package com.caej.order.domain;

import java.time.LocalDateTime;

import com.caej.order.payment.PaymentMethodView;
import com.caej.order.payment.PaymentStatusView;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author chi
 */
@Entity(name = "payment")
public class Payment {
    @Id(autoGenerated = false)
    public String id;
    @Field(name = "payment_time")
    public LocalDateTime paymentTime;
    @Field(name = "status")
    public PaymentStatusView status;
    @Field(name = "method")
    public PaymentMethodView method;
    @Field(name = "pay_mode")
    public String payMode;
    @Field(name = "total")
    public Double total;
    @Field(name = "full_name")
    public String fullName;
    @Field(name = "phone")
    public String phone;
    @Field(name = "address")
    public String address;
    @Field(name = "city")
    public String city;
    @Field(name = "province")
    public String province;
    @Field(name = "zip_code")
    public String zipCode;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "refund_id")
    public String refundId;
    @Field(name = "out_trade_no")
    public String outTradeNo;
    @Field(name = "bill_id")
    public String billId;
    @Field(name = "bill_url")
    public String billUrl;
    @Field(name = "title")
    public String title;
    @Field(name = "description")
    public String description;
    @Field(name = "optional")
    public String optional;
    @Field(name = "return_url")
    public String returnUrl;
    @Field(name = "notify_url")
    public String notifyUrl;
    @Field(name = "timeout")
    public Integer timeout;
    @Field(name = "limit_pay")
    public String limitPay;
    @Field(name = "pc_pay")
    public String pcPay;
    @Field(name = "open_id")
    public String openId;
    @Field(name = "show_url")
    public String showUrl;
    @Field(name = "qr_code_mode")
    public String qrCodeMode;
    @Field(name = "qr_code_width")
    public String qrCodeWidth;
    @Field(name = "sign")
    public String sign;
    @Field(name = "pay_timestamp")
    public Long payTimestamp;
    @Field(name = "code_url")
    public String codeUrl;
    @Field(name = "qr_code")
    public String qrCode;
    @Field(name = "pay_url")
    public String payUrl;
    @Field(name = "to_pay_error")
    public String toPayError;
    @Field(name = "pc_out_trade_no")
    public String pcOutTradeNo;
    @Field(name = "type")
    public String type;
    @Field(name = "notify_pay_time")
    public LocalDateTime notifyPayTime;
    @Field(name = "message_detail")
    public String messageDetail;
    @Field(name = "transaction_id")
    public String transactionId;
    @Field(name = "customer_id")
    public String customerId;
    @Field(name = "latest_effect_time")
    public LocalDateTime latestEffectTime;
}