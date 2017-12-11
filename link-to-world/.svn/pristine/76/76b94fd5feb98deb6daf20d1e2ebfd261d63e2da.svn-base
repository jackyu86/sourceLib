package com.caej.product.web;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.order.order.OrderView;
import com.caej.product.api.ProductConvertWebService;
import com.caej.product.api.orderview.OrderViewBuildRequest;
import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.domain.Product;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductPriceService;
import com.caej.product.service.ProductService;
import com.caej.product.service.orderview.OrderViewBuilder;

import io.sited.form.Form;

/**
 * @author miller
 */
public class ProductConvertWebServiceImpl implements ProductConvertWebService {
    @Inject
    ProductService productService;
    @Inject
    ProductPriceService productPriceService;
    @Inject
    ProductFormService productFormService;

    @Override
    public OrderView buildOrderView(OrderViewBuildRequest request) {
        OrderViewBuilder builder = new OrderViewBuilder();
        builder.buildRequest = request;
        Product product = productService.get(new ObjectId(request.productId));
        builder.product = product;
        Form form = productFormService.form(product, request.value, false, "checkout");
        builder.form = form;
        ProductPriceRequest productPriceRequest = new ProductPriceRequest();
        productPriceRequest.formValue = form.value;
        productPriceRequest.productId = request.productId;
        builder.productPrice = productPriceService.calculate(productPriceRequest);
        return builder.build();
    }
}
