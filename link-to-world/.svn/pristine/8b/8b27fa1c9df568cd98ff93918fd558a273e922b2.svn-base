package com.caej.admin.vendor;

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
public class InsuranceVendorWebServiceAdminClient implements InsuranceVendorWebService {
    private final Cache<InsuranceVendorResponse> cache;
    private final InsuranceVendorWebService insuranceVendorWebService;

    public InsuranceVendorWebServiceAdminClient(Cache<InsuranceVendorResponse> cache, InsuranceVendorWebService insuranceVendorWebService) {
        this.cache = cache;
        this.insuranceVendorWebService = insuranceVendorWebService;
    }

    @Override
    public InsuranceVendorResponse get(@PathParam("id") String id) {
        return insuranceVendorWebService.get(id);
    }

    @Override
    public FindView<InsuranceVendorResponse> find(InsuranceVendorQuery query) {
        return insuranceVendorWebService.find(query);
    }

    public void delete(String id) {
        insuranceVendorWebService.delete(id);
        invalidateCache(id);
    }

    private void invalidateCache(String id) {
        final String prefix = "$id";
        Optional<InsuranceVendorResponse> optional = cache.get(prefix + id);
        if (!optional.isPresent()) return;
        cache.invalidate(prefix + id);
    }

    @Override
    public void update(@PathParam("id") String id, UpdateInsuranceVendorRequest request) {
        insuranceVendorWebService.update(id, request);
        invalidateCache(id);
    }

    @Override
    public InsuranceVendorResponse create(CreateInsuranceVendorRequest request) {
        return insuranceVendorWebService.create(request);
    }

    @Override
    public void batchDelete(BatchDeleteInsuranceVendorRequest request) {
        insuranceVendorWebService.batchDelete(request);
        request.ids.forEach(id -> {
            invalidateCache(id.toHexString());
        });
    }
}
