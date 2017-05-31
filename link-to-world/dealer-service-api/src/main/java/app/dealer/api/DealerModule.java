package app.dealer.api;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;

/**
 * @author miller
 */
@ModuleInfo(name = "dealer.api", require = APIModule.class)
public class DealerModule extends Module {
    @Override
    protected void configure() throws Exception {
        DealerOptions serviceOptions = options(DealerOptions.class);

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(DealerWebService.class, serviceOptions.url);
        apiConfig.client(CreditWebService.class, serviceOptions.url);
        apiConfig.client(CreditTrackingWebService.class, serviceOptions.url);
        apiConfig.client(DealerProductWebService.class, serviceOptions.url);
        apiConfig.client(DealerUserWebService.class, serviceOptions.url);
        apiConfig.client(PolicyHolderWebService.class, serviceOptions.url);
    }
}
