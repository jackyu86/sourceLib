package app.dealer;

import app.dealer.api.CreditTrackingWebService;
import app.dealer.api.CreditWebService;
import app.dealer.api.DealerModule;
import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.PolicyHolderWebService;
import app.dealer.credit.domain.Credit;
import app.dealer.credit.domain.CreditTracking;
import app.dealer.credit.service.CreditService;
import app.dealer.credit.service.CreditTrackingService;
import app.dealer.credit.web.CreditTrackingWebServiceImpl;
import app.dealer.credit.web.CreditWebServiceImpl;
import app.dealer.dealer.domain.Dealer;
import app.dealer.dealer.domain.DealerTracking;
import app.dealer.dealer.domain.DealerUser;
import app.dealer.dealer.service.DealerService;
import app.dealer.dealer.service.DealerTrackingService;
import app.dealer.dealer.service.DealerUserService;
import app.dealer.dealer.web.DealerUserWebServiceImpl;
import app.dealer.dealer.web.DealerWebServiceImpl;
import app.dealer.policyholder.domain.PolicyHolder;
import app.dealer.policyholder.service.PolicyHolderService;
import app.dealer.policyholder.web.PolicyHolderWebServiceImpl;
import app.dealer.product.domain.DealerProduct;
import app.dealer.product.service.DealerProductService;
import app.dealer.product.web.DealerProductWebServiceImpl;
import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.JDBCConfig;
import io.sited.db.MongoConfig;

/**
 * @author miller
 */
@ModuleInfo(name = "dealer", require = {APIModule.class, DBModule.class})
public class DealerModuleImpl extends DealerModule {
    @Override
    protected void configure() throws Exception {
        require(MongoConfig.class)
            .entity(Dealer.class)
            .entity(DealerTracking.class)
            .entity(DealerProduct.class)
            .entity(DealerUser.class)
            .entity(PolicyHolder.class);

        require(JDBCConfig.class)
            .entity(Credit.class)
            .entity(CreditTracking.class);

        bind(DealerService.class);
        bind(DealerProductService.class);
        bind(DealerUserService.class);
        bind(CreditService.class);
        bind(CreditTrackingService.class);
        bind(DealerTrackingService.class);
        bind(PolicyHolderService.class);

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.service(DealerWebService.class, DealerWebServiceImpl.class);
        apiConfig.service(CreditWebService.class, CreditWebServiceImpl.class);
        apiConfig.service(CreditTrackingWebService.class, CreditTrackingWebServiceImpl.class);
        apiConfig.service(DealerProductWebService.class, DealerProductWebServiceImpl.class);
        apiConfig.service(DealerUserWebService.class, DealerUserWebServiceImpl.class);
        apiConfig.service(PolicyHolderWebService.class, PolicyHolderWebServiceImpl.class);
    }
}
