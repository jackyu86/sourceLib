package app.dealer.credit.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

import java.time.LocalDateTime;

/**
 * @author Jonathan.Guo
 */
@Entity(name = "credit")
public class Credit {
    @Id(autoGenerated = false)
    public String id;
    @Field(name = "dealer_id")
    public String dealerId;
    @Field(name = "total_credits")
    public Double totalCredits;
    @Field(name = "quota")
    public Double quota;
    @Field(name = "status")
    public CreditStatus status;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
