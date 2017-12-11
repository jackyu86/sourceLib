package app.dealer.dealer.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
@Entity(name = "dealer")
public class Dealer {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "email")
    public String email;
    @Field(name = "contact_name")
    public String contactName;
    @Field(name = "contact_id_number")
    public String contactIdNumber;
    @Field(name = "cell_phone")
    public String cellphone;
    @Field(name = "state")
    public String state;
    @Field(name = "city")
    public String city;
    @Field(name = "ward")
    public String ward;
    @Field(name = "parent_dealer_id")
    public String parentDealerId;
    @Field(name = "level")
    public DealerLevel level;
    @Field(name = "status")
    public DealerStatus status;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "business_licence")
    public String businessLicence;
    @Field(name = "parent_dealer_ids")
    public List<String> parentDealerIds;
    @Field(name = "auto_allocate")
    public Boolean autoAllocate;
}
