package io.sited.user;

import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.JDBCConfig;
import io.sited.db.MongoConfig;
import io.sited.user.api.PinCodeWebService;
import io.sited.user.api.RoleWebService;
import io.sited.user.api.UserModule;
import io.sited.user.api.UserWebService;
import io.sited.user.domain.ResetPasswordToken;
import io.sited.user.domain.Role;
import io.sited.user.domain.User;
import io.sited.user.domain.UserAutoLoginToken;
import io.sited.user.domain.UserPinCodeTracking;
import io.sited.user.service.AutoLoginTokenService;
import io.sited.user.service.PinCodeProvider;
import io.sited.user.service.PinCodeService;
import io.sited.user.service.ResetPasswordTokenService;
import io.sited.user.service.RoleService;
import io.sited.user.service.UserService;
import io.sited.user.service.impl.JDBCAutoLoginTokenServiceImpl;
import io.sited.user.service.impl.JDBCPinCodeServiceImpl;
import io.sited.user.service.impl.JDBCResetPasswordTokenServiceImpl;
import io.sited.user.service.impl.JDBCRoleServiceImpl;
import io.sited.user.service.impl.JDBCUserServiceImpl;
import io.sited.user.service.impl.MongoAutoLoginTokenServiceImpl;
import io.sited.user.service.impl.MongoPinCodeServiceImpl;
import io.sited.user.service.impl.MongoResetPasswordTokenServiceImpl;
import io.sited.user.service.impl.MongoRoleServiceImpl;
import io.sited.user.service.impl.MongoUserServiceImpl;
import io.sited.user.web.PinCodeWebServiceImpl;
import io.sited.user.web.RoleWebServiceImpl;
import io.sited.user.web.UserWebServiceImpl;

/**
 * @author chi
 */
@ModuleInfo(name = "user.api", require = {APIModule.class, DBModule.class})
public class UserModuleImpl extends UserModule {
    @Override
    protected void configure() throws Exception {
        UserOptions options = options(UserOptions.class);
        bind(UserOptions.class, options);
        bind(PinCodeProvider.class);

        if ("jdbc".equals(options.db)) {
            require(JDBCConfig.class)
                .entity(UserPinCodeTracking.class)
                .entity(Role.class)
                .entity(ResetPasswordToken.class)
                .entity(UserAutoLoginToken.class)
                .entity(User.class);

            bind(UserService.class, bind(JDBCUserServiceImpl.class));
            bind(RoleService.class, bind(JDBCRoleServiceImpl.class));
            bind(ResetPasswordTokenService.class, bind(JDBCResetPasswordTokenServiceImpl.class));
            bind(PinCodeService.class, bind(JDBCPinCodeServiceImpl.class));
            bind(AutoLoginTokenService.class, bind(JDBCAutoLoginTokenServiceImpl.class));
        } else if ("mongo".equals(options.db)) {
            require(MongoConfig.class)
                .entity(UserPinCodeTracking.class)
                .entity(Role.class)
                .entity(ResetPasswordToken.class)
                .entity(UserAutoLoginToken.class)
                .entity(User.class);


            bind(UserService.class, bind(MongoUserServiceImpl.class));
            bind(RoleService.class, bind(MongoRoleServiceImpl.class));
            bind(ResetPasswordTokenService.class, bind(MongoResetPasswordTokenServiceImpl.class));
            bind(PinCodeService.class, bind(MongoPinCodeServiceImpl.class));
            bind(AutoLoginTokenService.class, bind(MongoAutoLoginTokenServiceImpl.class));
        } else {
            throw new StandardException("invalid user options, db={}", options.db);
        }

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.service(UserWebService.class, UserWebServiceImpl.class);
        apiConfig.service(RoleWebService.class, RoleWebServiceImpl.class);
        apiConfig.service(PinCodeWebService.class, PinCodeWebServiceImpl.class);
    }
}
