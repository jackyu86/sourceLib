package com.caej.product.service.client;

import java.util.List;
import java.util.Optional;

import com.caej.insurance.api.InsuranceLiabilityWebService;
import com.caej.insurance.api.insurance.BatchGetInsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;

import io.sited.cache.Cache;
import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceLiabilityWebServiceClient implements InsuranceLiabilityWebService {
    private final Cache<InsuranceLiabilityResponse> cache;
    private final InsuranceLiabilityWebService insuranceLiabilityWebService;

    public InsuranceLiabilityWebServiceClient(Cache<InsuranceLiabilityResponse> liabilityCache, InsuranceLiabilityWebService insuranceLiabilityWebService) {
        this.cache = liabilityCache;
        this.insuranceLiabilityWebService = insuranceLiabilityWebService;
    }

    @Override
    public InsuranceLiabilityResponse get(@PathParam("id") String id) {
        final String prefix = "id$";
        Optional<InsuranceLiabilityResponse> optional = cache.get(prefix + id);
        if (optional.isPresent()) return optional.get();
        InsuranceLiabilityResponse insuranceLiabilityResponse = insuranceLiabilityWebService.get(id);
        cache.put(prefix + id, insuranceLiabilityResponse);
        return insuranceLiabilityResponse;
    }

    @Override
    public List<InsuranceLiabilityResponse> batchGet(BatchGetInsuranceLiabilityRequest request) {
        return insuranceLiabilityWebService.batchGet(request);
    }

    @Override
    public InsuranceLiabilityResponse create(InsuranceLiabilityRequest request) {
        return insuranceLiabilityWebService.create(request);
    }

    @Override
    public FindView<InsuranceLiabilityResponse> find(InsuranceLiabilityQuery query) {
        return insuranceLiabilityWebService.find(query);
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceLiabilityRequest request) {
        insuranceLiabilityWebService.update(id, request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceLiabilityWebService.delete(id);
    }

    @Override
    public Long groupCount(@PathParam("groupId") String groupId) {
        return insuranceLiabilityWebService.groupCount(groupId);
    }
}
