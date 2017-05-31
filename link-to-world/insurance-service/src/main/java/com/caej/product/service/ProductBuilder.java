package com.caej.product.service;

import static java.time.LocalDateTime.now;

import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceAmountView;
import com.caej.insurance.api.insurance.InsuranceLiabilityRefView;
import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.insurance.domain.InsuranceAmount;
import com.caej.insurance.domain.InsuranceLiabilityRef;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.api.product.ProductAgreeUrlView;
import com.caej.product.api.product.ProductFormGroupView;
import com.caej.product.api.product.ProductInsuranceView;
import com.caej.product.api.product.ProductPaymentRequest;
import com.caej.product.api.product.ProductPeriodView;
import com.caej.product.api.product.ProductQuestionView;
import com.caej.product.api.product.ProductRequest;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductAgreeUrl;
import com.caej.product.domain.ProductFormField;
import com.caej.product.domain.ProductFormGroup;
import com.caej.product.domain.ProductInsurance;
import com.caej.product.domain.ProductPayment;
import com.caej.product.domain.ProductPeriod;
import com.caej.product.domain.ProductQuestion;
import com.google.common.base.Strings;

import io.sited.util.JSON;

/**
 * @author chi
 */
public class ProductBuilder {
    private final ProductRequest request;
    private final Product product;

    public ProductBuilder(ProductRequest request) {
        this.request = request;
        this.product = new Product();
    }

    public ProductBuilder setId(ObjectId id) {
        this.product.id = id;
        return this;
    }

    public ProductBuilder setVersion(int version) {
        this.product.version = version;
        return this;
    }

    public Product build() {
        this.product.name = request.name;
        this.product.latestVersion = true;
        this.product.serialId = request.serialId;
        this.product.serialDisplayName = request.serialDisplayName;
        this.product.displayName = request.displayName;
        this.product.highlightContent = request.highlightContent;
        this.product.tipsContent = request.tipsContent;
        this.product.featureContent = request.featureContent;
        this.product.tags = request.tags;
        this.product.publicVisible = request.publicVisible;
        this.product.displayOrder = request.displayOrder;
        this.product.keywords = request.keywords;

        this.product.insuranceVendorId = request.insuranceVendorId;
        if (request.insuranceClaimIds != null) {
            this.product.insuranceClaimIds = request.insuranceClaimIds;
        }
        this.product.insuranceCategoryIds = request.insuranceCategoryIds;
        this.product.insuranceDeclarationIds = request.insuranceDeclarationIds;

        this.product.minUnits = request.minUnits;
        this.product.maxUnits = request.maxUnits;

        if (request.policyHolderMaxAge != null) {
            this.product.policyHolderMaxAge = insurancePeriod(request.policyHolderMaxAge);
        }
        if (request.policyHolderMinAge != null) {
            this.product.policyHolderMinAge = insurancePeriod(request.policyHolderMinAge);
        }
        if (request.insuredMaxAge != null) {
            this.product.insuredMaxAge = insurancePeriod(request.insuredMaxAge);
        }
        if (request.insuredMinAge != null) {
            this.product.insuredMinAge = insurancePeriod(request.insuredMinAge);
        }

        this.product.maxAmount = request.maxAmount;

        this.product.cases = request.cases;

        if (request.questions != null) {
            this.product.questions = request.questions.stream().map(this::question).collect(Collectors.toList());
        }

        if (request.pdp != null) {
            this.product.pdp = request.pdp;
        }

        if (request.plp != null) {
            this.product.plp = request.plp;
        }

        if (request.formGroups != null) {
            this.product.formGroups = request.formGroups.stream().sorted((o1, o2) -> (o1.displayOrder == null ? 1 : o1.displayOrder) - (o2.displayOrder == null ? 0 : o2.displayOrder)).map(this::formGroup).collect(Collectors.toList());
        }

        if (request.insurances != null) {
            this.product.insurances = request.insurances.stream().map(this::insurance).collect(Collectors.toList());
        }

        if (request.period != null) {
            this.product.period = period(request.period);
        }

        if (request.payment != null) {
            this.product.payment = payment(request.payment);
        }

        this.product.discount = request.discount;
        this.product.insuranceJobTreeId = request.insuranceJobTreeId;
        this.product.jobRestricted = request.jobRestricted;
        this.product.jobLevels = request.jobLevels;
        this.product.jobIds = request.jobIds;
        this.product.maxJobLevel = request.maxJobLevel;
        this.product.minJobLevel = request.minJobLevel;

        this.product.insuranceCityIds = request.insuranceCityIds;
        this.product.insuranceAreaIds = request.insuranceAreaIds;
        this.product.insuranceClauseIds = request.insuranceClauseIds;

        this.product.insurancePolicyDeliverTypes = request.insurancePolicyDeliverTypes;
        this.product.insuranceDividendTypes = request.insuranceDividendTypes;

        this.product.status = request.status;
        this.product.type = request.type;
        this.product.planCode = request.planCode;
        this.product.priceTableUrl = request.priceTableUrl;
        this.product.customerNotificationUrl = request.customerNotificationUrl;
        if (request.agreeUrls != null) {
            this.product.agreeUrls = request.agreeUrls.stream().map(this::agreeUrl).collect(Collectors.toList());
        }
        this.product.specialFunction = request.specialFunction;
        this.product.specialRule = request.specialRule;

        this.product.createdTime = request.createdTime == null ? now() : request.createdTime;
        this.product.createdBy = Strings.isNullOrEmpty(request.createdBy) ? "init" : request.createdBy;
        this.product.updatedTime = request.updatedTime == null ? now() : request.updatedTime;
        this.product.updatedBy = Strings.isNullOrEmpty(request.updatedBy) ? "init" : request.updatedBy;

        return product;
    }

