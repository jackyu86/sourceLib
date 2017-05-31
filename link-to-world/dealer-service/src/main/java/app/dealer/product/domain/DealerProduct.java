package app.dealer.product.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
@Entity(name = "dealer_product")
public class DealerProduct {
    @Id
    public ObjectId id;
    @Field(name = "dealer_id")
    public String dealerId;
    @Field(name = "product_name")
    public String productName;
    @Field(name = "insurance_category_ids")
    public List<ObjectId> insuranceCategoryIds;
    @Field(name = "surrender_mark")
    public Boolean surrenderMark;
    @Field(name = "bank_account_mark")
    public Boolean bankAccountMark;
    @Field(name = "commission_rate")
    public Integer commissionRate;

}
