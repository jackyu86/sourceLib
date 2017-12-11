package com.caej.site;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.caej.api.KdlinsApiModule;
import com.caej.cart.api.CartModule;
import com.caej.client.DealerProductWebServiceClient;
import com.caej.client.InsuranceLiabilityWebServiceClient;
import com.caej.client.InsuranceVendorWebServiceClient;
import com.caej.client.ProductDetailWebServiceClient;
import com.caej.client.ProductSearchWebServiceClient;
import com.caej.client.ProductWebServiceClient;
import com.caej.esb.api.ESBModule;
import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.InsuranceLiabilityWebService;
import com.caej.insurance.api.InsuranceModule;
import com.caej.insurance.api.category.BatchInsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryResponse;
import com.caej.insurance.api.insurance.InsuranceLiabilityResponse;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.order.OrderModule;
import com.caej.product.api.ProductModule;
import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.ProductDetailResponse;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.ProductSearchCategoryTreeResponse;
import com.caej.product.api.product.ProductSearchFirstLevelCategoryResponse;
import com.caej.site.about.web.AboutController;
import com.caej.site.cart.web.ajax.CartAJAXController;
import com.caej.site.checkout.CheckoutController;
import com.caej.site.checkout.PlaceOrderController;
import com.caej.site.checkout.ajax.CheckoutAJAXController;
import com.caej.site.checkout.ajax.PlaceOrderAJAXController;
import com.caej.site.checkout.ajax.UserAJAXController;
import com.caej.site.config.ESBConfig;
import com.caej.site.config.KdlinsConfig;
import com.caej.site.config.PayConfig;
import com.caej.site.config.UnderwritingConfig;
import com.caej.site.customer.service.CustomerService;
import com.caej.site.customer.web.CustomerController;
import com.caej.site.customer.web.DealerController;
import com.caej.site.customer.web.ajax.CustomerAJAXController;
import com.caej.site.customer.web.ajax.PolicyHolderAJAXController;
import com.caej.site.dealer.web.DealerProvider;
import com.caej.site.dealer.web.DealerUserController;
import com.caej.site.dealer.web.ajax.DealerAJAXController;
import com.caej.site.dealer.web.ajax.DealerProductAJAXController;
import com.caej.site.dealer.web.ajax.DealerUserAJAXController;
import com.caej.site.dealer.web.interceptor.DealerAJAXInterceptor;
import com.caej.site.dealer.web.interceptor.DealerPageInterceptor;
import com.caej.site.download.web.DownloadController;
import com.caej.site.esb.service.ESBService;
import com.caej.site.esb.web.ESBController;
import com.caej.site.esb.web.ajax.ESBAJAXController;
import com.caej.site.insurance.web.ajax.InsuranceCategoryAJAXController;
import com.caej.site.job.web.InsuranceJobAJAXController;
import com.caej.site.order.PayService;
import com.caej.site.order.ajax.OrderAJAXController;
import com.caej.site.order.ajax.OrderPayAJAXController;
import com.caej.site.order.web.OrderController;
import com.caej.site.page.service.DealerElementWriter;
import com.caej.site.page.service.DealerNodeProcessor;
import com.caej.site.page.service.JoinFilter;
import com.caej.site.page.service.LinkHrefAttributeWriter;
import com.caej.site.page.service.RoleElementWriter;
import com.caej.site.page.service.RoleNodeProcessor;
import com.caej.site.page.service.ScriptSrcAttributeWriter;
import com.caej.site.page.web.IndexController;
import com.caej.site.product.web.InsuranceNotificationController;
import com.caej.site.product.web.ProductControllerV2;
import com.caej.site.product.web.ProductSearchControllerV2;
import com.caej.site.product.web.ajax.ProductPriceAJAXController;
import com.caej.site.region.web.CountryAJAXController;
import com.caej.site.region.web.RegionAJAXController;
import com.caej.site.sms.queue.SMSPinCodeQueueHandler;
import com.caej.site.ticket.TicketAJAXController;
import com.caej.site.underwriting.web.ajax.UnderwritingAJAXController;
import com.caej.site.user.handler.EmailPinCodeQueueHandler;
import com.caej.site.user.service.UserService;
import com.caej.site.user.web.UserController;
import com.caej.site.validator.Phone;
import com.caej.site.validator.PhoneValidator;
import com.caej.ticket.TicketModule;
import com.caej.underwriting.api.UnderwritingModule;
import com.google.common.collect.Maps;

import app.dealer.api.DealerModule;
import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.product.DealerProductCategoryResponse;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.cache.Cache;
import io.sited.cache.CacheConfig;
import io.sited.cache.CacheModule;
import io.sited.cache.CacheOptions;
import io.sited.customer.api.CustomerModule;
import io.sited.email.api.EmailModule;
import io.sited.http.HttpConfig;
import io.sited.http.Request;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.I18nModule;
import io.sited.queue.QueueConfig;
import io.sited.template.TemplateConfig;
import io.sited.template.impl.ExpressionEngine;
import io.sited.user.api.UserModule;
import io.sited.user.web.User;
import io.sited.user.web.UserWebModule;
import io.sited.user.web.UserWebOptions;
import io.sited.user.web.message.CreatePinCodeEvent;
import io.sited.user.web.service.PinCodeService;
import io.sited.validator.ValidatorConfig;
import io.sited.web.AssetsConfig;
import io.sited.web.WebConfig;
import io.sited.web.WebModule;

