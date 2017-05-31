package com.caej.site.dealer.web.interceptor;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.dealer.DealerUserStatusView;
import io.sited.http.Interceptor;
import io.sited.http.Invocation;
import io.sited.http.Request;
import io.sited.http.exception.UnauthorizedException;
import io.sited.user.web.User;

import javax.inject.Inject;
import java.util.Optional;

import static io.sited.user.web.service.UserProvider.SESSION_USER_ID;

/**
 * @author chi
 */
public class DealerPageInterceptor implements Interceptor {
    @Inject
    DealerUserWebService dealerUserWebService;

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Request request = invocation.request();
        Optional<String> userId = request.session().get(SESSION_USER_ID);
        if (userId.isPresent()) {
            User user = request.require(User.class);
            Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(user.id);
            if (dealerUser.isPresent() && !dealerUser.get().status.equals(DealerUserStatusView.ACTIVE)) {
                throw new UnauthorizedException("user don't have permission to view page, user={}, page={}", user.id, request.url());
            }
        }
        return invocation.proceed();
    }
}
