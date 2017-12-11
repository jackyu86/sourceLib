package com.caej.product.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceAmountView;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityRefView;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;
import com.caej.insurance.api.insurance.InsuranceResponse;
import com.caej.insurance.domain.InsuranceAmount;
import com.caej.insurance.domain.InsuranceLiabilityRef;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductInsurance;
import com.caej.product.service.client.InsuranceLiabilityWebServiceClient;
import com.google.common.collect.Lists;

import io.sited.StandardException;

public class ProductLiabilityHelper {
    private final Product product;
    private final List<InsuranceResponse> insurances;
    private final List<InsuranceLiabilityGroupResponse> groups;
    private final InsuranceLiabilityWebServiceClient insuranceLiabilityWebService;

    public ProductLiabilityHelper(Product product, List<InsuranceResponse> insurances, List<InsuranceLiabilityGroupResponse> groups, InsuranceLiabilityWebServiceClient insuranceLiabilityWebService) {
        this.product = product;
        this.insurances = insurances;
        this.groups = groups;
        this.insuranceLiabilityWebService = insuranceLiabilityWebService;
    }

    public List<ProductLiability> top(int count) {
        List<ProductLiability> list = Lists.newArrayList();
        List<String> ids = Lists.newArrayList();
        for (ProductInsurance insurance : product.insurances) {
            insurance.liabilities.forEach(liability -> {
                if (!ids.contains(liability.insuranceLiabilityId.toHexString())) {
                    ids.add(liability.insuranceLiabilityId.toHexString());
                    list.add(productLiability(liability.insuranceLiabilityId));
                }
            });
        }
        list.sort((l1, l2) -> l1.priority.compareTo(l2.priority));
        return list.size() < count ? list : list.subList(0, count);
    }

    public ProductLiability productLiability(ObjectId insuranceLiabilityId) {
        for (ProductInsurance insurance : product.insurances) {
            for (InsuranceLiabilityRef liability : insurance.liabilities) {
                if (liability.insuranceLiabilityId.equals(insuranceLiabilityId)) {
                    InsuranceLiabilityRefView insuranceLiability = insuranceLiability(insurance.insuranceId, insuranceLiabilityId);

                    ProductLiability productLiability = new ProductLiability();
                    productLiability.name = insuranceLiabilityId.toHexString();

                    InsuranceLiabilityResponse insuranceLiabilityResponse = insuranceLiabilityWebService.get(insuranceLiability.insuranceLiabilityId.toHexString());
                    productLiability.displayName = insuranceLiabilityResponse.name;
                    productLiability.description = liability.description == null ? insuranceLiabilityResponse.description : liability.description;
                    productLiability.amount = liability.amount == null ? insuranceLiability.amount : amountView(liability.amount);
                    productLiability.priority = priority(insuranceLiabilityId);
                    return productLiability;
                }
            }
        }
        throw new StandardException("missing product liability, id={}", insuranceLiabilityId);
    }

    public InsuranceLiabilityRefView insuranceLiability(ObjectId insuranceId, ObjectId insuranceLiabilityId) {
        for (InsuranceResponse insurance : insurances) {
            if (insurance.id.equals(insuranceId)) {
                for (InsuranceLiabilityRefView liability : insurance.liabilities) {
                    if (liability.insuranceLiabilityId.equals(insuranceLiabilityId)) {
                        return liability;
                    }
                }
            }
        }
        throw new StandardException("missing insurance liability, id={}", insuranceLiabilityId);
    }

    private InsuranceAmountView amountView(InsuranceAmount insuranceAmount) {
        InsuranceAmountView insuranceAmountView = new InsuranceAmountView();
        insuranceAmountView.type = insuranceAmount.type;
        insuranceAmountView.inputMax = insuranceAmount.inputMax;
        insuranceAmountView.inputMin = insuranceAmount.inputMin;
        insuranceAmountView.inputIncrementUnit = insuranceAmount.inputIncrementUnit;
        insuranceAmountView.selections = insuranceAmount.selections;
        insuranceAmountView.minUnits = insuranceAmount.minUnits;
        insuranceAmountView.maxUnits = insuranceAmount.maxUnits;
        insuranceAmountView.formulaName = insuranceAmount.formulaName;
        insuranceAmountView.fixedValue = insuranceAmount.fixedValue;
        return insuranceAmountView;
    }

    private Integer priority(ObjectId insuranceLiabilityId) {
        InsuranceLiabilityResponse insuranceLiabilityResponse = insuranceLiabilityWebService.get(insuranceLiabilityId.toHexString());
        for (InsuranceLiabilityGroupResponse liabilityGroup : groups) {
            if (liabilityGroup.id.toHexString().equals(insuranceLiabilityResponse.groupId.toHexString())) {
                return liabilityGroup.priority * 10000 + insuranceLiabilityResponse.priority;
            }
        }
        throw new StandardException("missing liability group, id={}", insuranceLiabilityResponse.groupId);
    }

    public static class ProductLiability {
        public String name;
        public String displayName;
        public String description;
        public InsuranceAmountView amount;
        public Integer priority;
    }
}