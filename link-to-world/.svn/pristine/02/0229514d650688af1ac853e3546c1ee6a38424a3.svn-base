package com.caej.product;

import java.time.Duration;

import com.caej.insurance.api.EnumBeneficiaryTypeWebService;
import com.caej.insurance.api.EnumCertiTypeWebService;
import com.caej.insurance.api.EnumDeliverTypeWebService;
import com.caej.insurance.api.EnumGenderTypeWebService;
import com.caej.insurance.api.EnumInvoiceDeliverTypeWebService;
import com.caej.insurance.api.EnumLegalBeneficiaryTypeWebService;
import com.caej.insurance.api.EnumMarriageTypeWebService;
import com.caej.insurance.api.EnumPolicyRelationTypeWebService;
import com.caej.insurance.api.EnumPolicyTypeWebService;
import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.InsuranceJobWebService;
import com.caej.insurance.api.InsuranceLiabilityGroupWebService;
import com.caej.insurance.api.InsuranceLiabilityWebService;
import com.caej.insurance.api.InsuranceModule;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.InsuranceWebService;
import com.caej.insurance.api.country.AllInsuranceCountryResponse;
import com.caej.insurance.api.enumtype.AllEnumBeneficiaryTypeResponse;
import com.caej.insurance.api.enumtype.AllEnumCertiTypeResponse;
import com.caej.insurance.api.enumtype.AllEnumDeliverTypeResponse;
import com.caej.insurance.api.enumtype.AllEnumGenderTypeResponse;
import com.caej.insurance.api.enumtype.AllEnumInvoiceDeliverTypeResponse;
import com.caej.insurance.api.enumtype.AllEnumLegalBeneficiaryTypeResponse;
import com.caej.insurance.api.enumtype.AllEnumMarriageTypeResponse;
import com.caej.insurance.api.enumtype.AllEnumPolicyRelationTypeResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityGroupResponseList;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;
import com.caej.insurance.api.insurance.InsuranceResponse;
import com.caej.insurance.api.job.JobResponseList;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.product.api.ArchiveWebService;
import com.caej.product.api.ProductConvertWebService;
import com.caej.product.api.ProductDetailWebService;
import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.ProductModule;
import com.caej.product.api.ProductPriceWebService;
import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.ProductSerialWebService;
import com.caej.product.api.ProductWebService;
import com.caej.product.domain.AuditingProduct;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductAmount;
import com.caej.product.domain.ProductSerial;
import com.caej.product.domain.ProductSubject;
import com.caej.product.service.CustomFormGroupProvider;
import com.caej.product.service.FormGroupProvider;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductInsuranceLiabilityService;
import com.caej.product.service.ProductPriceService;
import com.caej.product.service.ProductSerialService;
import com.caej.product.service.ProductService;
import com.caej.product.service.archive.AddressArchiveProvider;
import com.caej.product.service.archive.ArchiveService;
import com.caej.product.service.archive.BirthDateArchiveProvider;
import com.caej.product.service.archive.BooleanArchiveProvider;
import com.caej.product.service.archive.DeliverTypeArchiveProvider;
import com.caej.product.service.archive.GenderArchiveProvider;
import com.caej.product.service.archive.IdArchiveProvider;
import com.caej.product.service.archive.InvoiceDeliverTypeArchiveProvider;
import com.caej.product.service.archive.JobArchiveProvider;
import com.caej.product.service.archive.LegalBeneficiaryArchiveProvider;
import com.caej.product.service.archive.LiabilityArchiveProvider;
import com.caej.product.service.archive.LocalDateArchiveProvider;
import com.caej.product.service.archive.LocalDateTimeArchiveProvider;
import com.caej.product.service.archive.MarriageTypeArchiveProvider;
import com.caej.product.service.archive.PlanPeriodArchiveProvider;
import com.caej.product.service.archive.PlanStartTimeArchiveProvider;
import com.caej.product.service.archive.RelationArchiveProvider;
import com.caej.product.service.archive.TravelDestArchiveProvider;
import com.caej.product.service.client.EnumBeneficiaryTypeWebServiceClient;
import com.caej.product.service.client.EnumCertiTypeWebServiceClient;
import com.caej.product.service.client.EnumDeliverTypeWebServiceClient;
import com.caej.product.service.client.EnumGenderTypeWebServiceClient;
import com.caej.product.service.client.EnumInvoiceDeliverTypeWebServiceClient;
import com.caej.product.service.client.EnumLegalBeneficiaryTypeWebServiceClient;
import com.caej.product.service.client.EnumMarriageTypeWebServiceClient;
import com.caej.product.service.client.EnumPolicyRelationTypeWebServiceClient;
import com.caej.product.service.client.InsuranceCountryWebServiceClient;
import com.caej.product.service.client.InsuranceJobWebServiceClient;
import com.caej.product.service.client.InsuranceLiabilityGroupWebServiceClient;
import com.caej.product.service.client.InsuranceLiabilityWebServiceClient;
import com.caej.product.service.client.InsuranceVendorWebServiceClient;
import com.caej.product.service.client.InsuranceWebServiceClient;
import com.caej.product.service.field.AddressFieldType;
import com.caej.product.service.field.AgeFieldType;
import com.caej.product.service.field.AgreeFieldType;
import com.caej.product.service.field.BenefitTypeFieldType;
import com.caej.product.service.field.BirthDateFieldType;
import com.caej.product.service.field.BooleanFieldType;
import com.caej.product.service.field.DeliverTypeFieldType;
import com.caej.product.service.field.EffectiveInsuranceFieldType;
import com.caej.product.service.field.EmailFieldType;
import com.caej.product.service.field.GenderFieldType;
import com.caej.product.service.field.IdentificationFieldType;
import com.caej.product.service.field.InsuranceAmountFieldType;
import com.caej.product.service.field.InvoiceDeliveryTypeFieldType;
import com.caej.product.service.field.JobFieldType;
import com.caej.product.service.field.LegalBeneficiaryFieldType;
import com.caej.product.service.field.LiabilityFieldType;
import com.caej.product.service.field.MarriageTypeFieldType;
import com.caej.product.service.field.NationalityFieldType;
import com.caej.product.service.field.PhoneFieldType;
import com.caej.product.service.field.PlanStartTimeFieldType;
import com.caej.product.service.field.PolicyTypeFieldType;
import com.caej.product.service.field.PostalCodeFieldType;
import com.caej.product.service.field.ProductPaymentFieldType;
import com.caej.product.service.field.ProductPeriodFieldType;
import com.caej.product.service.field.ProductSerialFieldType;
import com.caej.product.service.field.RelationFieldType;
import com.caej.product.service.field.SumPriceFieldType;
import com.caej.product.service.field.TravelDestFieldType;
import com.caej.product.service.field.UnitFieldType;
import com.caej.product.service.field.provider.AgeFieldProvider;
import com.caej.product.service.field.provider.AgreeFieldProvider;
import com.caej.product.service.field.provider.BenefitTypeFieldProvider;
import com.caej.product.service.field.provider.DefaultFieldProvider;
import com.caej.product.service.field.provider.DeliverTypeFieldProvider;
import com.caej.product.service.field.provider.GenderFieldProvider;
import com.caej.product.service.field.provider.IdFieldProvider;
import com.caej.product.service.field.provider.InvoiceDeliveryTypeFieldProvider;
import com.caej.product.service.field.provider.JobFieldProvider;
import com.caej.product.service.field.provider.LegalBeneficiaryFieldProvider;
import com.caej.product.service.field.provider.MarriageTypeFieldProvider;
import com.caej.product.service.field.provider.NationalityFieldProvider;
import com.caej.product.service.field.provider.PlanPaymentFieldProvider;
import com.caej.product.service.field.provider.PlanPeriodFieldProvider;
import com.caej.product.service.field.provider.PlanStartTimeFieldProvider;
import com.caej.product.service.field.provider.PolicyTypeFieldProvider;
import com.caej.product.service.field.provider.ProductSerialFieldProvider;
import com.caej.product.service.field.provider.RelationFieldProvider;
import com.caej.product.service.field.provider.TravelDestFieldProvider;
import com.caej.product.service.field.provider.UnitFieldProvider;
import com.caej.product.service.group.ProductFormGroupProvider;
import com.caej.product.service.group.ProductLiabilityFormGroupProvider;
import com.caej.product.service.price.ProductPriceEngine;
import com.caej.product.service.price.engine.BaseTableInsurancePriceEngineImpl;
import com.caej.product.service.price.engine.FixedInsurancePriceEngineImpl;
import com.caej.product.service.price.engine.SimpleProductPriceEngineImpl;
import com.caej.product.service.price.engine.TableInsurancePriceEngineImpl;
import com.caej.product.web.ArchiveWebServiceImpl;
import com.caej.product.web.ProductConvertWebServiceImpl;
import com.caej.product.web.ProductDetailWebServiceImpl;
import com.caej.product.web.ProductFormWebServiceImpl;
import com.caej.product.web.ProductPriceWebServiceImpl;
import com.caej.product.web.ProductSearchWebServiceImpl;
import com.caej.product.web.ProductSerialWebServiceImpl;
import com.caej.product.web.ProductWebServiceImpl;
import com.mongodb.client.MongoDatabase;

