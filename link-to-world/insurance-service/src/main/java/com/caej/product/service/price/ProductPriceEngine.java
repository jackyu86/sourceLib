package com.caej.product.service.price;

import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.price.ProductPriceResponse;

/**
 * @author chi
 */
public interface ProductPriceEngine {
    ProductPriceResponse calculate(ProductPriceRequest request);
}
