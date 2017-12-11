package app.dealer.policyholder.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author miller
 */
@Entity(name = "policy_holder")
public class PolicyHolder {
    @Id
    public ObjectId id;
    @Field(name = "dealer_id")
    public String dealerId;
    @Field(name = "name")
    public String name;
    @Field(name = "gender")
    public String gender;
    @Field(name = "birthDate")
    public LocalDate birthDate;
    @Field(name = "id_number")
    public String idNumber;
    @Field(name = "phone")
    public String phone;
    @Field(name = "email")
    public String email;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "create_time")
    public LocalDateTime createTime;
    @Field(name = "updated_by")
    public String updatedBy;
    @Field(name = "update_time")
    public LocalDateTime updateTime;
}
