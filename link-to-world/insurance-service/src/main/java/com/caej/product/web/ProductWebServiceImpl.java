package com.caej.product.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceAmountView;
import com.caej.insurance.api.insurance.InsuranceLiabilityRefView;
import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.insurance.domain.InsuranceAmount;
import com.caej.insurance.domain.InsuranceLiabilityRef;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.AuditingProductAuditRequest;
import com.caej.product.api.product.AuditingProductQuery;
import com.caej.product.api.product.AuditingProductResponse;
import com.caej.product.api.product.AuditingStatus;
import com.caej.product.api.product.ProductAdminRequest;
import com.caej.product.api.product.ProductAdminResponse;
import com.caej.product.api.product.ProductAgreeUrlView;
import com.caej.product.api.product.ProductFormFieldView;
import com.caej.product.api.product.ProductFormGroupView;
import com.caej.product.api.product.ProductInsuranceView;
import com.caej.product.api.product.ProductPaymentView;
import com.caej.product.api.product.ProductPeriodView;
import com.caej.product.api.product.ProductQuestionView;
import com.caej.product.api.product.ProductRequest;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.UpdateProductStatusRequest;
import com.caej.product.api.serial.ProductSerialProductRequest;
import com.caej.product.domain.AuditingProduct;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductAgreeUrl;
import com.caej.product.domain.ProductFormGroup;
import com.caej.product.domain.ProductInsurance;
import com.caej.product.domain.ProductPayment;
import com.caej.product.domain.ProductPeriod;
import com.caej.product.domain.ProductQuestion;
import com.caej.product.domain.ProductStatus;
import com.caej.product.service.ProductBuilder;
import com.caej.product.service.ProductDiffer;
import com.caej.product.service.ProductSerialService;
import com.caej.product.service.ProductService;
import com.caej.util.UUIDUtil;

