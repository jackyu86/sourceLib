package com.caej.cart.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author chi
 */
@Entity(name = "cart_item")
public class CartItem {
    @Id
    public ObjectId id;

    @Field(name = "cart_id")
    public String cartId;

    @Field(name = "product_id")
    public String productId;

    @Field(name = "product_name")
    public String productName;

    @Field(name = "product_description")
    public String productDescription;

    @Field(name = "form")
    public Map<String, Object> form;

    @Field(name = "created_time")
    public LocalDateTime createdTime;

    @Field(name = "created_by")
    public String createdBy;

    @Field(name = "updated_time")
    public LocalDateTime updatedTime;

    @Field(name = "updated_by")
    public String updatedBy;
}
