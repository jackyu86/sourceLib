package com.caej.product.api.amount;

import org.bson.types.ObjectId;

/**
 * @author chi
 */
public class ProductAmountRequest {
    public ObjectId userId;
    public ObjectId insuranceId;
    public ObjectId productId;
    public Double amount;
    public Double total;
    public String createdBy;
}
