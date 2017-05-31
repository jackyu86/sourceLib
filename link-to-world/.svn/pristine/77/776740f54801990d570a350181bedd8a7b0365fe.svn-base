package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumLegalBeneficiaryTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumLegalBeneficiaryTypeResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class EnumLegalBeneficiaryTypeWebServiceClient implements EnumLegalBeneficiaryTypeWebService {
    private final Cache<AllEnumLegalBeneficiaryTypeResponse> cache;
    private final EnumLegalBeneficiaryTypeWebService enumLegalBeneficiaryTypeWebService;

    public EnumLegalBeneficiaryTypeWebServiceClient(Cache<AllEnumLegalBeneficiaryTypeResponse> legalBeneficiaryTypeCache, EnumLegalBeneficiaryTypeWebService enumLegalBeneficiaryTypeWebService) {
        this.cache = legalBeneficiaryTypeCache;
        this.enumLegalBeneficiaryTypeWebService = enumLegalBeneficiaryTypeWebService;
    }

    @Override
    public AllEnumLegalBeneficiaryTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumLegalBeneficiaryTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllEnumLegalBeneficiaryTypeResponse all = enumLegalBeneficiaryTypeWebService.findAll();
        cache.put(prefix, all);
        return all;
    }
}
