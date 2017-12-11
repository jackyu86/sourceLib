package com.caej.cart.api.cart;

import java.util.Map;

/**
 * @author chi
 */
public class AddCartRequest {
    public String productId;
    public String productName;
    public String productDescription;
    public Map<String, Object> form;
    public CartTypeView type;
    public String requestBy;
}
