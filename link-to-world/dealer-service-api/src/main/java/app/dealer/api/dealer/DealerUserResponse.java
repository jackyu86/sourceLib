package app.dealer.api.dealer;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chi
 */
public class DealerUserResponse {
    public ObjectId id;
    public String dealerId;
    public String userId;
    public List<String> roles;
    public DealerUserStatusView status;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
