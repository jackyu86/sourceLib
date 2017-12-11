package com.caej.cart.web;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.cart.api.CartWebService;
import com.caej.cart.api.cart.AddCartRequest;
import com.caej.cart.api.cart.BatchGetCartItemRequest;
import com.caej.cart.api.cart.BatchGetCartItemResponse;
import com.caej.cart.api.cart.CartCountResponse;
import com.caej.cart.api.cart.CartResponse;
import com.caej.cart.api.cart.CartTypeView;
import com.caej.cart.api.cart.DeleteCartRequest;
import com.caej.cart.api.cart.MergeCartRequest;
import com.caej.cart.domain.Cart;
import com.caej.cart.domain.CartItem;
import com.caej.cart.service.CartService;
import com.google.common.collect.Lists;


/**
 * @author chi
 */
public class CartWebServiceImpl implements CartWebService {
    @Inject
    CartService cartService;

    @Override
    public void add(String id, AddCartRequest request) {
        cartService.add(id, request);
    }

    @Override
    public Optional<CartResponse> get(String id) {
        Optional<Cart> cart = cartService.findCartById(id);
        if (cart.isPresent()) {
            return Optional.of(response(cart.get(), cartService.findCartItemsByCartId(cart.get().id)));
        }
        return Optional.empty();
    }

    @Override
    public CartCountResponse count(String id) {
        CartCountResponse response = new CartCountResponse();
        response.count = cartService.itemCount(id);
        return response;
    }

    @Override
    public void merge(String id, MergeCartRequest request) {
        cartService.merge(id, request);
    }

    @Override
    public void delete(String id, DeleteCartRequest request) {
        cartService.delete(id, request);
    }

    @Override
    public BatchGetCartItemResponse batchGet(BatchGetCartItemRequest batchGetCartItemRequest) {
        BatchGetCartItemResponse response = new BatchGetCartItemResponse();
        response.cartItemViews = Lists.newArrayList();
        cartService.batchGet(batchGetCartItemRequest.carItemIds).forEach(cartItem -> {
            response.cartItemViews.add(view(cartItem));
        });
        return response;
    }

    private CartResponse.CartItemView view(CartItem item) {
        CartResponse.CartItemView view = new CartResponse.CartItemView();
        view.id = item.id;
        view.productId = item.productId;
        view.form = item.form;
        view.productName = item.productName;
        view.productDescription = item.productDescription;
        return view;
    }

    private CartResponse response(Cart cart, List<CartItem> items) {
        CartResponse response = new CartResponse();
        response.id = cart.id;
        response.type = CartTypeView.valueOf(cart.type.name());
        response.items = items.stream().map(this::view).collect(Collectors.toList());
        return response;
    }
}
