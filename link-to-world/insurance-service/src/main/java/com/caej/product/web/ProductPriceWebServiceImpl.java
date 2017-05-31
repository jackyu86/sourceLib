package com.caej.product.web;


import javax.inject.Inject;

import com.caej.product.api.ProductPriceWebService;
import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.price.ProductPriceResponse;
import com.caej.product.service.ProductPriceService;

/**
 * @author chi
 */
public class ProductPriceWebServiceImpl implements ProductPriceWebService {
    @Inject
    ProductPriceService productPriceService;

    @Override
    public ProductPriceResponse calculate(ProductPriceRequest request) {
        return productPriceService.calculate(request);
    }
}
