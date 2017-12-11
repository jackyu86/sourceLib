package io.sited.user.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "user_auto_login_token")
public class UserAutoLoginToken {
    @Id(autoGenerated = false)
    public String id;
    @Field(name = "user_id")
    public String userId;
    @Field(name = "token")
    public String token;
    @Field(name = "expired_time")
    public LocalDateTime expiredTime;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}