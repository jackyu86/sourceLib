package io.sited.user.web.controller.ajax;

import com.google.common.collect.Lists;
import io.sited.http.ServerResponse;
import io.sited.user.UserModuleImpl;
import io.sited.user.api.UserModule;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UserStatusView;
import io.sited.user.web.SiteRule;
import io.sited.user.web.UserWebModule;
import io.sited.user.web.controller.user.ResetPasswordAJAXRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author chi
 */
public class UserAJAXControllerTest {
    @Rule
    public SiteRule siteRule = new SiteRule(new UserWebModule(), new UserModuleImpl());

    @Before
    public void setup() {
        UserWebService userWebService = siteRule.module(UserModule.class).require(UserWebService.class);
        CreateUserRequest request = new CreateUserRequest();
        request.username = "some@test.com";
        request.password = "some";
        request.status = UserStatusView.ACTIVE;
        request.roles = Lists.newArrayList();
        userWebService.create(request);
    }

    @Test
    public void resetPassword() throws Exception {
        ResetPasswordAJAXRequest resetPasswordAJAXRequest = new ResetPasswordAJAXRequest();
        resetPasswordAJAXRequest.username = "some@test.com";
        resetPasswordAJAXRequest.captchaCode = "";

        ServerResponse response = siteRule.post("/ajax/user/reset-password").body(resetPasswordAJAXRequest).execute();
        Assert.assertEquals(204, response.statusCode());
    }
}