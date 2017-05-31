package com.caej.product.service.orderview;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.ShippingStatus;
import com.caej.order.payment.PaymentMethodView;
import com.caej.product.api.orderview.OrderViewBuildRequest;
import com.caej.product.api.price.ProductPriceResponse;
import com.caej.product.api.product.ProductPeriodStartTimeType;
import com.caej.product.domain.Product;
import com.caej.product.service.field.Identification;
import com.google.common.collect.Lists;

import io.sited.form.FieldGroup;
import io.sited.form.Form;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class OrderViewBuilder {
    public OrderViewBuildRequest buildRequest;
    public Form form;
    public Product product;
    public ProductPriceResponse productPrice;

    public OrderView build() {
        return order(product, productPrice);
    }

    private OrderView order(Product productResponse, ProductPriceResponse productPrice) {
        OrderView orderView = new OrderView();
        orderView.id = orderId();
        orderView.productId = buildRequest.productId;
        orderView.vendorId = productResponse.insuranceVendorId.toHexString();
        orderView.productName = productResponse.name;
        orderView.productDisplayName = productResponse.displayName;
        orderView.productDescription = "";
        orderView.form = buildRequest.value;

        orderView.price = productPrice.price;
        orderView.shippingFee = productPrice.shippingFee;
        orderView.invoiceFee = productPrice.invoiceFee;
        orderView.discount = productPrice.discount;
        orderView.total = productPrice.total;

        orderView.planStartTime = getPlanStartTime(form, product);
        InsurancePeriodView insurancePeriod = JSON.fromJSON(JSON.toJSON(form.group("plan").values.get("period")), InsurancePeriodView.class);
        orderView.periodDisplayName = insurancePeriod.displayName;
        orderView.periodUnit = insurancePeriod.unit.name();
        orderView.periodValue = insurancePeriod.value;
        orderView.unit = form.group("product").values.get("unit").toString();
        orderView.items = Lists.newArrayList();
        orderView.orderStatus = OrderStatusView.DRAFT;

        FieldGroup insured = form.fieldGroup("insured");
        if (Boolean.TRUE.equals(insured.multiple)) {
            Form.ListGroup list = form.listGroup("insured");
            for (int i = 0; i < list.size(); i++) {
                OrderView.OrderItemView orderItemView = new OrderView.OrderItemView();
                orderItemView.id = orderItemId(orderView.id, i + 1);
                orderItemView.price = orderView.price / list.size();
                orderItemView.orderDate = orderView.orderDate;
                orderItemView.orderStatus = orderView.orderStatus;
                Map<String, Object> insuredMap = list.values.get(i);
                orderItemView.name = insuredMap.get("name").toString();
                orderItemView.form = insuredMap;
                orderView.items.add(orderItemView);
            }
        } else {
            OrderView.OrderItemView orderItemView = new OrderView.OrderItemView();
            orderItemView.id = orderItemId(orderView.id, 1);
            orderItemView.price = orderView.price;
            orderItemView.orderDate = orderView.orderDate;
            orderItemView.orderStatus = orderView.orderStatus;
            Map<String, Object> insuredMap = form.group("insured").values;
            orderItemView.name = insuredMap.get("name").toString();
            orderItemView.form = insuredMap;
            orderView.items.add(orderItemView);
        }

        orderView.shippingStatus = ShippingStatus.NONE;
        orderView.orderDate = LocalDateTime.now();
        orderView.paymentMethod = PaymentMethodView.NOTSELECTED;
        getPolicyHolderValue(orderView);
        getInsuredValue(orderView);
        return orderView;
    }

    private String orderId() {
        Random random = ThreadLocalRandom.current();
        StringBuilder b = new StringBuilder();
        b.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        String timestamp = String.valueOf(System.currentTimeMillis());
        b.append(timestamp.substring(timestamp.length() - 3));
        for (int i = 0; i < 4; i++) {
            b.append(Math.abs(random.nextInt(10)));
        }
        return b.toString();
    }

    private LocalDateTime getPlanStartTime(Form form, Product product) {
        if (ProductPeriodStartTimeType.USER_INPUT.equals(product.period.startTimeType) || ProductPeriodStartTimeType.DEFAULT.equals(product.period.startTimeType)) {
            LocalDate startTime = form.group("plan").value("startTime");
            return LocalDateTime.of(startTime, LocalTime.of(0, 0, 0));
        }
        if (ProductPeriodStartTimeType.LATEST.equals(product.period.startTimeType)) {
            InsurancePeriod earliestInsuranceTime = product.period.earliestInsuranceTime;
            LocalDate localDate = LocalDate.now();
            LocalDate startTime = LocalDate.now();
            switch (earliestInsuranceTime.unit) {
                case DAY:
                    startTime = localDate.plus(earliestInsuranceTime.value - 1, ChronoUnit.DAYS);
                    break;
                case MONTH:
                    startTime = localDate.plus(earliestInsuranceTime.value, ChronoUnit.MONTHS);
                    break;
                case YEAR:
                    startTime = localDate.plus(earliestInsuranceTime.value, ChronoUnit.YEARS);
                    break;
                //todo age
                case AGE:
                case LIFE:
                case NONE:
                default:
                    break;
            }
            return LocalDateTime.of(startTime, LocalTime.of(0, 0, 0));
        }
        return null;
    }

    private String orderItemId(String id, int index) {
        return id + '-' + index;
    }

    private void getPolicyHolderValue(OrderView orderView) {
        Map<String, Object> policyHolder = form.group("policy-holder").values;
        if (policyHolder.get("name") != null) {
            orderView.policyHolderName = policyHolder.get("name").toString();
        }
        if (policyHolder.get("id") != null) {
            Identification identification = JSON.fromJSON(JSON.toJSON(policyHolder.get("id")), Identification.class);
            orderView.policyHolderIdType = identification.type;
            orderView.policyHolderIdNumber = identification.number;
        }
        if (policyHolder.get("phone") != null) {
            orderView.policyHolderPhone = policyHolder.get("phone").toString();
        }
        if (policyHolder.get("email") != null) {
            orderView.policyHolderEmail = policyHolder.get("email").toString();
        }
    }

    private void getInsuredValue(OrderView orderView) {
        Map<String, Object> insured = getSingleInsured();
        if (insured.get("name") != null) {
            orderView.insuredName = insured.get("name").toString();
        }
        if (insured.get("id") != null) {
            Identification identification = JSON.fromJSON(JSON.toJSON(insured.get("id")), Identification.class);
            orderView.insuredIdType = identification.type;
            orderView.insuredIdNumber = identification.number;
        }
        if (insured.get("phone") != null) {
            orderView.insuredPhone = insured.get("phone").toString();
        }
        if (insured.get("email") != null) {
            orderView.insuredEmail = insured.get("email").toString();
        }
    }

    private Map<String, Object> getSingleInsured() {
        if (form.fieldGroup("insured").multiple != null && form.fieldGroup("insured").multiple) {
            return form.listGroup("insured").values.get(0);
        } else {
            return form.group("insured").values;
        }
    }
}
