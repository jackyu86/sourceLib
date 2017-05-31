package com.caej.product.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceDividendType;
import com.caej.insurance.api.insurance.InsurancePolicyDeliverType;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.api.product.ProductFormFieldDisplayView;
import com.caej.product.api.product.ProductStatusType;
import com.caej.product.api.product.ProductType;
import com.caej.product.api.product.ProductVisibleType;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author chi
 */
@Entity(name = "product")
public class Product {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "version")
    public Integer version;
    @Field(name = "latest_version")
    public Boolean latestVersion;
    @Field(name = "serial_id")
    public ObjectId serialId; //产品系列
    @Field(name = "serial_display_name")
    public String serialDisplayName;
    @Field(name = "display_name")
    public String displayName;
    @Field(name = "keywords")
    public List<String> keywords;
    @Field(name = "highlight_content")
    public String highlightContent;
    @Field(name = "tips_content")
    public String tipsContent; //温馨提示
    @Field(name = "feature_content")
    public String featureContent;
    @Field(name = "tags")
    public List<String> tags;
    @Field(name = "public_visible")
    public ProductVisibleType publicVisible;

    @Field(name = "display_order")
    public Integer displayOrder;

    @Field(name = "insurance_vendor_id")
    public ObjectId insuranceVendorId; //保险厂商
    @Field(name = "insurance_claim_ids")
    public List<ObjectId> insuranceClaimIds; //理赔指引
    @Field(name = "insurance_category_ids")
    public List<ObjectId> insuranceCategoryIds;
    @Field(name = "insurance_declaration_ids")
    public List<ObjectId> insuranceDeclarationIds;

    @Field(name = "cases")
    public List<String> cases;
    @Field(name = "questions")
    public List<ProductQuestion> questions;

    @Field(name = "plp")
    public List<ProductFormFieldDisplayView> plp;

    @Field(name = "pdp")
    public List<ProductFormFieldDisplayView> pdp;

    @Field(name = "form_groups")
    public List<ProductFormGroup> formGroups;
    @Field(name = "insurances")
    public List<ProductInsurance> insurances;

    @Field(name = "period")
    public ProductPeriod period;
    @Field(name = "payment")
    public ProductPayment payment;

    @Field(name = "insurance_job_tree_id")
    public ObjectId insuranceJobTreeId;
    @Field(name = "job_restricted")
    public Boolean jobRestricted;
    @Field(name = "job_levels")
    public List<Integer> jobLevels;
    @Field(name = "job_ids")
    public List<ObjectId> jobIds;
    @Field(name = "max_job_level")
    public Integer maxJobLevel;
    @Field(name = "min_job_level")
    public Integer minJobLevel;

    @Field(name = "max_units")
    public Integer maxUnits;
    @Field(name = "min_units")
    public Integer minUnits;

    @Field(name = "policy_holder_min_age")
    public InsurancePeriod policyHolderMinAge;

    @Field(name = "policy_holder_max_age")
    public InsurancePeriod policyHolderMaxAge;

    @Field(name = "insured_min_age")
    public InsurancePeriod insuredMinAge;

    @Field(name = "insured_max_age")
    public InsurancePeriod insuredMaxAge;

    @Field(name = "max_amount")
    public Double maxAmount;

    @Field(name = "discount")
    public Double discount;

    @Field(name = "insurance_city_ids")
    public List<ObjectId> insuranceCityIds;
    @Field(name = "insurance_area_ids")
    public List<ObjectId> insuranceAreaIds;

    @Field(name = "policy_deliver_types")
    public List<InsurancePolicyDeliverType> insurancePolicyDeliverTypes;
    @Field(name = "dividend_types")
    public List<InsuranceDividendType> insuranceDividendTypes;

    @Field(name = "status")
    public ProductStatusType status;
    @Field(name = "type")
    public ProductType type;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "plan_code")
    public String planCode;
    @Field(name = "insurance_clause_ids")
    public List<ObjectId> insuranceClauseIds;
    @Field(name = "price_table_url")
    public String priceTableUrl;
    @Field(name = "customer_notification_url")
    public String customerNotificationUrl;
    @Field(name = "agree_urls")
    public List<ProductAgreeUrl> agreeUrls;
    @Field(name = "special_function")
    public String specialFunction;
    @Field(name = "special_rule")
    public String specialRule;
}
