package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumCertiTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumCertiTypeResponse;

import io.sited.cache.Cache;
/**
 * @author miller
 */
public class EnumCertiTypeWebServiceClient implements EnumCertiTypeWebService {
    private final Cache<AllEnumCertiTypeResponse> cache;
    private final EnumCertiTypeWebService enumCertiTypeWebService;

    public EnumCertiTypeWebServiceClient(Cache<AllEnumCertiTypeResponse> certiTypeCache, EnumCertiTypeWebService enumCertiTypeWebService) {
        this.cache = certiTypeCache;
        this.enumCertiTypeWebService = enumCertiTypeWebService;
    }

    @Override
    public AllEnumCertiTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumCertiTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllEnumCertiTypeResponse response = enumCertiTypeWebService.findAll();
        cache.put(prefix, response);
        return response;
    }
}
