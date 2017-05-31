package com.caej.product.service;


import javax.inject.Inject;

import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.price.ProductPriceResponse;
import com.caej.product.service.price.ProductPriceEngine;

/**
 * @author chi
 */
public class ProductPriceService {
    @Inject
    ProductPriceEngine productPriceEngine;

    public ProductPriceResponse calculate(ProductPriceRequest request) {
        return productPriceEngine.calculate(request);
    }
}
