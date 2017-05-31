package io.sited.user.api.user;

/**
 * @author chi
 */
public class AuthenticationRequest {
    public String username;
    public String password;
    public String captchaCode;
    public Boolean autoLogin;
    public String requestBy;
}