import app.dealer.api.DealerModule;
import app.dealer.api.DealerProductWebService;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.cache.Cache;
import io.sited.cache.CacheConfig;
import io.sited.cache.CacheModule;
import io.sited.cache.CacheOptions;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.db.impl.mongo.MongoInstaller;
import io.sited.file.api.FileModule;
import io.sited.form.FormConfig;
import io.sited.form.FormModule;
import io.sited.template.TemplateConfig;
import io.sited.template.TemplateModule;
import io.sited.util.source.ClasspathSourceRepository;

/**
 * @author chi
 */
@ModuleInfo(name = "product.api", require = {DBModule.class, APIModule.class, InsuranceModule.class, FormModule.class, DealerModule.class,
    TemplateModule.class, FileModule.class, CacheModule.class})
public class ProductModuleImpl extends ProductModule {
    @Override
    protected void configure() throws Exception {
        require(MongoConfig.class)
            .entity(Product.class)
            .entity(ProductAmount.class)
            .entity(ProductSerial.class)
            .entity(ProductSubject.class)
            .entity(AuditingProduct.class);
        bindCacheClient();
        bind(ProductSerialService.class);
        bind(ProductService.class);
        export(ProductService.class);

        bind(ProductInsuranceLiabilityService.class);
        bind(ProductFormService.class);
        export(ProductFormService.class);

        SimpleProductPriceEngineImpl productPriceEngine = bind(SimpleProductPriceEngineImpl.class);
        productPriceEngine.setInsurancePriceEngine("FIXED", new FixedInsurancePriceEngineImpl());
        productPriceEngine.setInsurancePriceEngine("TABLE", new TableInsurancePriceEngineImpl());
        productPriceEngine.setInsurancePriceEngine("BASE_TABLE", new BaseTableInsurancePriceEngineImpl());

        bind(ProductPriceEngine.class, productPriceEngine);
        bind(ProductPriceService.class);
        configureFields();

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.service(ProductWebService.class, ProductWebServiceImpl.class);
        apiConfig.service(ProductDetailWebService.class, ProductDetailWebServiceImpl.class);
        apiConfig.service(ProductSearchWebService.class, ProductSearchWebServiceImpl.class);
        apiConfig.service(ProductSerialWebService.class, ProductSerialWebServiceImpl.class);
        apiConfig.service(ProductPriceWebService.class, ProductPriceWebServiceImpl.class);
        apiConfig.service(ProductFormWebService.class, ProductFormWebServiceImpl.class);
        apiConfig.service(ProductConvertWebService.class, ProductConvertWebServiceImpl.class);
        apiConfig.service(ArchiveWebService.class, ArchiveWebServiceImpl.class);

        initProducts();
        initProductSerials();

        ClasspathSourceRepository repository = new ClasspathSourceRepository("/META-INF/web");
        TemplateConfig templateConfig = require(TemplateConfig.class);
        templateConfig.addRepository(repository);

    }

