package com.caej.product.service.client;

import java.util.List;
import java.util.Optional;

import com.caej.insurance.api.InsuranceWebService;
import com.caej.insurance.api.insurance.BatchGetInsuranceRequest;
import com.caej.insurance.api.insurance.InsuranceAdminRequest;
import com.caej.insurance.api.insurance.InsuranceQuery;
import com.caej.insurance.api.insurance.InsuranceResponse;

import io.sited.cache.Cache;
import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceWebServiceClient implements InsuranceWebService {
    private final Cache<InsuranceResponse> cache;
    private final InsuranceWebService insuranceWebService;

    public InsuranceWebServiceClient(Cache<InsuranceResponse> cache, InsuranceWebService insuranceWebService) {
        this.cache = cache;
        this.insuranceWebService = insuranceWebService;
    }

    @Override
    public InsuranceResponse get(@PathParam("id") String id) {
        final String prefix = "id$";
        Optional<InsuranceResponse> optional = cache.get(prefix + id);
        if (optional.isPresent()) return optional.get();
        InsuranceResponse insuranceResponse = insuranceWebService.get(id);
        cache.put(prefix + id, insuranceResponse);
        return insuranceResponse;
    }

    @Override
    public List<InsuranceResponse> batchGet(BatchGetInsuranceRequest request) {
        return insuranceWebService.batchGet(request);
    }

    @Override
    public FindView<InsuranceResponse> find(InsuranceQuery insuranceQuery) {
        return insuranceWebService.find(insuranceQuery);
    }

    @Override
    public InsuranceResponse create(InsuranceAdminRequest request) {
        return insuranceWebService.create(request);
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceAdminRequest request) {
        insuranceWebService.update(id, request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceWebService.delete(id);
    }
}
