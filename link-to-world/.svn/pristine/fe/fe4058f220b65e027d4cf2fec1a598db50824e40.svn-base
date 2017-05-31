package com.caej.cart;

import com.caej.api.KdlinsApiModule;
import com.caej.cart.api.CartModule;
import com.caej.cart.api.CartWebService;
import com.caej.cart.domain.Cart;
import com.caej.cart.domain.CartItem;
import com.caej.cart.service.CartService;
import com.caej.cart.web.CartWebServiceImpl;
import com.caej.insurance.api.InsuranceModule;

import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.scheduler.SchedulerModule;

/**
 * @author chi
 */
@ModuleInfo(name = "cart.api", require = {APIModule.class, DBModule.class, InsuranceModule.class, KdlinsApiModule.class,
    SchedulerModule.class})
public class CartModuleImpl extends CartModule {
    @Override
    protected void configure() throws Exception {
        require(MongoConfig.class)
            .entity(Cart.class)
            .entity(CartItem.class);

        bind(CartService.class);
        require(APIConfig.class).service(CartWebService.class, CartWebServiceImpl.class);
    }
}