    private void bindCacheClient() {

        Cache<AllEnumPolicyRelationTypeResponse> policyRelationTypeCache = require(CacheConfig.class).create(AllEnumPolicyRelationTypeResponse.class, cacheOption("AllEnumPolicyRelationTypeResponse"));
        Cache<AllEnumGenderTypeResponse> genderTypeCache = require(CacheConfig.class).create(AllEnumGenderTypeResponse.class, cacheOption("AllEnumGenderTypeResponse"));
        Cache<AllEnumCertiTypeResponse> certiTypeCache = require(CacheConfig.class).create(AllEnumCertiTypeResponse.class, cacheOption("AllEnumCertiTypeResponse"));
        Cache<AllEnumMarriageTypeResponse> marriageTypeCache = require(CacheConfig.class).create(AllEnumMarriageTypeResponse.class, cacheOption("AllEnumMarriageTypeResponse"));
        Cache<AllInsuranceCountryResponse> countryCache = require(CacheConfig.class).create(AllInsuranceCountryResponse.class, cacheOption("AllInsuranceCountryResponse"));
        Cache<JobResponseList> jobCache = require(CacheConfig.class).create(JobResponseList.class, cacheOption("JobResponseList"));
        Cache<AllEnumBeneficiaryTypeResponse> beneficiaryTypeCache = require(CacheConfig.class).create(AllEnumBeneficiaryTypeResponse.class, cacheOption("AllEnumBeneficiaryTypeResponse"));
        Cache<AllEnumLegalBeneficiaryTypeResponse> legalBeneficiaryTypeCache = require(CacheConfig.class).create(AllEnumLegalBeneficiaryTypeResponse.class, cacheOption("AllEnumLegalBeneficiaryTypeResponse"));
        Cache<AllEnumDeliverTypeResponse> deliverTypeCache = require(CacheConfig.class).create(AllEnumDeliverTypeResponse.class, cacheOption("AllEnumDeliverTypeResponse"));
        Cache<AllEnumInvoiceDeliverTypeResponse> invoiceDeliverTypeCache = require(CacheConfig.class).create(AllEnumInvoiceDeliverTypeResponse.class, cacheOption("AllEnumInvoiceDeliverTypeResponse"));
        Cache<InsuranceVendorResponse> vendorCache = require(CacheConfig.class).create(InsuranceVendorResponse.class, cacheOption("InsuranceVendorResponse"));
        Cache<InsuranceLiabilityGroupResponseList> liabilityGroupCache = require(CacheConfig.class).create(InsuranceLiabilityGroupResponseList.class, cacheOption("InsuranceLiabilityGroupResponseList"));
        Cache<InsuranceLiabilityResponse> liabilityCache = require(CacheConfig.class).create(InsuranceLiabilityResponse.class, cacheOption("InsuranceLiabilityResponse"));
        Cache<InsuranceResponse> insuranceCache = require(CacheConfig.class).create(InsuranceResponse.class, cacheOption("InsuranceResponse"));


        bind(EnumPolicyRelationTypeWebServiceClient.class, new EnumPolicyRelationTypeWebServiceClient(policyRelationTypeCache, require(EnumPolicyRelationTypeWebService.class)));
        bind(EnumGenderTypeWebServiceClient.class, new EnumGenderTypeWebServiceClient(genderTypeCache, require(EnumGenderTypeWebService.class)));
        bind(EnumCertiTypeWebServiceClient.class, new EnumCertiTypeWebServiceClient(certiTypeCache, require(EnumCertiTypeWebService.class)));
        bind(EnumMarriageTypeWebServiceClient.class, new EnumMarriageTypeWebServiceClient(marriageTypeCache, require(EnumMarriageTypeWebService.class)));
        bind(InsuranceCountryWebServiceClient.class, new InsuranceCountryWebServiceClient(countryCache, require(InsuranceCountryWebService.class)));
        bind(InsuranceJobWebServiceClient.class, new InsuranceJobWebServiceClient(jobCache, require(InsuranceJobWebService.class)));
        bind(EnumBeneficiaryTypeWebServiceClient.class, new EnumBeneficiaryTypeWebServiceClient(beneficiaryTypeCache, require(EnumBeneficiaryTypeWebService.class)));
        bind(EnumLegalBeneficiaryTypeWebServiceClient.class, new EnumLegalBeneficiaryTypeWebServiceClient(legalBeneficiaryTypeCache, require(EnumLegalBeneficiaryTypeWebService.class)));
        bind(EnumDeliverTypeWebServiceClient.class, new EnumDeliverTypeWebServiceClient(deliverTypeCache, require(EnumDeliverTypeWebService.class)));
        bind(EnumInvoiceDeliverTypeWebServiceClient.class, new EnumInvoiceDeliverTypeWebServiceClient(invoiceDeliverTypeCache, require(EnumInvoiceDeliverTypeWebService.class)));
        bind(InsuranceVendorWebServiceClient.class, new InsuranceVendorWebServiceClient(vendorCache, require(InsuranceVendorWebService.class)));
        bind(InsuranceLiabilityGroupWebServiceClient.class, new InsuranceLiabilityGroupWebServiceClient(liabilityGroupCache, require(InsuranceLiabilityGroupWebService.class)));
        bind(InsuranceLiabilityWebServiceClient.class, new InsuranceLiabilityWebServiceClient(liabilityCache, require(InsuranceLiabilityWebService.class)));
        bind(InsuranceWebServiceClient.class, new InsuranceWebServiceClient(insuranceCache, require(InsuranceWebService.class)));
    }

