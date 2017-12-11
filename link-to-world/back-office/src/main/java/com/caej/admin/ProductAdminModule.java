package com.caej.admin;

import com.caej.admin.area.InsuranceAreaAdminController;
import com.caej.admin.insurance.InsuranceAdminController;
import com.caej.admin.insurance.InsuranceClaimAdminController;
import com.caej.admin.insurancecategory.InsuranceCategoryAdminController;
import com.caej.admin.insuranceclause.InsuranceClauseAdminController;
import com.caej.admin.insuranceform.InsuranceFormFieldAdminController;
import com.caej.admin.insuranceform.InsuranceFormGroupAdminController;
import com.caej.admin.insurancejob.InsuranceJobAdminController;
import com.caej.admin.insuranceliability.InsuranceLiabilityAdminController;
import com.caej.admin.product.ProductAdminController;
import com.caej.admin.product.ProductDetailResponseClient;
import com.caej.admin.productserial.ProductSerialAdminController;
import com.caej.insurance.api.InsuranceModule;
import com.caej.product.api.ProductDetailWebService;
import com.caej.product.api.ProductModule;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.ProductAdminRequest;
import com.caej.product.api.product.ProductAdminResponse;
import com.caej.product.api.product.ProductDetailResponse;
import com.caej.product.api.product.ProductStatusType;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.admin.ConsoleOptions;
import io.sited.cache.Cache;
import io.sited.cache.CacheConfig;
import io.sited.cache.CacheModule;
import io.sited.cache.CacheOptions;
import io.sited.customer.api.CustomerModule;
import io.sited.db.FindView;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.impl.service.PropertiesMessageRepository;
import io.sited.user.api.UserModule;

/**
 * @author miller
 */
@ModuleInfo(name = "product.admin", require = {AdminModule.class, InsuranceModule.class, UserModule.class,
    CustomerModule.class, ProductModule.class, CacheModule.class})
public class ProductAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        CacheConfig cacheConfig = require(CacheConfig.class);
        Cache<ProductDetailResponse> cache = cacheConfig.create(ProductDetailResponse.class, new CacheOptions());
        bind(ProductDetailResponseClient.class);

        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(InsuranceClauseAdminController.class);
        adminConfig.controller(InsuranceCategoryAdminController.class);
        adminConfig.controller(InsuranceJobAdminController.class);
        adminConfig.controller(ProductAdminController.class);
        adminConfig.controller(ProductSerialAdminController.class);
        adminConfig.controller(InsuranceFormGroupAdminController.class);
        adminConfig.controller(InsuranceFormFieldAdminController.class);
        adminConfig.controller(InsuranceAdminController.class);
        adminConfig.controller(InsuranceLiabilityAdminController.class);
        adminConfig.controller(InsuranceAreaAdminController.class);
        adminConfig.controller(InsuranceClaimAdminController.class);
        adminConfig.controller(InsuranceLiabilityAdminController.class);
        adminConfig.console().install("insurance", new ConsoleOptions());
        adminConfig.console().install("product", new ConsoleOptions());
        require(I18nConfig.class)
            .add(new PropertiesMessageRepository("messages/product_zh-CN.properties"))
            .add(new PropertiesMessageRepository("messages/form_zh-CN.properties"));
        reloadProductCache(cache);
    }

    private void reloadProductCache(Cache<ProductDetailResponse> cache) {
        ProductAdminRequest productAdminRequest = new ProductAdminRequest();
        productAdminRequest.page = 1;
        productAdminRequest.limit = Integer.MAX_VALUE;
        productAdminRequest.latestVersion = true;
        ProductWebService productWebService = require(ProductWebService.class);
        ProductDetailWebService productDetailWebService = require(ProductDetailWebService.class);
        FindView<ProductAdminResponse> findView = productWebService.findAdmin(productAdminRequest);
        findView.forEach(productAdminResponse -> {
            if (ProductStatusType.ACTIVE.equals(productAdminResponse.status)) {
                ProductDetailResponse productDetailResponse = productDetailWebService.get(productAdminResponse.name);
                cache.put(productAdminResponse.name, productDetailResponse);
            } else {
                cache.invalidate(productAdminResponse.name);
            }
        });
    }
}
