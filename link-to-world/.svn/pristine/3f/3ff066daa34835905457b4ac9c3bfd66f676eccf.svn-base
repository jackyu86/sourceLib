package io.sited.user.admin.service;

import io.sited.Provider;
import io.sited.http.Request;
import io.sited.admin.AdminUser;
import io.sited.user.admin.exception.AdminUserUnauthenticatedException;
import io.sited.util.JSON;

import java.util.Optional;

/**
 * @author chi
 */
public class AdminUserProvider implements Provider<AdminUser, Request> {
    public static final String USER_KEY = "__admin_user__";

    public static Optional<AdminUser> adminUser(Request request) {
        if (request.context().containsKey(USER_KEY)) {
            return Optional.of((AdminUser) request.context().get(USER_KEY));
        } else {
            Optional<String> user = request.session().get(USER_KEY);
            if (user.isPresent()) {
                AdminUser adminUser = JSON.fromJSON(user.get(), AdminUser.class);
                request.context().put(USER_KEY, adminUser);
                return Optional.of(adminUser);
            }
        }
        return Optional.empty();
    }

    @Override
    public AdminUser get(Request request) {
        Optional<AdminUser> adminUser = adminUser(request);
        if (!adminUser.isPresent()) {
            throw new AdminUserUnauthenticatedException("admin user not login");
        }
        return adminUser.get();
    }
}
