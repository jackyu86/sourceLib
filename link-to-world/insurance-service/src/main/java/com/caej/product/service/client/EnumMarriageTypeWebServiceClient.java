package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumMarriageTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumMarriageTypeResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class EnumMarriageTypeWebServiceClient implements EnumMarriageTypeWebService {
    private final Cache<AllEnumMarriageTypeResponse> cache;
    private final EnumMarriageTypeWebService enumMarriageTypeWebService;

    public EnumMarriageTypeWebServiceClient(Cache<AllEnumMarriageTypeResponse> marriageTypeCache, EnumMarriageTypeWebService enumMarriageTypeWebService) {
        this.cache = marriageTypeCache;
        this.enumMarriageTypeWebService = enumMarriageTypeWebService;
    }

    @Override
    public AllEnumMarriageTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumMarriageTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllEnumMarriageTypeResponse all = enumMarriageTypeWebService.findAll();
        cache.put(prefix, all);
        return all;
    }
}
