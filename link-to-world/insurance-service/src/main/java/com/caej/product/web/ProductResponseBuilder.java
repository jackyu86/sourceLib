package com.caej.product.web;


import java.util.List;
import java.util.stream.Collectors;

import com.caej.insurance.api.insurance.InsuranceAmountView;
import com.caej.insurance.api.insurance.InsuranceLiabilityRefView;
import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.insurance.domain.InsuranceAmount;
import com.caej.insurance.domain.InsuranceLiabilityRef;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.api.product.ProductAgreeUrlView;
import com.caej.product.api.product.ProductFormFieldView;
import com.caej.product.api.product.ProductFormGroupView;
import com.caej.product.api.product.ProductInsuranceView;
import com.caej.product.api.product.ProductPeriodView;
import com.caej.product.api.product.ProductQuestionView;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductAgreeUrl;
import com.caej.product.domain.ProductFormGroup;
import com.caej.product.domain.ProductInsurance;
import com.caej.product.domain.ProductPeriod;
import com.caej.product.domain.ProductQuestion;

/**
 * @author chi
 */
public class ProductResponseBuilder {
    private final Product product;

    public ProductResponseBuilder(Product product) {
        this.product = product;
    }

    public ProductResponse build() {
        ProductResponse response = new ProductResponse();
        response.id = product.id.toHexString();
        response.name = product.name;
        response.serialId = product.serialId;
        response.version = product.version;
        response.displayName = product.displayName;
        response.highlightContent = product.highlightContent;
        response.tipsContent = product.tipsContent;
        response.featureContent = product.featureContent;
        response.tags = product.tags;
        response.keywords = product.keywords;
        response.publicVisible = product.publicVisible;
        response.insuranceVendorId = product.insuranceVendorId;
        response.insuranceClaimIds = product.insuranceClaimIds;
        response.insuranceCategoryIds = product.insuranceCategoryIds;
        response.insuranceDeclarationIds = product.insuranceDeclarationIds;
        response.cases = product.cases;

        if (product.questions != null) {
            response.questions = questions(product.questions);
        }
        if (product.formGroups != null) {
            response.formGroups = formGroups(product.formGroups);
        }
        if (product.period != null) {
            response.period = productPeriodView(product.period);
        }
        if (product.insurances != null) {
            response.insurances = product.insurances.stream().map(this::insurance).collect(Collectors.toList());
        }

        response.discount = product.discount;

        response.insuranceJobTreeId = product.insuranceJobTreeId;
        response.jobRestricted = product.jobRestricted;
        response.jobLevels = product.jobLevels;
        response.jobIds = product.jobIds;
        response.maxJobLevel = product.maxJobLevel;
        response.minJobLevel = product.minJobLevel;
        response.insuranceCityIds = product.insuranceCityIds;
        response.insuranceAreaIds = product.insuranceAreaIds;
        response.insuranceClauseIds = product.insuranceClauseIds;

        response.minUnits = product.minUnits;
        response.maxUnits = product.maxUnits;

        response.maxAmount = product.maxAmount;

        response.insurancePolicyDeliverTypes = product.insurancePolicyDeliverTypes;
        response.insuranceDividendTypes = product.insuranceDividendTypes;
        response.status = product.status;
        response.type = product.type;
        response.planCode = product.planCode;
        response.createdTime = product.createdTime;
        response.createdBy = product.createdBy;
        response.updatedTime = product.updatedTime;
        response.updatedBy = product.updatedBy;
        response.priceTableUrl = product.priceTableUrl;
        response.customerNotificationUrl = product.customerNotificationUrl;
        response.specialFunction = product.specialFunction;
        response.specialRule = product.specialRule;
        if (product.agreeUrls != null) {
            response.agreeUrls = product.agreeUrls.stream().map(this::agreeUrl).collect(Collectors.toList());
        }

        return response;
    }

    private List<ProductQuestionView> questions(List<ProductQuestion> productQuestionList) {
        return productQuestionList.stream().map(productQuestion -> {
            ProductQuestionView productQuestionView = new ProductQuestionView();
            productQuestionView.question = productQuestion.question;
            productQuestionView.answer = productQuestion.answer;
            productQuestionView.displayOrder = productQuestion.displayOrder;
            return productQuestionView;
        }).collect(Collectors.toList());
    }

