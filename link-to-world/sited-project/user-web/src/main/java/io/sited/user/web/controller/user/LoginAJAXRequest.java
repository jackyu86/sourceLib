package io.sited.user.web.controller.user;

import io.sited.user.web.validator.Password;
import io.sited.user.web.validator.Username;

/**
 * @author chi
 */
public class LoginAJAXRequest {
    @Username
    public String username;
    @Password
    public String password;
    public String captchaCode;
    public Boolean autoLogin;
    public String requestBy;
}
