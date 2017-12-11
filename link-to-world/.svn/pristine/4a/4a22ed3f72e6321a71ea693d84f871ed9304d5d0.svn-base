package app.dealer.dealer.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chi
 */
@Entity(name = "dealer_user")
public class DealerUser {
    @Id
    public ObjectId id;
    @Field(name = "dealer_id")
    public String dealerId;
    @Field(name = "user_id")
    public String userId;
    @Field(name = "salt")
    public String salt;
    @Field(name = "iteration")
    public Integer iteration;
    @Field(name = "hashed_pay_password")
    public String hashedPayPassword;
    @Field(name = "roles")
    public List<String> roles;
    @Field(name = "status")
    public DealerUserStatus status;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "channel_id")
    public String channelId;
    @Field(name = "source")
    public String source;
}
