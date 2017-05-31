package io.sited.user.admin.interceptor;

import io.sited.http.Interceptor;
import io.sited.http.Invocation;
import io.sited.http.Request;
import io.sited.http.Route;
import io.sited.http.exception.UnauthorizedException;
import io.sited.admin.AdminUser;

/**
 * @author chi
 */
public class UserAdminInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Request request = invocation.request();
        if (isProtected(request.path())) {
            AdminUser adminUser = request.require(AdminUser.class);
            Route route = (Route) request.context().get(Route.class);
            if (!adminUser.hasRole("admin") && !adminUser.hasPermission(route.permission)) {
                throw new UnauthorizedException("user has no permission, user={}, permission={}", adminUser.username, route.permission);
            }
        }
        return invocation.proceed();
    }

    private boolean isProtected(String path) {
        return path.startsWith("/admin")
                && !path.startsWith("/admin/static")
                && !path.startsWith("/admin/ajax/user/login")
                && !path.startsWith("/admin/login");
    }
}
