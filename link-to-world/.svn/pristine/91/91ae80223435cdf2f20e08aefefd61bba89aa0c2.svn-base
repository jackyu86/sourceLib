package com.caej.product.api.product;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceDividendType;
import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.insurance.api.insurance.InsurancePolicyDeliverType;
import com.caej.product.api.price.ProductPriceResponse;

/**
 * @author chi
 */
public class ProductRequest {
    public ObjectId id;
    public String name;
    public ObjectId serialId; //产品系列
    public String serialDisplayName;
    public Integer version;

    public ObjectId insuranceVendorId; //保险厂商
    public List<ObjectId> insuranceClaimIds; //理赔指引
    public List<ObjectId> insuranceCategoryIds;
    public List<ObjectId> insuranceDeclarationIds;

    public String displayName;
    public List<String> keywords;
    public String highlightContent;
    public String tipsContent; //温馨提示
    public String featureContent; //
    public List<String> tags;
    public ProductVisibleType publicVisible;

    public Integer displayOrder;

    public List<String> cases;
    public List<ProductQuestionView> questions;

    public List<ProductFormFieldDisplayView> plp; //PLP显示因子
    public List<ProductFormFieldDisplayView> pdp; //PDP显示因子
    public List<ProductFormGroupView> formGroups; //投保�?

    public List<ProductInsuranceView> insurances;

    public ProductPeriodView period;
    public ProductPaymentRequest payment;

    public ProductPriceResponse price;
    public Double discount;

    public ObjectId insuranceJobTreeId;
    public Boolean jobRestricted;
    public List<Integer> jobLevels;
    public List<ObjectId> jobIds;
    public Integer maxJobLevel;
    public Integer minJobLevel;

    public Integer minUnits;
    public Integer maxUnits;

    public InsurancePeriodView policyHolderMinAge;
    public InsurancePeriodView policyHolderMaxAge;
    public InsurancePeriodView insuredMinAge;
    public InsurancePeriodView insuredMaxAge;

    public Double maxAmount;

    public List<ObjectId> insuranceCityIds;
    public List<ObjectId> insuranceAreaIds;

    public List<InsurancePolicyDeliverType> insurancePolicyDeliverTypes;
    public List<InsuranceDividendType> insuranceDividendTypes;

    public ProductStatusType status;
    public ProductType type;
    public String planCode;

    public List<ObjectId> insuranceClauseIds;
    public String priceTableUrl;
    public String customerNotificationUrl;
    public List<ProductAgreeUrlView> agreeUrls;
    public String specialFunction;
    public String specialRule;
    public String requestBy;

    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}