/**
 * @author chi
 */
@ModuleInfo(name = "kdlins.web", require = {
        UserModule.class, EmailModule.class, I18nModule.class,
        ProductModule.class, InsuranceModule.class, UserWebModule.class, CartModule.class, TicketModule.class,
        CustomerModule.class, DealerModule.class, WebModule.class, KdlinsApiModule.class, OrderModule.class, ESBModule.class, CacheModule.class, UnderwritingModule.class})
public class KdlinsWebModule extends Module {
    @Override
    protected void configure() throws Exception {
        bindCacheClient();
        WebConfig webConfig = require(WebConfig.class);
        KdlinsConfig kdlinsConfig = options(KdlinsConfig.class);
        bind(PayConfig.class, kdlinsConfig.pay);
        bind(UnderwritingConfig.class, kdlinsConfig.underwriting);
        bind(ESBConfig.class, kdlinsConfig.esb);
        bind(AssetsConfig.class, webConfig.assets());
        bind(PayService.class);
        ESBService esbService = new ESBService(kdlinsConfig.esb);
        bind(ESBService.class, esbService);
        bind(UserService.class);
        bind(options(UserWebOptions.class));
        bind(PinCodeService.class);
        bind(CustomerService.class);

        webConfig.controller(AboutController.class);
        webConfig.controller(IndexController.class);
        webConfig.controller(CustomerController.class);
        webConfig.controller(UserController.class);
        webConfig.controller(ProductSearchControllerV2.class);
        webConfig.controller(ProductControllerV2.class);
        webConfig.controller(CheckoutController.class);
        webConfig.controller(PlaceOrderController.class);
        webConfig.controller(InsuranceNotificationController.class);
        webConfig.controller(ESBController.class);
        webConfig.controller(DealerController.class);
        webConfig.controller(DealerUserController.class);
        webConfig.controller(OrderController.class);
        webConfig.controller(OrderPayAJAXController.class);
        webConfig.controller(DownloadController.class);

        webConfig.controller(CustomerAJAXController.class);
        webConfig.controller(InsuranceJobAJAXController.class);
        webConfig.controller(RegionAJAXController.class);
        webConfig.controller(DealerAJAXController.class);
        webConfig.controller(DealerProductAJAXController.class);
        webConfig.controller(InsuranceCategoryAJAXController.class);
        webConfig.controller(CountryAJAXController.class);
        webConfig.controller(ProductPriceAJAXController.class);
        webConfig.controller(PolicyHolderAJAXController.class);
        webConfig.controller(DealerUserAJAXController.class);
        webConfig.controller(CheckoutAJAXController.class);
        webConfig.controller(UserAJAXController.class);
        webConfig.controller(PlaceOrderAJAXController.class);
        webConfig.controller(CartAJAXController.class);
        webConfig.controller(OrderAJAXController.class);
        webConfig.controller(TicketAJAXController.class);
        webConfig.controller(ESBAJAXController.class);
        webConfig.controller(UnderwritingAJAXController.class);

        QueueConfig queueConfig = require(QueueConfig.class);
        queueConfig.get(CreatePinCodeEvent.class)
                .addHandler(bind(EmailPinCodeQueueHandler.class))
                .addHandler(bind(SMSPinCodeQueueHandler.class));

        require(I18nConfig.class).add("messages/kdlins_zh-CN.properties");

        require(HttpConfig.class)
                .interceptor("/order", bind(DealerPageInterceptor.class))
                .interceptor("/checkout", bind(DealerPageInterceptor.class))
                .interceptor("/ajax/dealer", bind(DealerAJAXInterceptor.class));

        require(HttpConfig.class).request()
                .bind(DealerResponse.class, bind(DealerProvider.class));

        bindTemplate();
        bindErrorPage();

        require(ValidatorConfig.class)
                .bind(Phone.class, bind(PhoneValidator.class));
    }

