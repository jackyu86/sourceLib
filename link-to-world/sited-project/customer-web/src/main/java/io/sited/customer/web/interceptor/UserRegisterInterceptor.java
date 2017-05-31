package io.sited.customer.web.interceptor;

import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.http.Interceptor;
import io.sited.http.Invocation;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UserResponse;
import io.sited.user.web.controller.user.LoginAJAXResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author chi
 */
public class UserRegisterInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(UserRegisterInterceptor.class);

    @Inject
    CustomerWebService customerWebService;

    @Inject
    UserWebService userWebService;

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Object response = invocation.proceed();
        if (response instanceof Response) {
            LoginAJAXResponse loginAJAXResponse = ((Response) response).body();
            UserResponse userResponse = userWebService.get(loginAJAXResponse.userId);

            logger.info("create customer, id={}", userResponse.id);
            CreateCustomerRequest request = new CreateCustomerRequest();
            request.id = userResponse.id;
            request.channelId = channelId(invocation.request());
            request.campaignId = campaignId(invocation.request());
            request.source = source(invocation.request());
            customerWebService.create(request);
        }
        return response;
    }

    private String channelId(Request request) {
        Optional<String> cookie = request.cookie("channel-id");
        if (cookie.isPresent()) {
            return cookie.get().toLowerCase();
        }
        return null;
    }

    private String campaignId(Request request) {
        Optional<String> cookie = request.cookie("campaign-id");
        if (cookie.isPresent()) {
            return cookie.get().toLowerCase();
        }
        return null;
    }

    private String source(Request request) {
        Optional<String> cookie = request.cookie("source");
        if (cookie.isPresent()) {
            return cookie.get().toLowerCase();
        }
        return null;
    }
}