    private void configureFields() {
        FormConfig formConfig = require(FormConfig.class);
        formConfig.declare(new AgeFieldType())
            .declare(bind(GenderFieldType.class))
            .declare(new EmailFieldType())
            .declare(new PhoneFieldType())
            .declare(new BirthDateFieldType())
            .declare(new SumPriceFieldType())
            .declare(new ProductSerialFieldType())
            .declare(new ProductPeriodFieldType())
            .declare(new ProductPaymentFieldType())
            .declare(new InsuranceAmountFieldType())
            .declare(new UnitFieldType())
            .declare(new PlanStartTimeFieldType())
            .declare(new IdentificationFieldType())
            .declare(new JobFieldType())
            .declare(bind(RelationFieldType.class))
            .declare(bind(BenefitTypeFieldType.class))
            .declare(new LiabilityFieldType())
            .declare(bind(PolicyTypeFieldType.class))
            .declare(new BooleanFieldType())
            .declare(bind(DeliverTypeFieldType.class))
            .declare(bind(MarriageTypeFieldType.class))
            .declare(bind(LegalBeneficiaryFieldType.class))
            .declare(new TravelDestFieldType())
            .declare(bind(NationalityFieldType.class))
            .declare(new AddressFieldType())
            .declare(bind(InvoiceDeliveryTypeFieldType.class))
            .declare(new EffectiveInsuranceFieldType())
            .declare(new PostalCodeFieldType())
            .declare(new AgreeFieldType());

        ProductFormService productFormService = require(ProductFormService.class);
        InsuranceFormGroupWebService insuranceFormGroupWebService = require(InsuranceFormGroupWebService.class);
        InsuranceFormFieldWebService insuranceFormFieldWebService = require(InsuranceFormFieldWebService.class);

        //被投保人
        FormGroupProvider insuredGroupProvider = new FormGroupProvider("insured", "被投保人")
            .register(new DefaultFieldProvider("insured", "policyCode", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new RelationFieldProvider("insured", "relation", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumPolicyRelationTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("insured", "name", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new GenderFieldProvider("insured", "gender", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumGenderTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("insured", "birthDate", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new NationalityFieldProvider("insured", "nationality", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(InsuranceCountryWebServiceClient.class)))
            .register(new IdFieldProvider("insured", "id", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumCertiTypeWebServiceClient.class)))
            .register(new MarriageTypeFieldProvider("insured", "marriageType", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumMarriageTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("insured", "workCompany", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "title", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new JobFieldProvider("insured", "job", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(InsuranceJobWebServiceClient.class)))
            .register(new DefaultFieldProvider("insured", "phone", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "telephone", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "email", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "address", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "postalCode", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new AgeFieldProvider("insured", "age", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "income", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "incomeSource", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "height", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("insured", "weight", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(insuredGroupProvider);

        //产品
        FormGroupProvider productGroupProvider = new ProductFormGroupProvider()
            .register(new UnitFieldProvider("product", "unit", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new ProductSerialFieldProvider(formConfig, require(ProductSerialService.class), require(DealerProductWebService.class), require(ProductService.class)));
        productFormService.register(productGroupProvider);


        //计划
        FormGroupProvider planGroupProvider = new FormGroupProvider("plan", "保障计划")
            .register(new PlanPeriodFieldProvider(formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new PlanPaymentFieldProvider(formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new PlanStartTimeFieldProvider(formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(planGroupProvider);

        //投保人
        FormGroupProvider policyHolderGroupProvider = new FormGroupProvider("policy-holder", "投保人")
            .register(new DefaultFieldProvider("policy-holder", "name", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new AgeFieldProvider("policy-holder", "age", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new GenderFieldProvider("policy-holder", "gender", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumGenderTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("policy-holder", "birthDate", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new IdFieldProvider("policy-holder", "id", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumCertiTypeWebServiceClient.class)))
            .register(new NationalityFieldProvider("policy-holder", "nationality", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(InsuranceCountryWebServiceClient.class)))
            .register(new MarriageTypeFieldProvider("policy-holder", "marriageType", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumMarriageTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("policy-holder", "workCompany", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "title", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new JobFieldProvider("policy-holder", "job", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(InsuranceJobWebServiceClient.class)))
            .register(new DefaultFieldProvider("policy-holder", "phone", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "telephone", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "email", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "address", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "postalCode", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "income", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "incomeSource", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "height", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("policy-holder", "weight", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(policyHolderGroupProvider);

        FormGroupProvider beneficiaryGroupProvider = new FormGroupProvider("beneficiary", "受益人")
            .register(new BenefitTypeFieldProvider("beneficiary", "type", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumBeneficiaryTypeWebServiceClient.class)))
            .register(new LegalBeneficiaryFieldProvider("beneficiary", "legalBeneficiary", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumLegalBeneficiaryTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("beneficiary", "name", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new GenderFieldProvider("beneficiary", "gender", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumGenderTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("beneficiary", "birthDate", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new IdFieldProvider("beneficiary", "id", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumCertiTypeWebServiceClient.class)))
            .register(new RelationFieldProvider("beneficiary", "relation", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumPolicyRelationTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("beneficiary", "beneficiaryRate", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(beneficiaryGroupProvider);

        //保单
        FormGroupProvider policyGroupProvider = new FormGroupProvider("policy", "投保信息")
            .register(new PolicyTypeFieldProvider("policy", "type", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumPolicyTypeWebService.class)))
            .register(new TravelDestFieldProvider("policy", "travelDest", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(InsuranceCountryWebServiceClient.class)))
            .register(new DeliverTypeFieldProvider("policy", "deliverType", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumDeliverTypeWebServiceClient.class)));
        productFormService.register(policyGroupProvider);

        //保障内容
        productFormService.register(new ProductLiabilityFormGroupProvider("liability", formConfig, require(ProductInsuranceLiabilityService.class)));

        //告知单
        FormGroupProvider informationGroupProvider = new FormGroupProvider("information", "告知单")
            .register(new DefaultFieldProvider("information", "informationQuestion1", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "informationQuestion2", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "highAccidentInsuranceQuestion1", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "highAccidentInsuranceQuestion2", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "highAccidentInsuranceQuestion3", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "highAccidentInsuranceQuestion4", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "highAccidentInsuranceQuestion5", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "highAccidentInsuranceQuestion6", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "normalAccidentInsuranceQuestion1", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "normalAccidentInsuranceQuestion2", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "normalAccidentInsuranceQuestion3", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "normalAccidentInsuranceQuestion4", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("information", "normalAccidentInsuranceQuestion5", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(informationGroupProvider);

        //其他信息(单证)
        FormGroupProvider othersGroupProvider = new FormGroupProvider("others", "单证信息")
            .register(new DefaultFieldProvider("others", "invoiceName", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("others", "invoiceDate", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new InvoiceDeliveryTypeFieldProvider("others", "invoiceDeliverType", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService, require(EnumInvoiceDeliverTypeWebServiceClient.class)))
            .register(new DefaultFieldProvider("others", "firstName", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("others", "mobile", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("others", "address", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("others", "postalCode", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("others", "premium", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(othersGroupProvider);

        //紧急联系人
        FormGroupProvider emergencyContactGroupProvider = new FormGroupProvider("emergency-contact", "紧急联系人")
            .register(new DefaultFieldProvider("emergency-contact", "name", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("emergency-contact", "phone", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("emergency-contact", "telephone", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("emergency-contact", "email", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(emergencyContactGroupProvider);

        //投保声明
        FormGroupProvider insuranceStatementGroupProvider = new FormGroupProvider("insurance-statement", "投保声明")
            .register(new AgreeFieldProvider("insurance-statement", "agree", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(insuranceStatementGroupProvider);

        //航班信息
        FormGroupProvider flightGroupProvider = new FormGroupProvider("flight", "航班信息")
            .register(new DefaultFieldProvider("flight", "flightNo", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService))
            .register(new DefaultFieldProvider("flight", "flightTime", formConfig, insuranceFormGroupWebService, insuranceFormFieldWebService));
        productFormService.register(flightGroupProvider);

        //自定义分组
        productFormService.register(new CustomFormGroupProvider(insuranceFormGroupWebService, insuranceFormFieldWebService, formConfig));
        configureArchive();
    }

    private void initProducts() {
        ProductService productService = require(ProductService.class);
        productService.installIfNotExists("db/product/富邦旅行险.json");
        productService.installIfNotExists("db/product/高额交通意外保险.json");
        productService.installIfNotExists("db/product/君行天下旅行保障计划A.json");
        productService.installIfNotExists("db/product/君行天下旅行保障计划B.json");
        productService.installIfNotExists("db/product/君行天下旅行保障计划C.json");
        productService.installIfNotExists("db/product/君行天下旅行保障计划D.json");
        productService.installIfNotExists("db/product/君龙境内旅行医疗救援保障计划一.json");
        productService.installIfNotExists("db/product/君龙境内旅行医疗救援保障计划二.json");
        productService.installIfNotExists("db/product/君龙境内旅行医疗救援保障计划三.json");
        productService.installIfNotExists("db/product/君龙境内旅行医疗救援保障计划四.json");
        productService.installIfNotExists("db/product/君龙境内旅行医疗救援保障计划五.json");
        productService.installIfNotExists("db/product/君龙境内旅行医疗救援保障计划六.json");
        productService.installIfNotExists("db/product/君龙普通综合意外险基础计划.json");
        productService.installIfNotExists("db/product/君龙普通综合意外险优选计划.json");
        productService.installIfNotExists("db/product/君龙普通综合意外险尊享计划.json");
        productService.installIfNotExists("db/product/君龙高额综合意外险精英计划.json");
        productService.installIfNotExists("db/product/君龙高额综合意外险白领计划.json");
        productService.installIfNotExists("db/product/君龙高额综合意外险金领计划.json");
        productService.installIfNotExists("db/product/君龙高额综合意外险至尊计划.json");
        productService.installIfNotExists("db/product/君龙单日航空意外保障计划一.json");
        productService.installIfNotExists("db/product/君龙单日航空意外保障计划二.json");
        productService.installIfNotExists("db/product/君龙单日航空意外保障计划三.json");
        productService.installIfNotExists("db/product/君龙7日航空意外保障计划一.json");
        productService.installIfNotExists("db/product/君龙7日航空意外保障计划二.json");
        productService.installIfNotExists("db/product/君龙7日航空意外保障计划三.json");
        productService.installIfNotExists("db/product/君龙爱无忧重大疾病保障计划.json");
    }

    private void initProductSerials() {
        MongoDatabase db = require(MongoConfig.class).db();
        new MongoInstaller<>(ProductSerial.class, db).installIfEmpty("db/product-serial/product-serial.json");
    }

    private CacheOptions cacheOption(String name) {
        CacheOptions cacheOption = new CacheOptions();
        return cacheOption;
    }

    private void configureArchive() {
        ArchiveService archiveService = bind(ArchiveService.class);
        archiveService.register(new AddressArchiveProvider())
            .register(new BooleanArchiveProvider())
            .register(new DeliverTypeArchiveProvider())
            .register(new GenderArchiveProvider())
            .register(new IdArchiveProvider())
            .register(new InvoiceDeliverTypeArchiveProvider())
            .register(bind(JobArchiveProvider.class))
            .register(new LegalBeneficiaryArchiveProvider())
            .register(new LiabilityArchiveProvider())
            .register(new MarriageTypeArchiveProvider())
            .register(new RelationArchiveProvider())
            .register(new LocalDateArchiveProvider())
            .register(new LocalDateTimeArchiveProvider())
            .register(new PlanStartTimeArchiveProvider())
            .register(new PlanPeriodArchiveProvider())
            .register(new BirthDateArchiveProvider())
            .register(new TravelDestArchiveProvider());
        export(ArchiveService.class);
    }

    private CacheOptions cacheOption(String name, Duration duration) {
        CacheOptions cacheOptions = new CacheOptions();
        cacheOptions.expireTime = duration;
        return cacheOptions;
    }
}
