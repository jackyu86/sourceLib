package com.caej.cart.web;

import java.time.LocalDateTime;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.caej.SiteRule;
import com.caej.cart.CartModuleImpl;
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
import com.caej.cart.domain.CartType;
import com.caej.insurance.InsuranceModuleImpl;
import com.caej.product.ProductModuleImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;

import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;

/**
 * @author miller
 */
public class CartWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new ProductModuleImpl(), new InsuranceModuleImpl(), new CartModuleImpl());
    private Cart cart;
    private CartItem cartItem;
    private MongoCollection<Cart> collection;
    private MongoCollection<CartItem> itemCollection;
    private String cartId;
    private ObjectId carItemId;

    @Before
    public void before() throws Exception {
        cart = new Cart();
        cart.id = new ObjectId().toHexString();
        cart.type = CartType.ANONYMOUS;
        cart.createdBy = "test";
        cart.createdTime = LocalDateTime.now();
        cart.updatedBy = "test";
        cart.updatedTime = LocalDateTime.now();
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("cart", Cart.class);
        collection.insertOne(cart);
        cartId = cart.id;
        cartItem = new CartItem();
        cartItem.cartId = cartId;
        cartItem.productId = new ObjectId().toHexString();
        cartItem.productName = "test";
        cartItem.productDescription = "test";
        cartItem.form = Maps.newHashMap();
        cartItem.createdBy = "test";
        cartItem.updatedBy = "test";
        cartItem.createdTime = LocalDateTime.now();
        cartItem.updatedTime = LocalDateTime.now();
        itemCollection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("cart_item", CartItem.class);
        itemCollection.insertOne(cartItem);
        carItemId = cartItem.id;
    }

    @After
    public void after() throws Exception {
        collection.deleteOne(new Document("_id", cartId));
        itemCollection.deleteOne(new Document("_id", carItemId));
    }

    @Test
    public void add() throws Exception {
        String id = new ObjectId().toHexString();
        AddCartRequest request = new AddCartRequest();
        request.productId = new ObjectId().toHexString();
        request.productName = "new";
        request.productDescription = "new";
        request.form = Maps.newHashMap();
        request.type = CartTypeView.ANONYMOUS;
        request.requestBy = "test";
        ServerResponse response = site.post("/api/cart/" + id + "/item").body(request).execute();
        Assert.assertEquals(204, response.statusCode());
        Cart check = collection.find(new Document("_id", id)).first();
        Assert.assertEquals(CartType.ANONYMOUS, check.type);
        CartItem checkItem = itemCollection.find(new Document("cart_id", id)).first();
        Assert.assertEquals(request.productId, checkItem.productId);
    }

    @Test
    public void get() throws Exception {
        ServerResponse response = site.get("/api/cart/" + cartId).execute();
        Assert.assertEquals(200, response.statusCode());
        Optional<CartResponse> cartResponseOptional = response.body(Optional.class);
        Assert.assertTrue(cartResponseOptional.isPresent());
        CartResponse cartResponse = cartResponseOptional.get();
        Assert.assertEquals(cartResponse.id, cartId);
        Assert.assertEquals(cart.type.name(), cartResponse.type.name());
        Assert.assertFalse(cartResponse.items.isEmpty());
        CartResponse.CartItemView check = cartResponse.items.get(0);
        Assert.assertEquals(cartItem.productId, check.productId);
        Assert.assertEquals(cartItem.productName, check.productName);
    }

    @Test
    public void count() throws Exception {
        ServerResponse response = site.get("/api/cart/" + cartId + "/count").execute();
        Assert.assertEquals(200, response.statusCode());
        CartCountResponse cartCountResponse = response.body(CartCountResponse.class);
        Assert.assertEquals(1, cartCountResponse.count.intValue());
    }

    @Test
    public void merge() throws Exception {
        String toId = new ObjectId().toHexString();
        MergeCartRequest mergeCartRequest = new MergeCartRequest();
        mergeCartRequest.id = cartId;
        mergeCartRequest.requestBy = "test";
        ServerResponse response = site.post("/api/cart/" + toId + "/merge").body(mergeCartRequest).execute();
        Assert.assertEquals(204, response.statusCode());
        Cart toCart = collection.find(new Document("_id", toId)).first();
        Assert.assertEquals(CartType.CUSTOMER, toCart.type);
        CartItem toCartItem = itemCollection.find(new Document("cart_id", toId)).first();
        Assert.assertEquals(cartItem.productName, toCartItem.productName);
        Cart check = collection.find(new Document("_id", cartId)).first();
        Assert.assertNull(check);
        CartItem checkItem = itemCollection.find(new Document("id", carItemId)).first();
        Assert.assertNull(checkItem);
    }

    @Test
    public void delete() throws Exception {
        DeleteCartRequest deleteCartRequest = new DeleteCartRequest();
        deleteCartRequest.itemIds = Lists.newArrayList(carItemId);
        deleteCartRequest.requestedBy = "test";
        ServerResponse response = site.put("/api/cart/" + cartId + "/delete-item").body(deleteCartRequest).execute();
        Assert.assertEquals(204, response.statusCode());
        CartItem check = itemCollection.find(new Document("_id", carItemId)).first();
        Assert.assertNull(check);
    }

    @Test
    public void batchGet() throws Exception {
        BatchGetCartItemRequest batchGetCartItemRequest = new BatchGetCartItemRequest();
        batchGetCartItemRequest.carItemIds = Lists.newArrayList(carItemId);
        ServerResponse response = site.post("/api/cart/items").body(batchGetCartItemRequest).execute();
        Assert.assertEquals(200, response.statusCode());
        BatchGetCartItemResponse batchGetCartItemResponse = response.body(BatchGetCartItemResponse.class);
        Assert.assertEquals(1, batchGetCartItemResponse.cartItemViews.size());
        CartResponse.CartItemView cartItemView = batchGetCartItemResponse.cartItemViews.get(0);
        Assert.assertEquals(cartItem.productName, cartItemView.productName);
    }

}