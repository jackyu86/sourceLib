package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 险种
 *
 * @author chi
 */
@Entity(name = "insurance")
public class Insurance {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "master")
    public Boolean master;
    @Field(name = "max_amount")
    public Double maxAmount;
    @Field(name = "liabilities")
    public List<InsuranceLiabilityRef> liabilities;
    @Field(name = "price_table")
    public InsurancePriceTable priceTable;
    @Field(name = "price_table_url")
    public String priceTableURL;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
