package io.sited.admin;

import java.util.List;

/**
 * @author chi
 */
public class AdminUser {
    public String id;
    public String username;
    public String fullName;
    public List<String> roles;
    public List<String> permissions;

    public boolean hasPermission(String permission) {
        return permissions.stream().anyMatch(s -> s.equals(permission));
    }

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(s -> s.toUpperCase().contains(role.toUpperCase()));
    }
}
