package com.caej.product.api.form;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chi
 */
public class BatchGetFormRequest {
    public List<CategoryProductId> productIds;

    public static class CategoryProductId {
        public ObjectId categoryId;
        public String productId;
    }
}
