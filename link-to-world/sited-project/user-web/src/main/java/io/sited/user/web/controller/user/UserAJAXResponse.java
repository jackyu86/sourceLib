package io.sited.user.web.controller.user;

import io.sited.user.api.user.UserStatusView;

import java.util.List;

/**
 * @author chi
 */
public class UserAJAXResponse {
    public String id;
    public String username;
    public String phone;
    public String fullName;
    public String imageURL;
    public List<String> roles;
    public UserStatusView status;
}
