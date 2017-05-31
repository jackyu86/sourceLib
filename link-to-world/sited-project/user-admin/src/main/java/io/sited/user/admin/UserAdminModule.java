package io.sited.user.admin;


import com.google.common.collect.Lists;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.admin.AdminUser;
import io.sited.http.HttpConfig;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.I18nModule;
import io.sited.i18n.impl.service.PropertiesMessageRepository;
import io.sited.user.admin.controller.UserAdminController;
import io.sited.user.admin.controller.ajax.RoleAdminAJAXController;
import io.sited.user.admin.controller.ajax.UserAdminAJAXController;
import io.sited.user.admin.exception.AdminUserUnauthenticatedException;
import io.sited.user.admin.interceptor.UserAdminInterceptor;
import io.sited.user.admin.service.AdminUserProvider;
import io.sited.user.admin.service.AdminUserUnauthenticatedExceptionWriter;
import io.sited.user.api.UserModule;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UserResponse;

import java.util.Optional;

/**
 * @author chi
 */
@ModuleInfo(name = "user.admin", require = {UserModule.class, AdminModule.class, I18nModule.class})
public class UserAdminModule extends Module {
    @Override
    protected void configure() throws Exception {
        UserAdminOptions options = options(UserAdminOptions.class);
        bind(UserAdminOptions.class, options);

        HttpConfig httpConfig = require(HttpConfig.class);
        httpConfig.request()
            .bind(AdminUser.class, bind(AdminUserProvider.class));
        httpConfig
            .writer(AdminUserUnauthenticatedException.class, bind(AdminUserUnauthenticatedExceptionWriter.class));
        httpConfig.interceptor("/admin", bind(UserAdminInterceptor.class));

        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(RoleAdminAJAXController.class);
        adminConfig.controller(UserAdminAJAXController.class);
        adminConfig.controller(UserAdminController.class);


        if (options.defaultAdminEnabled) {
            UserWebService userWebService = require(UserWebService.class);
            Optional<UserResponse> user = userWebService.findByUsername("admin");
            if (!user.isPresent()) {
                CreateUserRequest createUserRequest = new CreateUserRequest();
                createUserRequest.username = "admin";
                createUserRequest.password = "admin";
                createUserRequest.roles = Lists.newArrayList("admin");
                createUserRequest.email = "admin@admin";
                userWebService.create(createUserRequest);
            }
        }

        require(I18nConfig.class)
            .add(new PropertiesMessageRepository("messages/user-admin_en-US.properties"))
            .add(new PropertiesMessageRepository("messages/user-admin_zh-CN.properties"));

        adminConfig
            .console().install("user", options.console);
    }
}