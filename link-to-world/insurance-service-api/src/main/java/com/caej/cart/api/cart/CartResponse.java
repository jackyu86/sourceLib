package com.caej.cart.api.cart;

import org.bson.types.ObjectId;
import com.caej.product.api.price.ProductPriceResponse;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class CartResponse {
    public String id;
    public List<CartItemView> items;
    public CartTypeView type;

    public static class CartItemView {
        public ObjectId id;
        public String productId;
        public String productName;
        public String productDescription;
        public Map<String, Object> form;
        public ProductPriceResponse price;
    }
}