import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class ProductWebServiceImpl implements ProductWebService {
    @Inject
    ProductService productService;
    @Inject
    ProductSerialService productSerialService;

    @Override
    public ProductResponse get(String id) {
        return new ProductResponseBuilder(productService.get(new ObjectId(id))).build();
    }

    @Override
    public List<ProductResponse> list() {
        return productService.list(ProductStatus.ACTIVE).stream().map(product -> new ProductResponseBuilder(product).build()).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getByName(@PathParam("name") String name) {
        Optional<Product> productOptional = productService.getByNameIgnoreStatus(name);
        if (productOptional.isPresent()) {
            return new ProductResponseBuilder(productOptional.get()).build();
        } else {
            throw new NotFoundException("missing product,name={}", name);
        }
    }

    @Override
    public ProductResponse getByNameIgnoreStatus(@PathParam("name") String name) {
        Optional<Product> productOptional = productService.getByNameIgnoreStatus(name);
        if (productOptional.isPresent()) {
            return new ProductResponseBuilder(productOptional.get()).build();
        } else {
            throw new NotFoundException("missing product,name={}", name);
        }
    }

    @Override
    public Long countActiveInsuranceClause(@PathParam("id") String id) {
        return productService.countActiveInsuranceClause(new ObjectId(id));
    }

    @Override
    public Long countActiveInsuranceClaim(@PathParam("id") String id) {
        return productService.countActiveInsuranceClaim(new ObjectId(id));
    }

    @Override
    public FindView<ProductAdminResponse> findAdmin(ProductAdminRequest request) {
        return FindView.map(productService.findAdmin(request), this::adminResponse);
    }

    @Override
    public void updateProductStatus(@PathParam("id") String id, UpdateProductStatusRequest request) {
        productService.updateProductStatus(new ObjectId(id), request);
    }

    @Override
    public ProductAdminResponse getAdmin(@PathParam("id") String id) {
        return adminResponse(productService.getById(new ObjectId(id)));
    }

    @Override
    public void createAudit(ProductRequest request) {
        if (request.name == null) {
            request.name = UUIDUtil.generate();
        }
        Product product = new ProductBuilder(request).setId(request.id).setVersion(request.version).build();
        productService.createAudit(product);
    }

    @Override
    public FindView<AuditingProductResponse> auditList(AuditingProductQuery query) {
        return FindView.map(productService.auditList(query), this::auditResponse);
    }

    @Override
    public void deleteAudit(@PathParam("id") String id) {
        productService.deleteAudit(new ObjectId(id));
    }

    @Override
    public AuditingProductResponse getAudit(@PathParam("id") String id) {
        return auditResponse(productService.getAudit(new ObjectId(id)));
    }

    @Override
    public void audit(@PathParam("id") String id, AuditingProductAuditRequest request) {
        AuditingProduct auditingProduct = productService.getAudit(new ObjectId(id));
        Optional<Product> productOptional = productService.getByNameIgnoreStatus(auditingProduct.product.name);
        if (AuditingStatus.SUCCESS.equals(request.status)) {
            if (productOptional.isPresent()) {
                auditProduct(productOptional.get(), request, auditingProduct, id);
            } else {
                insertNewProduct(auditingProduct, request.requestBy);
            }
        }
        productService.updateAuditProduct(new ObjectId(id), request);
    }

    private void auditProduct(Product product, AuditingProductAuditRequest request, AuditingProduct auditingProduct, String id) {
        if (product != null && product.version.equals(auditingProduct.product.version)) {
            product.latestVersion = false;
            ProductSerialProductRequest removeOld = new ProductSerialProductRequest();
            removeOld.productName = product.name;
            removeOld.serialDisplayName = product.serialDisplayName;
            removeOld.requestBy = request.requestBy;
            if (product.serialId != null) {
                productSerialService.removeProduct(product.serialId, removeOld);
            }
            productService.update(product.id, product, request.requestBy);
            productService.updateAuditProduct(new ObjectId(id), request);
            Product newProduct = auditingProduct.product;
            if (newProduct.serialId != null) {
                ProductSerialProductRequest addNew = new ProductSerialProductRequest();
                addNew.productName = newProduct.name;
                addNew.serialDisplayName = newProduct.serialDisplayName;
                addNew.requestBy = request.requestBy;
                productSerialService.addProduct(newProduct.serialId, addNew);
            }
            newProduct.id = null;
            newProduct.version = auditingProduct.version;
            newProduct.createdTime = LocalDateTime.now();
            newProduct.createdBy = request.requestBy;
            newProduct.updatedTime = LocalDateTime.now();
            newProduct.updatedBy = request.requestBy;
            productService.insert(newProduct);
        } else {
            request.status = AuditingStatus.FAILED;
            request.comment = request.comment + "\n审核失败：版本号已过时";
        }
    }

    private void insertNewProduct(AuditingProduct auditingProduct, String requestBy) {
        Product newProduct = auditingProduct.product;
        if (newProduct.serialId != null) {
            ProductSerialProductRequest request = new ProductSerialProductRequest();
            request.productName = newProduct.name;
            request.serialDisplayName = newProduct.serialDisplayName;
            request.requestBy = requestBy;
            productSerialService.addProduct(newProduct.serialId, request);
        }
        newProduct.id = null;
        newProduct.version = auditingProduct.version;
        newProduct.createdTime = LocalDateTime.now();
        newProduct.createdBy = requestBy;
        newProduct.updatedTime = LocalDateTime.now();
        newProduct.updatedBy = requestBy;
        productService.insert(newProduct);
    }

    @Override
    public String auditDiffer(@PathParam("id") String id) {
        AuditingProduct auditingProduct = productService.getAudit(new ObjectId(id));
        Optional<Product> productOptional = productService.getByNameAndVersion(auditingProduct.product.name, auditingProduct.product.version);
        ProductDiffer productDiffer = new ProductDiffer();
        return productDiffer.toString(productDiffer.diff(productOptional.isPresent() ? productOptional.get() : new Product(), auditingProduct.product));
    }

    private AuditingProductResponse auditResponse(AuditingProduct auditingProduct) {
        AuditingProductResponse auditingProductResponse = new AuditingProductResponse();
        auditingProductResponse.id = auditingProduct.id;
        auditingProductResponse.product = adminResponse(auditingProduct.product);
        auditingProductResponse.productDisplayName = auditingProduct.productDisplayName;
        auditingProductResponse.version = auditingProduct.version;
        auditingProductResponse.createdTime = auditingProduct.createdTime;
        auditingProductResponse.createdBy = auditingProduct.createdBy;
        auditingProductResponse.auditedBy = auditingProduct.auditedBy;
        auditingProductResponse.auditedTime = auditingProduct.auditedTime;
        auditingProductResponse.comment = auditingProduct.comment;
        auditingProductResponse.status = auditingProduct.status;
        return auditingProductResponse;
    }

    public ProductAdminResponse adminResponse(Product product) {
        ProductAdminResponse response = new ProductAdminResponse();
        response.id = product.id == null ? null : product.id.toHexString();
        response.name = product.name;
        response.version = product.version;
        response.latestVersion = product.latestVersion;
        response.serialId = product.serialId;
        response.serialDisplayName = product.serialDisplayName;
        response.displayName = product.displayName;
        response.keywords = product.keywords;
        response.highlightContent = product.highlightContent;
        response.tipsContent = product.tipsContent;
        response.featureContent = product.featureContent;
        response.tags = product.tags;
        response.publicVisible = product.publicVisible;
        response.displayOrder = product.displayOrder;
        response.insuranceVendorId = product.insuranceVendorId;
        response.insuranceClaimIds = product.insuranceClaimIds;
        response.insuranceCategoryIds = product.insuranceCategoryIds;
        response.insuranceDeclarationIds = product.insuranceDeclarationIds;
        response.cases = product.cases;

        if (product.questions != null) {
            response.questions = questions(product.questions);
        }

        if (product.plp != null) {
            response.plp = product.plp;
        }

        if (product.pdp != null) {
            response.pdp = product.pdp;
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

        if (product.payment != null) {
            response.payment = productPaymentView(product.payment);
        }

        response.insuranceJobTreeId = product.insuranceJobTreeId;
        response.jobRestricted = product.jobRestricted;
        response.jobLevels = product.jobLevels;
        response.jobIds = product.jobIds;
        response.maxJobLevel = product.maxJobLevel;
        response.minJobLevel = product.minJobLevel;
        response.minUnits = product.minUnits;
        response.maxUnits = product.maxUnits;
        if (product.policyHolderMaxAge != null) {
            response.policyHolderMaxAge = insurancePeriodView(product.policyHolderMaxAge);
        }
        if (product.policyHolderMinAge != null) {
            response.policyHolderMinAge = insurancePeriodView(product.policyHolderMinAge);
        }
        if (product.insuredMaxAge != null) {
            response.insuredMaxAge = insurancePeriodView(product.insuredMaxAge);
        }
        if (product.insuredMinAge != null) {
            response.insuredMinAge = insurancePeriodView(product.insuredMinAge);
        }
        response.maxAmount = product.maxAmount;
        response.discount = product.discount;

        response.insuranceCityIds = product.insuranceCityIds;
        response.insuranceAreaIds = product.insuranceAreaIds;
        response.insurancePolicyDeliverTypes = product.insurancePolicyDeliverTypes;

        response.insuranceDividendTypes = product.insuranceDividendTypes;
        response.status = product.status;
        response.type = product.type;
        response.createdTime = product.createdTime;
        response.createdBy = product.createdBy;
        response.updatedTime = product.updatedTime;
        response.updatedBy = product.updatedBy;
        response.planCode = product.planCode;
        response.insuranceClauseIds = product.insuranceClauseIds;

        response.priceTableUrl = product.priceTableUrl;
        response.customerNotificationUrl = product.customerNotificationUrl;
        if (product.agreeUrls != null) {
            response.agreeUrls = product.agreeUrls.stream().map(this::agreeUrl).collect(Collectors.toList());
        }
        response.specialFunction = product.specialFunction;
        response.specialRule = product.specialRule;

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

    private ProductPaymentView productPaymentView(ProductPayment productPayment) {
        ProductPaymentView productPaymentView = new ProductPaymentView();
        if (productPayment.methods != null) {
            productPaymentView.methods = productPayment.methods;
        }
        if (productPayment.fixedPeriods != null)
            productPaymentView.fixedPeriods = productPayment.fixedPeriods.stream().map(this::insurancePeriodView).collect(Collectors.toList());
        if (productPayment.irregularPeriods != null)
            productPaymentView.irregularPeriods = productPayment.irregularPeriods.stream().map(this::insurancePeriodView).collect(Collectors.toList());
        if (productPayment.yearPeriods != null)
            productPaymentView.yearPeriods = productPayment.yearPeriods.stream().map(this::insurancePeriodView).collect(Collectors.toList());
        if (productPayment.agePeriods != null)
            productPaymentView.agePeriods = productPayment.agePeriods.stream().map(this::insurancePeriodView).collect(Collectors.toList());
        return productPaymentView;
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
