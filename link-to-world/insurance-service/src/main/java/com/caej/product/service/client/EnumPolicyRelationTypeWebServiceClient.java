package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumPolicyRelationTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumPolicyRelationTypeResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class EnumPolicyRelationTypeWebServiceClient implements EnumPolicyRelationTypeWebService {
    private final Cache<AllEnumPolicyRelationTypeResponse> cache;
    private final EnumPolicyRelationTypeWebService enumPolicyRelationTypeWebService;

    public EnumPolicyRelationTypeWebServiceClient(Cache<AllEnumPolicyRelationTypeResponse> policyRelationTypeCache, EnumPolicyRelationTypeWebService enumPolicyRelationTypeWebService) {
        this.cache = policyRelationTypeCache;
        this.enumPolicyRelationTypeWebService = enumPolicyRelationTypeWebService;
    }

    @Override
    public AllEnumPolicyRelationTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumPolicyRelationTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) {
            return optional.get();
        }
        AllEnumPolicyRelationTypeResponse response = enumPolicyRelationTypeWebService.findAll();
        cache.put(prefix, response);
        return response;
    }
}
