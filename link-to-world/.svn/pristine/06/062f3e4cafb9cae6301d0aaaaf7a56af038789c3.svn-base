package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumDeliverTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumDeliverTypeResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class EnumDeliverTypeWebServiceClient implements EnumDeliverTypeWebService {
    private final Cache<AllEnumDeliverTypeResponse> cache;
    private final EnumDeliverTypeWebService enumDeliverTypeWebService;

    public EnumDeliverTypeWebServiceClient(Cache<AllEnumDeliverTypeResponse> deliverTypeCache, EnumDeliverTypeWebService enumDeliverTypeWebService) {
        this.cache = deliverTypeCache;
        this.enumDeliverTypeWebService = enumDeliverTypeWebService;
    }

    @Override
    public AllEnumDeliverTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumDeliverTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllEnumDeliverTypeResponse all = enumDeliverTypeWebService.findAll();
        cache.put(prefix, all);
        return all;
    }
}
