package io.sited.user.web;

/**
 * @author chi
 */
public class UserWebOptions {
    public Boolean pinCodeEnabled = true;
    public Boolean captchaCodeEnabled = true;

    public Boolean registerAutoLoginEnabled = true;
    public Boolean autoLoginEnabled = true;
    public Integer autoLoginMaxAge = Integer.MAX_VALUE;
    public String autoLoginCookie = "_login_token";
}
