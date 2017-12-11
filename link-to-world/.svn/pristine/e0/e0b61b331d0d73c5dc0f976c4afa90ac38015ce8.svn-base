package com.caej.underwriting.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.order.order.OrderView;
import com.caej.order.payment.PaymentView;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.service.field.TravelDest;
import com.caej.underwriting.builder.UnderwritingBeneficiaryBuilder;
import com.caej.underwriting.builder.UnderwritingCancelBuilder;
import com.caej.underwriting.builder.UnderwritingFlightBuilder;
import com.caej.underwriting.builder.UnderwritingInformationBuilder;
import com.caej.underwriting.builder.UnderwritingInsIntervalBuilder;
import com.caej.underwriting.builder.UnderwritingInsuredsBuilder;
import com.caej.underwriting.builder.UnderwritingOthersBuilder;
import com.caej.underwriting.builder.UnderwritingPaymentMethodBuilder;
import com.caej.underwriting.builder.UnderwritingPlanBuilder;
import com.caej.underwriting.builder.UnderwritingPolicyHolderBuilder;
import com.caej.util.UUIDUtil;
import com.google.common.collect.Lists;

import io.sited.form.FieldGroup;
import io.sited.form.Form;

/**
 * @author miller
 */
public class UnderwritingRequestBuilder {
    private final static String DEFAULT_DELIVER_TYPE = "5";
    private final OrderView order;
    private final UnderwritingRequest request;
    private final Form form;
    private final List<String> groupNames;
    private final ProductResponse product;
    private final PaymentView paymentView;
    private final String type;
    private Double invoicePremium = 0D;

    public UnderwritingRequestBuilder(OrderView order, String type, Form form, ProductResponse product, PaymentView paymentView) {
        this.order = order;
        this.form = form;
        this.product = product;
        this.paymentView = paymentView;
        this.type = type;
        List<String> names = Lists.newArrayList();
        form.groups.forEach(fieldGroup -> {
            names.add(fieldGroup.name);
        });
        this.groupNames = names;
        this.request = new UnderwritingRequest();
        this.request.main = buildMain(type);
        this.request.application = new UnderwritingRequest.UnderwritingRequestApplication();
    }

    private UnderwritingRequest.UnderwritingRequestMain buildMain(String type) {
        UnderwritingRequest.UnderwritingRequestMain main = new UnderwritingRequest.UnderwritingRequestMain();
        main.transNo = UUIDUtil.generate();
        main.transType = type;
        LocalDateTime now = LocalDateTime.now();
        main.transDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        main.transTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return main;
    }

    public UnderwritingRequest build() {
        this.addPolicyHolder()
            .addInsureds()
            .addBeneficiaries()
            .addDeclarations()
            .addPlan()
            .addPaymentMethod()
            .addInsInterval()
            .addOthers(this.invoicePremium)
            .addFlight();
        if ("201".equals(this.type) || "301".equals(this.type)) {
            this.addCancel();
        }
        return this.request;
    }

    private UnderwritingRequestBuilder addFlight() {
        this.request.application.flight = UnderwritingFlightBuilder.build(form);
        return this;
    }

    private UnderwritingRequestBuilder addOthers(Double invoicePremium) {
        this.request.application.others = UnderwritingOthersBuilder.build(form, invoicePremium);
        return this;
    }

    private UnderwritingRequestBuilder addInsInterval() {
        this.request.application.insInterval = UnderwritingInsIntervalBuilder.build(form, product);
        return this;
    }

    private UnderwritingRequestBuilder addPaymentMethod() {
        this.request.application.paymentMethod = UnderwritingPaymentMethodBuilder.build(paymentView);
        return this;
    }

    private UnderwritingRequestBuilder addPlan() {
        this.request.application.plan = UnderwritingPlanBuilder.build(product);
        return this;
    }

    //todo autoMobiles
    //todo flight

    private UnderwritingRequestBuilder addDeclarations() {
        this.request.application.declarations = UnderwritingInformationBuilder.build(form);
        return this;
    }

    private UnderwritingRequestBuilder addBeneficiaries() {
        this.request.application.beneficiarys = UnderwritingBeneficiaryBuilder.build(form);
        return this;
    }

    private UnderwritingRequestBuilder addInsureds() {
        this.request.application.insureds = UnderwritingInsuredsBuilder.build(order);
        return this;
    }

    private UnderwritingRequestBuilder addPolicyHolder() {
        Form.Group policyHolder = form.group("policy-holder");
        FieldGroup fieldGroup = form.fieldGroup("policy-holder");
        this.request.application.policyHolder = UnderwritingPolicyHolderBuilder.build(policyHolder, fieldGroup);
        return this;
    }

    private UnderwritingRequestBuilder addCancel() {
        this.request.cancel = UnderwritingCancelBuilder.build(this.request);
        return this;
    }

    public UnderwritingRequestBuilder initApplication(Double premium, Double invoicePremium) {
        if ("201".equals(this.type)) {
            this.request.application.applyCode = this.order.items.get(0).policyCode;
        } else {
            this.request.application.applyCode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        }
        this.request.application.applyDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        this.request.application.agencyCode = "";
        this.request.application.premium = premium;
        if (form.hasGroup("policy")) {
            Form.Group policy = form.group("policy");
            FieldGroup fieldGroup = form.fieldGroup("policy");
            if (fieldGroup.field("deliverType").isPresent()) {
                this.request.application.deliverType = policy.value("deliverType");
            } else {
                this.request.application.deliverType = DEFAULT_DELIVER_TYPE;
            }
            if (fieldGroup.field("travelDest").isPresent()) {
                TravelDest travelDest = policy.value("travelDest");
                this.request.application.travelDestCode = travelDest.code;
                this.request.application.travelDestName = travelDest.name;
            }
        } else {
            this.request.application.deliverType = DEFAULT_DELIVER_TYPE;
        }
        this.invoicePremium = invoicePremium;
        return this;
    }
}
