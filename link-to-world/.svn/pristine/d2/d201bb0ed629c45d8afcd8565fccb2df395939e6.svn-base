package com.caej.admin;

import com.caej.admin.vendor.InsuranceVendorWebServiceAdminClient;
import com.caej.admin.vendor.VendorAdminController;
import com.caej.insurance.api.InsuranceModule;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.product.api.ProductModule;

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
import io.sited.user.api.UserModule;

/**
 * @author chi
 */
@ModuleInfo(name = "vendor.admin", require = {AdminModule.class, InsuranceModule.class, UserModule.class,
    CustomerModule.class, ProductModule.class, CacheModule.class})
public class VendorAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(VendorAdminController.class);

        adminConfig.console().install("vendor", new ConsoleOptions());

        Cache<InsuranceVendorResponse> vendorResponseCache = require(CacheConfig.class).create(InsuranceVendorResponse.class, cacheOption("InsuranceVendorResponse"));
        bind(InsuranceVendorWebServiceAdminClient.class, new InsuranceVendorWebServiceAdminClient(vendorResponseCache, require(InsuranceVendorWebService.class)));

    }

    private CacheOptions cacheOption(String name) {
        CacheOptions cacheOption = new CacheOptions();
        return cacheOption;
    }
}
