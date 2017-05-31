package com.caej.admin.product;

import java.util.Optional;

import javax.inject.Inject;

import com.caej.product.api.product.ProductDetailResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class ProductDetailResponseClient {
    @Inject
    Cache<ProductDetailResponse> cache;

    public void invalidate(String name) {
        Optional<ProductDetailResponse> optional = cache.get(name);
        if (!optional.isPresent()) {
            return;
        }
        cache.invalidate(name);
    }
}
