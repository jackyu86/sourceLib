package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumGenderTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumGenderTypeResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class EnumGenderTypeWebServiceClient implements EnumGenderTypeWebService {
    private final Cache<AllEnumGenderTypeResponse> cache;
    private final EnumGenderTypeWebService enumGenderTypeWebService;

    public EnumGenderTypeWebServiceClient(Cache<AllEnumGenderTypeResponse> genderTypeCache, EnumGenderTypeWebService enumGenderTypeWebService) {
        this.cache = genderTypeCache;
        this.enumGenderTypeWebService = enumGenderTypeWebService;
    }

    @Override
    public AllEnumGenderTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumGenderTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllEnumGenderTypeResponse response = enumGenderTypeWebService.findAll();
        cache.put(prefix, response);
        return response;
    }
}
