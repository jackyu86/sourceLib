package com.caej.site.cart.web.ajax;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.cart.api.CartWebService;
import com.caej.cart.api.cart.AddCartRequest;
import com.caej.cart.api.cart.CartCountResponse;
import com.caej.cart.api.cart.CartResponse;
import com.caej.cart.api.cart.CartTypeView;
import com.caej.cart.api.cart.DeleteCartRequest;

import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.user.web.User;

/**
 * @author miller
 */
public class CartAJAXController {
    @Inject
    CartWebService cartWebService;

    @Path("/ajax/cart")
    @POST
    public Response add(Request request) throws IOException {
        AddCartRequest addCartRequest = request.body(AddCartRequest.class);
        User user = request.require(User.class, null);
        String cartId;
        if (user == null) {
            addCartRequest.type = CartTypeView.ANONYMOUS;
            cartId = request.client().id();
            addCartRequest.requestBy = "kdlins-web";
        } else {
            addCartRequest.type = CartTypeView.CUSTOMER;
            cartId = user.id;
            addCartRequest.requestBy = "kdlins-web";
        }
        cartWebService.add(cartId, addCartRequest);
        return Response.empty();
    }

    @Path("/ajax/cart")
    @GET
    public Response get(Request request) throws IOException {
        String id = cartId(request);
        Optional<CartResponse> cartResponse = cartWebService.get(id);
        return cartResponse.isPresent() ? Response.body(cartResponse.get()) : Response.empty();
    }

    @Path("/ajax/cart/count")
    @GET
    public Response count(Request request) throws IOException {
        String id = cartId(request);
        CartCountResponse cartCountResponse = cartWebService.count(id);
        return Response.body(cartCountResponse);
    }

    @Path("/ajax/cart/delete-item")
    @PUT
    public Response delete(Request request) throws IOException {
        String id = cartId(request);
        DeleteCartRequest deleteCartRequest = request.body(DeleteCartRequest.class);
        deleteCartRequest.requestedBy = id;
        cartWebService.delete(id, deleteCartRequest);
        return Response.empty();
    }

    private String cartId(Request request) {
        User user = request.require(User.class, null);
        if (user == null) {
            return request.client().id();
        } else {
            return user.id;
        }
    }
}
