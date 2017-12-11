package io.sited.user.web;

import io.sited.user.api.user.UserStatusView;

import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class User {
    public String id;
    public String username;
    public String fullName;
    public String phone;
    public String email;
    public String imageURL;
    public List<String> roles;
    public String provider;
    public Map<String, String> fields;
    public UserStatusView status;

    public boolean hasRole(String roleName) {
        return roles != null && roles.contains(roleName);
    }
}