    private InsurancePeriod insurancePeriod(InsurancePeriodView insurancePeriodView) {
        InsurancePeriod insurancePeriod = new InsurancePeriod();
        insurancePeriod.displayName = insurancePeriodView.displayName;
        insurancePeriod.value = insurancePeriodView.value;
        insurancePeriod.unit = insurancePeriodView.unit;
        return insurancePeriod;
    }

    private ProductPeriod period(ProductPeriodView request) {
        ProductPeriod productPeriod = new ProductPeriod();
        productPeriod.type = request.type;
        if (request.inputUnit != null) {
            productPeriod.inputUnit = request.inputUnit;
        }
        if (request.inputMin != null) {
            productPeriod.inputMin = insurancePeriod(request.inputMin);
        }
        if (request.inputMax != null) {
            productPeriod.inputMax = insurancePeriod(request.inputMax);
        }
        if (request.selections != null) {
            productPeriod.selections = request.selections.stream().map(this::insurancePeriod).collect(Collectors.toList());
        }
        if (request.ages != null) {
            productPeriod.ages = request.ages.stream().map(this::insurancePeriod).collect(Collectors.toList());
        }
        if (request.fixedValue != null) {
            productPeriod.fixedValue = insurancePeriod(request.fixedValue);
        }
        if (request.startTimeType != null) {
            productPeriod.startTimeType = request.startTimeType;
        }
        if (request.earliestInsuranceTime != null) {
            productPeriod.earliestInsuranceTime = insurancePeriod(request.earliestInsuranceTime);
        }
        return productPeriod;
    }

    private ProductPayment payment(ProductPaymentRequest payment) {
        ProductPayment productPayment = new ProductPayment();
        productPayment.methods = payment.methods;
        if (payment.fixedPeriods != null) {
            productPayment.fixedPeriods = payment.fixedPeriods.stream().map(view -> {
                InsurancePeriod insurancePeriod = new InsurancePeriod();
                insurancePeriod.displayName = view.displayName;
                insurancePeriod.unit = view.unit;
                insurancePeriod.value = view.value;
                return insurancePeriod;
            }).collect(Collectors.toList());
        }
        if (payment.irregularPeriods != null) {
            productPayment.irregularPeriods = payment.irregularPeriods.stream().map(view -> {
                InsurancePeriod insurancePeriod = new InsurancePeriod();
                insurancePeriod.displayName = view.displayName;
                insurancePeriod.unit = view.unit;
                insurancePeriod.value = view.value;
                return insurancePeriod;
            }).collect(Collectors.toList());
        }
        if (payment.yearPeriods != null) {
            productPayment.yearPeriods = payment.yearPeriods.stream().map(view -> {
                InsurancePeriod insurancePeriod = new InsurancePeriod();
                insurancePeriod.displayName = view.displayName;
                insurancePeriod.unit = view.unit;
                insurancePeriod.value = view.value;
                return insurancePeriod;
            }).collect(Collectors.toList());
        }
        if (payment.agePeriods != null) {
            productPayment.agePeriods = payment.agePeriods.stream().map(view -> {
                InsurancePeriod insurancePeriod = new InsurancePeriod();
                insurancePeriod.displayName = view.displayName;
                insurancePeriod.unit = view.unit;
                insurancePeriod.value = view.value;
                return insurancePeriod;
            }).collect(Collectors.toList());
        }
        return productPayment;
    }

