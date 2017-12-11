package com.caej.product.api.product;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceDividendType;
import com.caej.insurance.api.insurance.InsurancePolicyDeliverType;

/**
 * @author chi
 */
public class ProductResponse {
    public String id;
    public String name;
    public ObjectId serialId; //产品系列
    public Integer version;

    public ObjectId insuranceVendorId; //保险厂商
    public List<ObjectId> insuranceClaimIds; //理赔指引
    public List<ObjectId> insuranceCategoryIds;
    public List<ObjectId> insuranceDeclarationIds;

    public String displayName;
    public String highlightContent;
    public String tipsContent; //温馨提示
    public String featureContent; //
    public List<String> tags;
    public List<String> keywords;
    public ProductVisibleType publicVisible;

    public Integer displayOrder;

    public List<String> cases;
    public List<ProductQuestionView> questions;

    public List<ProductFormFieldDisplayView> plp; //PLP显示因子
    public List<ProductFormFieldDisplayView> pdp; //PDP显示因子

    public List<ProductFormGroupView> formGroups; //投保�?
    public List<ProductInsuranceView> insurances; //险种

    public ProductPeriodView period;
    public ProductPaymentView payment;

    public Double discount;

    public ObjectId insuranceJobTreeId;
    public Boolean jobRestricted;
    public List<Integer> jobLevels;
    public List<ObjectId> jobIds;
    public Integer maxJobLevel;
    public Integer minJobLevel;

    public Integer maxUnits;
    public Integer minUnits;

    public Double maxAmount;

    public List<ObjectId> insuranceCityIds;
    public List<ObjectId> insuranceAreaIds;
    public List<ObjectId> insuranceClauseIds;

    public List<InsurancePolicyDeliverType> insurancePolicyDeliverTypes;

    public List<InsuranceDividendType> insuranceDividendTypes;
    public ProductStatusType status;
    public ProductType type;

    public String planCode;
    public LocalDateTime createdTime;
    public LocalDateTime updatedTime;
    public String createdBy;
    public String updatedBy;

    public String priceTableUrl;
    public String customerNotificationUrl;
    public List<ProductAgreeUrlView> agreeUrls;
    public String specialFunction;
    public String specialRule;
}
