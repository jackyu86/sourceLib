package io.sited.user.web.service;

import io.sited.Provider;
import io.sited.http.Request;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.AuthenticationResponse;
import io.sited.user.api.user.TokenAuthenticationRequest;
import io.sited.user.api.user.UserResponse;
import io.sited.user.web.User;
import io.sited.user.web.UserWebOptions;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.CookieImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author chi
 */
public class UserProvider implements Provider<User, Request> {
    private final Logger logger = LoggerFactory.getLogger(UserProvider.class);

    public static final String SESSION_USER_ID = "_user_id";

    @Inject
    UserWebService userWebService;

    @Inject
    UserWebOptions userWebOptions;

    @Override
    public void missing(Class<User> type) {
        throw new UnauthenticatedException("login required");
    }

    @Override
    public User get(Request request) {
        if (request.context().containsKey(User.class)) {
            return (User) request.context().get(User.class);
        }

        Optional<String> id = request.session().get(SESSION_USER_ID);
        if (id.isPresent()) {
            User user = user(userWebService.get(id.get()));
            request.context().put(User.class, user);
            return user;
        }

        if (userWebOptions.autoLoginEnabled) {
            Optional<String> cookie = request.cookie(userWebOptions.autoLoginCookie);
            if (cookie.isPresent()) {
                try {
                    TokenAuthenticationRequest authenticationRequest = new TokenAuthenticationRequest();
                    authenticationRequest.token = cookie.get();
                    AuthenticationResponse authenticationResponse = userWebService.authenticate(authenticationRequest);
                    User user = user(authenticationResponse.user);
                    request.session().set(SESSION_USER_ID, user.id);
                    return user;
                } catch (Throwable e) {
                    logger.warn("invalid auto login token cookie, value={}", cookie.get());
                    HttpServerExchange exchange = request.require(HttpServerExchange.class, null);
                    CookieImpl toRemoveCookie = new CookieImpl(userWebOptions.autoLoginCookie, null);
                    toRemoveCookie.setMaxAge(0);
                    toRemoveCookie.setPath("/");
                    exchange.getResponseCookies().put(userWebOptions.autoLoginCookie, toRemoveCookie);
                }
            }

        }
        return null;
    }

    private User user(UserResponse userResponse) {
        User user = new User();
        user.id = userResponse.id;
        user.username = userResponse.username;
        user.fullName = userResponse.fullName;
        user.phone = userResponse.phone;
        user.email = userResponse.email;
        user.imageURL = userResponse.imageURL;
        user.fields = userResponse.fields;
        user.roles = userResponse.roles;
        user.provider = userResponse.provider;
        user.status = userResponse.status;
        return user;
    }
}
