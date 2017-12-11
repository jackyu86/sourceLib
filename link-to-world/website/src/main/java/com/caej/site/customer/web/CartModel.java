package com.caej.site.customer.web;

import com.caej.cart.api.cart.CartResponse;
import com.caej.product.api.product.ProductResponse;

/**
 * @author miller
 */
public class CartModel {
    public CartResponse.CartItemView item;
    public ProductResponse product;
}
