package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.vendor.BatchDeleteInsuranceVendorRequest;
import com.caej.insurance.api.vendor.CreateInsuranceVendorRequest;
import com.caej.insurance.api.vendor.InsuranceVendorQuery;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.insurance.api.vendor.UpdateInsuranceVendorRequest;

import io.sited.cache.Cache;
import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceVendorWebServiceClient implements InsuranceVendorWebService {
    private final Cache<InsuranceVendorResponse> cache;
    private final InsuranceVendorWebService insuranceVendorWebService;

    public InsuranceVendorWebServiceClient(Cache<InsuranceVendorResponse> vendorCache, InsuranceVendorWebService insuranceVendorWebService) {
        this.cache = vendorCache;
        this.insuranceVendorWebService = insuranceVendorWebService;
    }

    @Override
    public InsuranceVendorResponse get(@PathParam("id") String id) {
        final String prefix = "id$";
        Optional<InsuranceVendorResponse> optional = cache.get(prefix + id);
        if (optional.isPresent()) return optional.get();
        InsuranceVendorResponse insuranceVendorResponse = insuranceVendorWebService.get(id);
        cache.put(prefix + id, insuranceVendorResponse);
        return insuranceVendorResponse;
    }

    @Override
    public FindView<InsuranceVendorResponse> find(InsuranceVendorQuery query) {
        return insuranceVendorWebService.find(query);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceVendorWebService.delete(id);
    }

    @Override
    public void update(@PathParam("id") String id, UpdateInsuranceVendorRequest request) {
        insuranceVendorWebService.update(id, request);
    }

    @Override
    public InsuranceVendorResponse create(CreateInsuranceVendorRequest request) {
        return insuranceVendorWebService.create(request);
    }

    @Override
    public void batchDelete(BatchDeleteInsuranceVendorRequest request) {
        insuranceVendorWebService.batchDelete(request);
    }
}
