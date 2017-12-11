package io.sited.user.api.role;

import java.util.List;

/**
 * @author chi
 */
public class CreateRoleRequest {
    public String name;
    public String displayName;
    public String description;
    public List<String> permissions;
    public String requestBy;
}