    private List<ProductFormGroupView> formGroups(List<ProductFormGroup> formGroupList) {
        return formGroupList.stream().map(productFormGroup -> {
            ProductFormGroupView productFormGroupView = new ProductFormGroupView();
            productFormGroupView.name = productFormGroup.name;
            productFormGroupView.optional = productFormGroup.optional;
            productFormGroupView.fields = productFormGroup.fields.stream().map(productFormField -> {
                ProductFormFieldView productFormFieldView = new ProductFormFieldView();
                productFormFieldView.name = productFormField.name;
                productFormFieldView.options = productFormField.options;
                productFormFieldView.multiple = productFormField.multiple;
                productFormFieldView.editable = productFormField.editable;
                productFormFieldView.defaultValue = productFormField.defaultValue;
                productFormFieldView.displayAs = productFormField.displayAs;
                return productFormFieldView;
            }).collect(Collectors.toList());
            return productFormGroupView;
        }).collect(Collectors.toList());
    }

    private ProductPeriodView productPeriodView(ProductPeriod productPeriod) {
        ProductPeriodView productDurationView = new ProductPeriodView();
        productDurationView.type = productPeriod.type;
        productDurationView.inputUnit = productPeriod.inputUnit;
        if (productPeriod.inputMin != null) {
            productDurationView.inputMin = insurancePeriodView(productPeriod.inputMin);
        }
        if (productPeriod.inputMax != null) {
            productDurationView.inputMax = insurancePeriodView(productPeriod.inputMax);
        }
        if (productPeriod.selections != null) {
            productDurationView.selections = productPeriod.selections.stream().map(this::insurancePeriodView).collect(Collectors.toList());
        }
        if (productPeriod.ages != null) {
            productDurationView.ages = productPeriod.ages.stream().map(this::insurancePeriodView).collect(Collectors.toList());
        }
        if (productPeriod.fixedValue != null) {
            productDurationView.fixedValue = insurancePeriodView(productPeriod.fixedValue);
        }
        if (productPeriod.startTimeType != null) {
            productDurationView.startTimeType = productPeriod.startTimeType;
        }
        if (productPeriod.earliestInsuranceTime != null) {
            productDurationView.earliestInsuranceTime = insurancePeriodView(productPeriod.earliestInsuranceTime);
        }
        return productDurationView;
    }

    private InsurancePeriodView insurancePeriodView(InsurancePeriod insurancePeriod) {
        InsurancePeriodView insurancePeriodView = new InsurancePeriodView();
        insurancePeriodView.displayName = insurancePeriod.displayName;
        insurancePeriodView.value = insurancePeriod.value;
        insurancePeriodView.unit = insurancePeriod.unit;
        return insurancePeriodView;
    }

    private ProductInsuranceView insurance(ProductInsurance productInsurance) {
        ProductInsuranceView productInsuranceView = new ProductInsuranceView();
        productInsuranceView.insuranceId = productInsurance.insuranceId;
        productInsuranceView.optional = productInsurance.optional;
        productInsuranceView.liabilities = productInsurance.liabilities.stream().map(this::insuranceLiability).collect(Collectors.toList());
        return productInsuranceView;
    }

    private ProductAgreeUrlView agreeUrl(ProductAgreeUrl productAgreeUrl) {
        ProductAgreeUrlView productAgreeUrlView = new ProductAgreeUrlView();
        productAgreeUrlView.name = productAgreeUrl.name;
        productAgreeUrlView.url = productAgreeUrl.url;
        return productAgreeUrlView;
    }

    private InsuranceLiabilityRefView insuranceLiability(InsuranceLiabilityRef insuranceLiabilityRef) {
        InsuranceLiabilityRefView insuranceLiabilityRefView = new InsuranceLiabilityRefView();
        insuranceLiabilityRefView.insuranceLiabilityId = insuranceLiabilityRef.insuranceLiabilityId;
        insuranceLiabilityRefView.amount = amount(insuranceLiabilityRef.amount);
        insuranceLiabilityRefView.description = insuranceLiabilityRef.description;
        return insuranceLiabilityRefView;
    }

    private InsuranceAmountView amount(InsuranceAmount insuranceAmount) {
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
}
