package com.caej.client;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.product.api.ProductDetailWebService;
import com.caej.product.api.product.ProductDetailResponse;
import com.google.common.collect.Lists;

import io.sited.cache.Cache;

/**
 * @author chi
 */
public class ProductDetailWebServiceClient {
    @Inject
    Cache<ProductDetailResponse> cache;
    @Inject
    ProductDetailWebService productDetailWebService;

    public ProductDetailResponse get(String name) {
        Optional<ProductDetailResponse> cachedResponse = cache.get(name);
        if (cachedResponse.isPresent()) {
            return cachedResponse.get();
        }
        ProductDetailResponse response = productDetailWebService.get(name);
        cache.put(name, response);
        return response;
    }

    public List<ProductDetailResponse> batchGet(List<String> names) {
        Map<String, ProductDetailResponse> cachedResponses = cache.batchGet(names);
        List<ProductDetailResponse> responses = Lists.newArrayList();
        names.forEach(name -> {
            if (cachedResponses.containsKey(name)) {
                responses.add(cachedResponses.get(name));
            } else {
                ProductDetailResponse response = productDetailWebService.get(name);
                cache.put(name, response);
                responses.add(response);
            }
        });
        return responses;
    }
}
