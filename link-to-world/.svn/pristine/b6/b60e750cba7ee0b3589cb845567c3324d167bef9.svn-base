package com.caej.cart.service;

import static java.time.LocalDateTime.now;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.cart.api.cart.AddCartRequest;
import com.caej.cart.api.cart.DeleteCartRequest;
import com.caej.cart.api.cart.MergeCartRequest;
import com.caej.cart.domain.Cart;
import com.caej.cart.domain.CartItem;
import com.caej.cart.domain.CartType;
import com.google.common.collect.Lists;

import io.sited.db.MongoRepository;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class CartService {
    @Inject
    MongoRepository<Cart> cartRepository;

    @Inject
    MongoRepository<CartItem> cartItemRepository;

    public void add(String id, AddCartRequest request) {
        Optional<Cart> cartOptional = findCartById(id);
        if (cartOptional.isPresent()) {
            createCartItem(id, request);
            updateCart(cartOptional.get(), request.requestBy);
        } else {
            createCartItem(id, request);
            createCart(id, CartType.valueOf(request.type.name()), request.requestBy);
        }
    }

    public Optional<Cart> findCartById(String id) {
        return cartRepository.query(new Document("_id", id)).findOne();
    }

    private void createCartItem(String id, AddCartRequest request) {
        CartItem cartItem = new CartItem();
        cartItem.cartId = id;
        cartItem.productId = request.productId;
        cartItem.productName = request.productName;
        cartItem.productDescription = request.productDescription;
        cartItem.form = request.form;
        cartItem.createdTime = now();
        cartItem.createdBy = request.requestBy;
        cartItem.updatedTime = now();
        cartItem.updatedBy = request.requestBy;
        cartItemRepository.insert(cartItem);
    }

    private void updateCart(Cart cart, String requestBy) {
        cart.updatedTime = now();
        cart.updatedBy = requestBy;
        cartRepository.update(cart.id, cart);
    }

    private void createCart(String id, CartType type, String requestBy) {
        Cart cart = new Cart();
        cart.id = id;
        cart.type = type;
        cart.createdBy = requestBy;
        cart.createdTime = now();
        cart.updatedBy = requestBy;
        cart.updatedTime = now();
        cartRepository.insert(cart);
    }

    public Integer itemCount(String id) {
        return (int) cartItemRepository.query(new Document("cart_id", id)).count();
    }

    public void merge(String id, MergeCartRequest request) {
        Optional<Cart> fromCartOptional = findCartById(request.id);
        if (!fromCartOptional.isPresent()) {
            return;
        }

        Cart fromCart = fromCartOptional.get();
        if (fromCart.type != CartType.ANONYMOUS) {
            throw new BadRequestException("type", null, "invalid merge from cart, expected type to be ANONYMOUS");
        }

        List<CartItem> fromCartItems = findCartItemsByCartId(fromCart.id);
        if (fromCartItems.isEmpty()) {
            return;
        }

        Optional<Cart> toCartOptional = findCartById(id);
        if (toCartOptional.isPresent()) {
            copyCartItems(fromCartItems, id, findCartItemsByCartId(id), request.requestBy);
            updateCart(toCartOptional.get(), request.requestBy);
        } else {
            createCart(id, CartType.CUSTOMER, request.requestBy);
            copyCartItems(fromCartItems, id, Lists.newArrayList(), request.requestBy);
        }
        deleteCart(fromCart.id);
        deleteCartItems(fromCart.id);
    }

    public List<CartItem> findCartItemsByCartId(String id) {
        return cartItemRepository.query(new Document("cart_id", id)).findMany();
    }

    private void copyCartItems(List<CartItem> fromCartItems, String toCartId, List<CartItem> toCartItems, String requestBy) {
        List<CartItem> toCreateCartItems = Lists.newArrayList();

        fromCartItems.forEach(cartItem -> {
            CartItem item = new CartItem();
            item.cartId = toCartId;
            item.productId = cartItem.productId;
            item.productName = cartItem.productName;
            item.productDescription = cartItem.productDescription;
            item.form = cartItem.form;
            item.createdTime = cartItem.createdTime;
            item.createdBy = cartItem.createdBy;
            item.updatedTime = cartItem.updatedTime;
            item.updatedBy = cartItem.updatedBy;
            toCreateCartItems.add(item);
        });

        if (!toCreateCartItems.isEmpty()) {
            cartItemRepository.batchInsert(toCreateCartItems);
        }
    }

    private void deleteCart(String id) {
        cartRepository.delete(id);
    }

    private void deleteCartItems(String id) {
        cartItemRepository.batchDelete(findCartItemsByCartId(id).stream().map(cartItem -> cartItem.id).collect(Collectors.toList()));
    }

    public void delete(String id, DeleteCartRequest request) {
        Cart cart = findCartById(id).orElseThrow(() -> new NotFoundException("cart not found, id={}", id));
        cartItemRepository.query(new Document("cart_id", id).append("_id", new Document("$in", request.itemIds))).delete();
        updateCart(cart, request.requestedBy);
    }

    private String skuId(String vendorNumber, String parentSku, String sku) {
        return vendorNumber + '-' + parentSku + '-' + sku;
    }

    public List<CartItem> batchGet(List<ObjectId> ids) {
        return cartItemRepository.query(new Document("_id", new Document("$in", ids))).findMany();
    }
}
