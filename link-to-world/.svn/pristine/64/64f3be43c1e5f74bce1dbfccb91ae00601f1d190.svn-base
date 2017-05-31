package com.caej.underwriting.service;


import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.insurance.api.EnumInvoiceDeliverTypeWebService;
import com.caej.order.OrderWebService;
import com.caej.order.PaymentWebService;
import com.caej.order.order.OrderView;
import com.caej.order.payment.PaymentView;
import com.caej.product.api.ProductPriceWebService;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.price.ProductPriceResponse;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductService;

import io.sited.form.Form;

/**
 * @author miller
 */
public class UnderwritingRequestBuildService {
    @Inject
    ProductPriceWebService productPriceWebService;
    @Inject
    ProductWebService productWebService;
    @Inject
    PaymentWebService paymentWebService;
    @Inject
    OrderWebService orderWebService;
    @Inject
    ProductFormService productFormService;
    @Inject
    EnumInvoiceDeliverTypeWebService enumInvoiceDeliverTypeWebService;
    @Inject
    ProductService productService;

    public UnderwritingRequest buildRequest(String orderId, String type) {
        OrderView orderView = orderWebService.get(orderId);
        return buildRequest(orderView, type);
    }

    public UnderwritingRequest buildRequest(OrderView orderView, String type) {
        ProductPriceRequest productPriceRequest = new ProductPriceRequest();
        productPriceRequest.productId = orderView.productId;
        productPriceRequest.formValue = orderView.form;
        ProductPriceResponse productPriceResponse = productPriceWebService.calculate(productPriceRequest);
        Form form = productFormService.form(productService.get(new ObjectId(orderView.productId)), orderView.form, true, "preview");
        ProductResponse productResponse = productWebService.get(orderView.productId);
        PaymentView paymentView = paymentWebService.get(orderView.paymentId);

        UnderwritingRequestBuilder underwritingRequestBuilder = new UnderwritingRequestBuilder(orderView, type, form, productResponse, paymentView);
        return underwritingRequestBuilder
            .initApplication(productPriceResponse.price, invoicePremium(form)).build();
    }

    private Double invoicePremium(Form form) {
        if (form.value.get("others") == null) return 0D;
        if (form.group("others").values.get("invoiceDeliverType") == null) return 0D;
        return enumInvoiceDeliverTypeWebService.price(form.group("others").value("invoiceDeliverType"));
    }
}