    private ProductQuestion question(ProductQuestionView productQuestionView) {
        ProductQuestion question = new ProductQuestion();
        question.question = productQuestionView.question;
        question.answer = productQuestionView.answer;
        question.displayOrder = null == productQuestionView.displayOrder ? 0 : productQuestionView.displayOrder;
        return question;
    }

    private ProductFormGroup formGroup(ProductFormGroupView productFormGroupView) {
        ProductFormGroup productFormGroup = new ProductFormGroup();
        productFormGroup.name = productFormGroupView.name;
        productFormGroup.displayOrder = productFormGroupView.displayOrder;
        productFormGroup.multiple = productFormGroupView.multiple;
        productFormGroup.optional = productFormGroupView.optional;
        productFormGroup.fields = productFormGroupView.fields.stream().sorted((o1, o2) -> (o1.displayOrder == null ? 1 : o1.displayOrder) - (o2.displayOrder == null ? 0 : o2.displayOrder)).map(productFormFieldView -> {
            ProductFormField productFormField = new ProductFormField();
            productFormField.name = productFormFieldView.name;
            productFormField.displayOrder = productFormFieldView.displayOrder;
            productFormField.options = productFormFieldView.options;
            productFormField.editable = productFormFieldView.editable;
            productFormField.multiple = productFormFieldView.multiple;
            productFormField.displayAs = productFormFieldView.displayAs;
            if (productFormFieldView.defaultValue == null) {
                productFormField.defaultValue = null;
            } else {
                productFormField.defaultValue = productFormFieldView.defaultValue.getClass().isAssignableFrom(String.class) ? productFormFieldView.defaultValue.toString() : JSON.toJSON(productFormFieldView.defaultValue);
            }
            return productFormField;
        }).collect(Collectors.toList());

        return productFormGroup;
    }

    private ProductInsurance insurance(ProductInsuranceView productInsuranceView) {
        ProductInsurance productInsurance = new ProductInsurance();
        productInsurance.insuranceId = productInsuranceView.insuranceId;
        productInsurance.optional = productInsuranceView.optional;
        productInsurance.liabilities = productInsuranceView.liabilities.stream().map(this::insuranceLiability).collect(Collectors.toList());
        return productInsurance;
    }

    private ProductAgreeUrl agreeUrl(ProductAgreeUrlView productAgreeUrlView) {
        ProductAgreeUrl productAgreeUrl = new ProductAgreeUrl();
        productAgreeUrl.name = productAgreeUrlView.name;
        productAgreeUrl.url = productAgreeUrlView.url;
        return productAgreeUrl;
    }

    private InsuranceLiabilityRef insuranceLiability(InsuranceLiabilityRefView productInsuranceLiabilityView) {
        InsuranceLiabilityRef productInsuranceLiability = new InsuranceLiabilityRef();
        productInsuranceLiability.insuranceLiabilityId = productInsuranceLiabilityView.insuranceLiabilityId;
        productInsuranceLiability.amount = amount(productInsuranceLiabilityView.amount);
        productInsuranceLiability.description = productInsuranceLiabilityView.description;
        return productInsuranceLiability;
    }

    private InsuranceAmount amount(InsuranceAmountView insuranceAmountView) {
        InsuranceAmount insuranceAmount = new InsuranceAmount();
        insuranceAmount.type = insuranceAmountView.type;
        insuranceAmount.inputMax = insuranceAmountView.inputMax;
        insuranceAmount.inputMin = insuranceAmountView.inputMin;
        insuranceAmount.inputIncrementUnit = insuranceAmountView.inputIncrementUnit;
        insuranceAmount.selections = insuranceAmountView.selections;
        insuranceAmount.minUnits = insuranceAmountView.minUnits;
        insuranceAmount.maxUnits = insuranceAmountView.maxUnits;
        insuranceAmount.formulaName = insuranceAmountView.formulaName;
        insuranceAmount.fixedValue = insuranceAmountView.fixedValue;
        return insuranceAmount;
    }
}
