package com.caej.client;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.google.common.collect.Lists;

import io.sited.cache.Cache;

/**
 * @author chi
 */
public class InsuranceVendorWebServiceClient {
    @Inject
    Cache<InsuranceVendorResponse> cache;
    @Inject
    InsuranceVendorWebService insuranceVendorWebService;

    public InsuranceVendorResponse get(String id) {
        Optional<InsuranceVendorResponse> cachedResponse = cache.get(id);
        if (cachedResponse.isPresent()) {
            return cachedResponse.get();
        }
        InsuranceVendorResponse response = insuranceVendorWebService.get(id);
        cache.put(id, response);
        return null;
    }

    public List<InsuranceVendorResponse> batchGet(List<String> ids) {
        Map<String, InsuranceVendorResponse> cachedResponses = cache.batchGet(ids);
        List<InsuranceVendorResponse> responses = Lists.newArrayList();
        ids.forEach(id -> {
            if (cachedResponses.containsKey(id)) {
                responses.add(cachedResponses.get(id));
            } else {
                InsuranceVendorResponse response = insuranceVendorWebService.get(id);
                cache.put(id, response);
                responses.add(response);
            }
        });
        return responses;
    }

}
