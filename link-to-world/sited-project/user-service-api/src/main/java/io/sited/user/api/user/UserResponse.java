package io.sited.user.api.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class UserResponse {
    public String id;
    public String username;
    public String fullName;
    public String imageURL;
    public String email;
    public String phone;
    public List<String> roles;
    public Map<String, String> fields;
    public String provider;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
    public UserStatusView status;
}