    private void bindCacheClient() {
        CacheConfig cacheConfig = require(CacheConfig.class);
        Cache<ProductResponse> productResponseCache = cacheConfig.create(ProductResponse.class, cacheOption("ProductResponse"));
        Cache<InsuranceLiabilityResponse> insuranceLiabilityResponseCache = cacheConfig.create(InsuranceLiabilityResponse.class, cacheOption("InsuranceLiabilityResponse"));
        Cache<DealerProductCategoryResponse> dealerProductCategoryResponseCache = cacheConfig.create(DealerProductCategoryResponse.class, cacheOption("DealerProductCategoryResponse"));
        Cache<ProductSearchCategoryTreeResponse> productSearchCategoryTreeResponseCache = cacheConfig.create(ProductSearchCategoryTreeResponse.class, cacheOption("ProductSearchCategoryTreeResponse", Duration.ofHours(4)));
        Cache<ProductSearchFirstLevelCategoryResponse> productSearchFirstLevelCategoryResponseCache = cacheConfig.create(ProductSearchFirstLevelCategoryResponse.class, cacheOption("ProductSearchFirstLevelCategoryResponse", Duration.ofHours(4)));

        bind(ProductWebServiceClient.class, new ProductWebServiceClient(productResponseCache, require(ProductWebService.class)));
        bind(InsuranceLiabilityWebServiceClient.class, new InsuranceLiabilityWebServiceClient(require(InsuranceLiabilityWebService.class), insuranceLiabilityResponseCache));
        bind(DealerProductWebServiceClient.class, new DealerProductWebServiceClient(require(DealerProductWebService.class), dealerProductCategoryResponseCache));
        bind(ProductSearchWebServiceClient.class, new ProductSearchWebServiceClient(require(ProductSearchWebService.class), productSearchCategoryTreeResponseCache, productSearchFirstLevelCategoryResponseCache));

        cacheConfig.create(ProductDetailResponse.class, new CacheOptions());
        bind(ProductDetailWebServiceClient.class);

        cacheConfig.create(InsuranceVendorResponse.class, new CacheOptions());
        bind(InsuranceVendorWebServiceClient.class);
    }

    private void bindTemplate() {
        TemplateConfig templateConfig = require(TemplateConfig.class);
        templateConfig.setElementWriter("j:header", (element, context, writer) -> {
            context.include = true;
            Map<String, Object> original = context.bindings;
            Map<String, Object> bindings = Maps.newHashMap();
            bindings.putAll(original);
            Request request = (Request) context.bindings.get("_request");
            bindings.put("categories", headerCategories(request));

            User user = request.require(User.class, null);
            if (user != null) {
                bindings.put("user", user);
            }
            context.bindings = bindings;

            templateConfig.get("/include/header.html").output(context, writer);
            context.bindings = original;
            context.include = false;
        });

        templateConfig.setElementWriter("j:product-list-header", (element, context, writer) -> {
            context.include = true;
            Map<String, Object> original = context.bindings;
            Map<String, Object> bindings = Maps.newHashMap();

            Request request = (Request) context.bindings.get("_request");
            User user = request.require(User.class, null);
            if (user != null) {
                bindings.put("user", user);
            }

            bindings.putAll(original);
            bindings.put("categories", headerCategories(request));
            context.bindings = bindings;
            templateConfig.get("/include/product-list-header.html").output(context, writer);
            context.bindings = original;
            context.include = false;
        });

        templateConfig.addNodeProcessor(new RoleNodeProcessor());
        templateConfig.addNodeProcessor(new DealerNodeProcessor());
        templateConfig.setElementWriter("j:role", new RoleElementWriter());
        templateConfig.setElementWriter("j:dealer", new DealerElementWriter());
        templateConfig.setAttributeWriter("j:js", new ScriptSrcAttributeWriter(require(ExpressionEngine.class), site().build()));
        templateConfig.setAttributeWriter("j:css", new LinkHrefAttributeWriter(require(ExpressionEngine.class), site().build()));
        templateConfig.setFilter("join", new JoinFilter());
    }

    private void bindErrorPage() {
        HttpConfig httpConfig = require(HttpConfig.class);
        httpConfig.writer(NotFoundException.class, bind(NotFoundExceptionBodyWriter.class));
        httpConfig.writer(Throwable.class, bind(ThrowableBodyWriter.class));
        httpConfig.writer(UnauthorizedException.class, bind(UnauthorizedExceptionBodyWriter.class));
        httpConfig.writer(BadRequestException.class, bind(BadRequestExceptionResponseWriter.class));
    }

    private CacheOptions cacheOption(String name) {
        CacheOptions cacheOption = new CacheOptions();
        return cacheOption;
    }

    private CacheOptions cacheOption(String name, Duration duration) {
        CacheOptions cacheOptions = new CacheOptions();
        cacheOptions.expireTime = duration;
        return cacheOptions;
    }

    private List<InsuranceCategoryResponse> headerCategories(Request request) {
        Optional<DealerUserResponse> optional = dealerUser(request);
        if (optional.isPresent()) {
            List<ObjectId> categoryIds = require(DealerProductWebServiceClient.class).category(optional.get().dealerId).categoryIds;
            BatchInsuranceCategoryRequest batchInsuranceCategoryRequest = new BatchInsuranceCategoryRequest();
            batchInsuranceCategoryRequest.categoryIds = categoryIds;
            return require(InsuranceCategoryWebService.class).firstLevel(batchInsuranceCategoryRequest);
        } else {
            return require(ProductSearchWebServiceClient.class).firstLevelCategory().list;
        }
    }

    private Optional<DealerUserResponse> dealerUser(Request request) {
        DealerUserWebService dealerUserWebService = require(DealerUserWebService.class);
        try {
            User current = request.require(User.class);
            return dealerUserWebService.get(current.id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
