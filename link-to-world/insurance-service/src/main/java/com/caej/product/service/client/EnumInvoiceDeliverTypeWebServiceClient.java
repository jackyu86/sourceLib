package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.EnumInvoiceDeliverTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumInvoiceDeliverTypeResponse;

import io.sited.cache.Cache;

/**
 * @author miller
 */
public class EnumInvoiceDeliverTypeWebServiceClient implements EnumInvoiceDeliverTypeWebService {
    private final Cache<AllEnumInvoiceDeliverTypeResponse> cache;
    private final EnumInvoiceDeliverTypeWebService enumInvoiceDeliverTypeWebService;

    public EnumInvoiceDeliverTypeWebServiceClient(Cache<AllEnumInvoiceDeliverTypeResponse> cache, EnumInvoiceDeliverTypeWebService enumInvoiceDeliverTypeWebService) {
        this.cache = cache;
        this.enumInvoiceDeliverTypeWebService = enumInvoiceDeliverTypeWebService;
    }

    @Override
    public AllEnumInvoiceDeliverTypeResponse findAll() {
        final String prefix = "all";
        Optional<AllEnumInvoiceDeliverTypeResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllEnumInvoiceDeliverTypeResponse all = enumInvoiceDeliverTypeWebService.findAll();
        cache.put(prefix, all);
        return all;
    }

    @Override
    public Double price(String value) {
        return enumInvoiceDeliverTypeWebService.price(value);
    }
}
