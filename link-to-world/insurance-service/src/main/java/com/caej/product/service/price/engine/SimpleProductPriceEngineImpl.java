package com.caej.product.service.price.engine;

import java.text.DecimalFormat;
import java.util.Map;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceResponse;
import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.price.ProductPriceResponse;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductInsurance;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductService;
import com.caej.product.service.client.EnumInvoiceDeliverTypeWebServiceClient;
import com.caej.product.service.client.InsuranceWebServiceClient;
import com.caej.product.service.price.InsurancePriceEngine;
import com.caej.product.service.price.InsurancePriceRequest;
import com.caej.product.service.price.InsurancePriceResponse;
import com.caej.product.service.price.ProductPriceEngine;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.form.Form;

/**
 * @author chi
 */
public class SimpleProductPriceEngineImpl implements ProductPriceEngine {
    private final Map<String, InsurancePriceEngine> engines = Maps.newHashMap();
    @Inject
    ProductService productService;
    @Inject
    ProductFormService productFormService;
    @Inject
    InsuranceWebServiceClient insuranceWebService;
    @Inject
    EnumInvoiceDeliverTypeWebServiceClient enumInvoiceDeliverTypeWebService;

    @Override
    public ProductPriceResponse calculate(ProductPriceRequest request) {
        Product product = productService.get(new ObjectId(request.productId));
        Form form = productFormService.form(product, request.formValue, true, "calculate");

        ProductPriceResponse price = new ProductPriceResponse();
        price.price = 0.0;
        price.detail = Lists.newArrayList();

        for (ProductInsurance insurance : product.insurances) {
            InsuranceResponse insuranceResponse = insuranceWebService.get(insurance.insuranceId.toHexString());
            InsurancePriceRequest insurancePriceRequest = new InsurancePriceRequest();
            insurancePriceRequest.product = product;
            insurancePriceRequest.form = form;
            insurancePriceRequest.insurance = insuranceResponse;
            InsurancePriceResponse insurancePriceResponse = engines.get(insuranceResponse.priceTable.engine).calculate(insurancePriceRequest);
            price.price += insurancePriceResponse.price;
            ProductPriceResponse.InsurancePrice insurancePrice = new ProductPriceResponse.InsurancePrice();
            insurancePrice.id = insurance.insuranceId.toHexString();
            insurancePrice.price = insurancePriceResponse.price;
            price.detail.add(insurancePrice);
        }
        Integer unit = form.group("product").value("unit");
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        price.price = Double.valueOf(decimalFormat.format(price.price * unit));
        price.salePrice = Double.valueOf(decimalFormat.format(price.price * product.discount));
        price.discount = Double.valueOf(decimalFormat.format(price.price - price.salePrice));
        price.invoiceFee = invoiceDeliverPrice(form);
        price.shippingFee = 0D;
        price.total = price.salePrice + price.invoiceFee + price.shippingFee;
        return price;
    }

    private Double invoiceDeliverPrice(Form form) {
        if (form.value.get("others") == null) return 0D;
        if (form.group("others").values.get("invoiceDeliverType") == null) return 0D;
        return enumInvoiceDeliverTypeWebService.price(form.group("others").value("invoiceDeliverType"));
    }

    public SimpleProductPriceEngineImpl setInsurancePriceEngine(String type, InsurancePriceEngine insurancePriceEngine) {
        engines.put(type, insurancePriceEngine);
        return this;
    }
}
