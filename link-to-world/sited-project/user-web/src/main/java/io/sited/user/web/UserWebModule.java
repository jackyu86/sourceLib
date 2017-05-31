package io.sited.user.web;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.http.HttpConfig;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.I18nModule;
import io.sited.queue.QueueConfig;
import io.sited.queue.QueueModule;
import io.sited.user.api.UserModule;
import io.sited.user.web.controller.CaptchaImageController;
import io.sited.user.web.controller.PinCodeAJAXController;
import io.sited.user.web.controller.UserAJAXController;
import io.sited.user.web.message.CreatePinCodeEvent;
import io.sited.user.web.service.CaptchaImageService;
import io.sited.user.web.service.PinCodeService;
import io.sited.user.web.service.UnauthenticatedExceptionWriter;
import io.sited.user.web.service.UserProvider;
import io.sited.user.web.validator.Password;
import io.sited.user.web.validator.PasswordValidator;
import io.sited.user.web.validator.Username;
import io.sited.user.web.validator.UsernameValidator;
import io.sited.validator.ValidatorConfig;
import io.sited.web.WebConfig;
import io.sited.web.WebModule;

/**
 * @author chi
 */
@ModuleInfo(name = "user.web", require = {WebModule.class, UserModule.class, QueueModule.class, I18nModule.class})
public class UserWebModule extends Module {
    @Override
    protected void configure() throws Exception {
        UserWebOptions options = options(UserWebOptions.class);
        bind(UserWebOptions.class, options);

        QueueConfig queueConfig = require(QueueConfig.class);
        queueConfig.create(CreatePinCodeEvent.class);

        require(HttpConfig.class)
            .writer(UnauthenticatedException.class, new UnauthenticatedExceptionWriter<>())
            .writer(UnauthorizedException.class, new UnauthenticatedExceptionWriter<>());

        bind(PinCodeService.class);
        bind(CaptchaImageService.class);
        WebConfig webConfig = require(WebConfig.class);

        require(HttpConfig.class).request()
            .bind(User.class, bind(UserProvider.class));

        webConfig.controller(UserAJAXController.class);
        webConfig.controller(CaptchaImageController.class);
        webConfig.controller(PinCodeAJAXController.class);

        require(ValidatorConfig.class)
            .bind(Username.class, bind(UsernameValidator.class))
            .bind(Password.class, bind(PasswordValidator.class));

        require(I18nConfig.class)
            .add("messages/user-web_en-US.properties")
            .add("messages/user-web_zh-CN.properties");
    }
}
