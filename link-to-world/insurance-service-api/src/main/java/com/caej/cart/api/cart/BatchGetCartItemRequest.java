package com.caej.cart.api.cart;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author miller
 */
public class BatchGetCartItemRequest {
    public List<ObjectId> carItemIds;
}
