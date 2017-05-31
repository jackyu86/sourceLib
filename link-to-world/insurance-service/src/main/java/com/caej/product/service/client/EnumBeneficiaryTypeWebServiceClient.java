package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumBeneficiaryTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumBeneficiaryTypeResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class EnumBeneficiaryTypeWebServiceClient implements EnumBeneficiaryTypeWebService {
    private final Cache<AllEnumBeneficiaryTypeResponse> cache;
    private final EnumBeneficiaryTypeWebService enumBeneficiaryTypeWebService;

    public EnumBeneficiaryTypeWebServiceClient(Cache<AllEnumBeneficiaryTypeResponse> beneficiaryTypeCache, EnumBeneficiaryTypeWebService enumBeneficiaryTypeWebService) {
        this.cache = beneficiaryTypeCache;
        this.enumBeneficiaryTypeWebService = enumBeneficiaryTypeWebService;
    }

    @Override
    public AllEnumBeneficiaryTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumBeneficiaryTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllEnumBeneficiaryTypeResponse all = enumBeneficiaryTypeWebService.findAll();
        cache.put(prefix, all);
        return all;
    }
}
