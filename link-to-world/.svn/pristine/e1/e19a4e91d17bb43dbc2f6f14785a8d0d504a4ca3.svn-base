package com.caej.site.dealer.web;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.DealerResponse;
import io.sited.Provider;
import io.sited.http.Request;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.web.User;

import javax.inject.Inject;

/**
 * @author chi
 */
public class DealerProvider implements Provider<DealerResponse, Request> {
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    DealerWebService dealerWebService;

    @Override
    public void missing(Class<DealerResponse> type) {
        throw new UnauthenticatedException("current is not a dealer");
    }

    @Override
    public DealerResponse get(Request request) {
        if (request.context().containsKey(DealerResponse.class)) {
            return (DealerResponse) request.context().get(DealerResponse.class);
        }

        User user = request.require(User.class);
        if (!user.hasRole("dealerAdmin") && !user.hasRole("dealerUser")) {
            missing(DealerResponse.class);
        }
        String dealerId = dealerUserWebService.get(user.id).get().dealerId;
        DealerResponse dealerResponse = dealerWebService.get(dealerId).get();
        request.context().put(DealerResponse.class, dealerResponse);
        return dealerResponse;
    }

}
