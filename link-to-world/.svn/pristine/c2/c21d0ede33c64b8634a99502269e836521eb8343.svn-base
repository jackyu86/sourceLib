package com.caej.product.service.client;

import java.util.Optional;

import com.caej.insurance.api.InsuranceLiabilityGroupWebService;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminRequest;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupAdminResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupQuery;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupResponseList;

import io.sited.cache.Cache;
import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceLiabilityGroupWebServiceClient implements InsuranceLiabilityGroupWebService {
    private final Cache<InsuranceLiabilityGroupResponseList> cache;
    private final InsuranceLiabilityGroupWebService insuranceLiabilityGroupWebService;

    public InsuranceLiabilityGroupWebServiceClient(Cache<InsuranceLiabilityGroupResponseList> liabilityGroupCache, InsuranceLiabilityGroupWebService insuranceLiabilityGroupWebService) {
        this.cache = liabilityGroupCache;
        this.insuranceLiabilityGroupWebService = insuranceLiabilityGroupWebService;
    }

    @Override
    public InsuranceLiabilityGroupResponseList all() {
        final String prefix = "all";
        Optional<InsuranceLiabilityGroupResponseList> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        InsuranceLiabilityGroupResponseList all = insuranceLiabilityGroupWebService.all();
        cache.put(prefix, all);
        return all;
    }

    @Override
    public InsuranceLiabilityGroupAdminResponse create(InsuranceLiabilityGroupAdminRequest request) {
        return insuranceLiabilityGroupWebService.create(request);
    }

    @Override
    public InsuranceLiabilityGroupAdminResponse get(@PathParam("id") String id) {
        return insuranceLiabilityGroupWebService.get(id);
    }

    @Override
    public FindView<InsuranceLiabilityGroupAdminResponse> find(InsuranceLiabilityGroupQuery query) {
        return insuranceLiabilityGroupWebService.find(query);
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceLiabilityGroupAdminRequest request) {
        insuranceLiabilityGroupWebService.update(id, request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceLiabilityGroupWebService.delete(id);
    }
}
