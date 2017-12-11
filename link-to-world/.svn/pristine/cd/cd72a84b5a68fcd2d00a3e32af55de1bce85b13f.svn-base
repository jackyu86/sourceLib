package com.caej.product.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.insurance.BatchGetInsuranceRequest;
import com.caej.product.domain.Product;
import com.caej.product.service.client.InsuranceLiabilityGroupWebServiceClient;
import com.caej.product.service.client.InsuranceLiabilityWebServiceClient;
import com.caej.product.service.client.InsuranceWebServiceClient;
import com.google.common.collect.Lists;

/**
 * @author chi
 */
public class ProductInsuranceLiabilityService {
    @Inject
    InsuranceWebServiceClient insuranceWebService;
    @Inject
    InsuranceLiabilityGroupWebServiceClient insuranceLiabilityGroupWebService;
    @Inject
    InsuranceLiabilityWebServiceClient insuranceLiabilityWebService;

    public ProductLiabilityHelper helper(Product product) {
        BatchGetInsuranceRequest request = new BatchGetInsuranceRequest();
        Set<String> ids = product.insurances.stream().map(productInsurance -> productInsurance.insuranceId.toHexString()).collect(Collectors.toSet());
        request.ids = Lists.newArrayList(ids);
        return new ProductLiabilityHelper(product, insuranceWebService.batchGet(request), insuranceLiabilityGroupWebService.all().list, insuranceLiabilityWebService);
    }
}
