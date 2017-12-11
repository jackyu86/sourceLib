package app.dealer.dealer.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author Jonathan.Guo
 */
@Entity(name = "dealer_tracking")
public class DealerTracking {
    @Id
    public ObjectId id;
    @Field(name = "distribution_id")
    public ObjectId distributionId;
    @Field(name = "operator_id")
    public ObjectId operatorId;
    @Field(name = "change_status")
    public DealerUserStatus changeStatus;
    @Field(name = "operation_time")
    public LocalDateTime operationTime;
}
