package io.sited.user.web.controller.user;

import io.sited.user.web.validator.Password;
import io.sited.user.web.validator.Username;

import java.util.Map;

/**
 * @author chi
 */
public class RegisterAJAXRequest {
    public String fullName;
    @Username
    public String username;
    @Password
    public String password;
    public String email;
    public String phone;
    public Map<String, String> fields;
    public String pinCode;
    public String requestBy;
}
