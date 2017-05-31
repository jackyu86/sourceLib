package com.caej.order;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author chi
 */
@ModuleInfo(name = "order.api", require = {APIModule.class})
public class OrderModule extends Module {
    @Override
    protected void configure() throws Exception {
        OrderOptions options = options(OrderOptions.class);
        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(OrderWebService.class, options.url);
        apiConfig.client(OrderStatisticsWebService.class, options.url);
        apiConfig.client(PaymentWebService.class, options.url);
        apiConfig.client(RefundTrackingWebService.class, options.url);
        apiConfig.client(OrderArchiveRecordWebService.class, options.url);
    }
}
