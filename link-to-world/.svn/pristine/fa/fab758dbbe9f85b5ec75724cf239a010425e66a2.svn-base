package com.caej.site.product.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.client.InsuranceLiabilityWebServiceClient;
import com.caej.client.ProductDetailWebServiceClient;
import com.caej.client.ProductWebServiceClient;
import com.caej.insurance.api.InsuranceClaimWebService;
import com.caej.insurance.api.InsuranceClauseWebService;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.claim.BatchGetInsuranceClaimRequest;
import com.caej.insurance.api.claim.InsuranceClaimResponse;
import com.caej.insurance.api.clause.BatchGetInsuranceClauseRequest;
import com.caej.insurance.api.clause.InsuranceClauseResponse;
import com.caej.insurance.api.insurance.InsuranceAmountType;
import com.caej.insurance.api.insurance.InsuranceAmountView;
import com.caej.insurance.api.insurance.InsuranceLiabilityRefView;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityType;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.product.api.product.ProductDetailResponse;
import com.caej.product.api.product.ProductInsuranceView;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.ProductVisibleType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.product.DealerProductView;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.user.web.User;

/**
 * @author chi
 */
public class ProductControllerV2 {
    @Inject
    ProductWebServiceClient productWebServiceClient;
    @Inject
    InsuranceClauseWebService insuranceClauseWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    ProductDetailWebServiceClient client;
    @Inject
    InsuranceVendorWebService insuranceVendorWebService;
    @Inject
    InsuranceLiabilityWebServiceClient insuranceLiabilityWebServiceClient;
    @Inject
    InsuranceClaimWebService insuranceClaimWebService;

    @Path("/product/:name")
    @GET
    public Response product(Request request) {
        String productName = request.pathParam("name");
        HashMap<String, Object> context = Maps.newHashMap();
        ProductDetailResponse product = client.get(productName);
        if (!isVisible(product.product.publicVisible, request, product.product.name)) {
            throw new NotFoundException(request.path());
        }

        context.put("product", product.product);
        context.put("vendor", insuranceVendorWebService.get(product.product.insuranceVendorId.toHexString()));
        context.put("form", product.pdpForm);

        List<InsuranceLiabilityWebModel> riskProtections = Lists.newArrayList();
        List<String> riskProtectionIds = Lists.newArrayList();
        List<InsuranceLiabilityWebModel> liveBenefits = Lists.newArrayList();
        List<String> liveBenefitIds = Lists.newArrayList();
        for (ProductInsuranceView insurance : product.product.insurances) {
            for (InsuranceLiabilityRefView liabilityRefView : insurance.liabilities) {
                InsuranceLiabilityResponse liability = insuranceLiabilityWebServiceClient.get(liabilityRefView.insuranceLiabilityId.toHexString());
                if (riskProtectionIds.contains(liability.id.toHexString()) || liveBenefitIds.contains(liability.id.toHexString()))
                    continue;
                if (InsuranceLiabilityType.RISK_PROTECTION.equals(liability.type)) {
                    riskProtectionIds.add(liability.id.toHexString());
                    riskProtections.add(insuranceLiabilityWebModel(liability, liabilityRefView));
                } else if (InsuranceLiabilityType.LIVE_BENEFIT.equals(liability.type)) {
                    liveBenefitIds.add(liability.id.toHexString());
                    liveBenefits.add(insuranceLiabilityWebModel(liability, liabilityRefView));
                }
            }
        }
        BatchGetInsuranceClaimRequest claimRequest = new BatchGetInsuranceClaimRequest();
        claimRequest.ids = product.product.insuranceClaimIds;
        List<InsuranceClaimResponse> insuranceClaimList = insuranceClaimWebService.batchGet(claimRequest);
        context.put("riskProtections", riskProtections);
        context.put("liveBenefits", liveBenefits);
        context.put("claims", insuranceClaimList);
        return Response.template("/product/pdp.html", context);
    }

    private boolean isVisible(ProductVisibleType publicVisible, Request request, String productName) {
        User user = request.require(User.class, null);
        if (user == null) {
            return !ProductVisibleType.ONLY_DEALER.name().equals(publicVisible.name());
        }
        Optional<DealerUserResponse> dealerUserResponse = dealerUserWebService.get(user.id);
        if (!dealerUserResponse.isPresent()) {
            return !ProductVisibleType.ONLY_DEALER.name().equals(publicVisible.name());
        }
        if (ProductVisibleType.ONLY_CUSTOMER.name().equals(publicVisible.name())) return false;
        Optional<DealerProductView> dealerProductOptional = dealerProductWebService.getByDealerIdAndProductName(dealerUserResponse.get().dealerId, productName);
        return dealerProductOptional.isPresent();
    }

    private InsuranceLiabilityWebModel insuranceLiabilityWebModel(InsuranceLiabilityResponse liability, InsuranceLiabilityRefView liabilityRefView) {
        InsuranceLiabilityWebModel model = new InsuranceLiabilityWebModel();
        model.id = liability.id.toHexString();
        model.name = liability.name;
        model.description = liabilityRefView.description == null ? liability.description : liabilityRefView.description;
        model.defaultValue = defaultValue(liabilityRefView.amount);
        return model;
    }

    private String defaultValue(InsuranceAmountView amount) {
        if (InsuranceAmountType.FIXED.equals(amount.type)) {
            return amount.fixedValue;
        }
        if (InsuranceAmountType.USER_SELECTION.equals(amount.type)) {
            return amount.selections.get(0) + "";
        }
        return "";
    }


    @Path("/product/:name/clause")
    @GET
    public Response clause(Request request) {
        String name = request.pathParam("name");
        ProductResponse productResponse = productWebServiceClient.getByName(name);
        InsuranceVendorResponse insuranceVendorResponse = insuranceVendorWebService.get(productResponse.insuranceVendorId.toHexString());
        BatchGetInsuranceClauseRequest batchGetInsuranceClauseRequest = new BatchGetInsuranceClauseRequest();
        batchGetInsuranceClauseRequest.ids = productResponse.insuranceClauseIds;
        List<InsuranceClauseResponse> clauseResponseList = insuranceClauseWebService.batch(batchGetInsuranceClauseRequest);
        Map<String, Object> context = Maps.newHashMap();
        context.put("vendor", insuranceVendorResponse);
        context.put("product", productResponse);
        context.put("clauses", clauseResponseList);
        return Response.template("/product/clause.html", context);
    }
}
