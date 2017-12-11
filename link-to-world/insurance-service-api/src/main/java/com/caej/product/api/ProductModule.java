package com.caej.product.api;

import com.caej.insurance.api.InsuranceModule;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author chi
 */
@ModuleInfo(name = "product.api", require = {APIModule.class, InsuranceModule.class})
public class ProductModule extends Module {
    @Override
    protected void configure() throws Exception {
        ProductOptions options = options(ProductOptions.class);
        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(ProductFormWebService.class, options.url);
        apiConfig.client(ProductPriceWebService.class, options.url);
        apiConfig.client(ProductSerialWebService.class, options.url);
        apiConfig.client(ProductSearchWebService.class, options.url);
        apiConfig.client(ProductWebService.class, options.url);
        apiConfig.client(ProductConvertWebService.class, options.url);
        apiConfig.client(ArchiveWebService.class, options.url);
        apiConfig.client(ProductDetailWebService.class, options.url);
    }
}
