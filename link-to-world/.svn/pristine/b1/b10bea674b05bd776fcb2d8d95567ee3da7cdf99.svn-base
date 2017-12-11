package com.caej.site.checkout.ajax;

import static com.caej.order.order.OrderStatusView.DRAFT;
import static com.caej.order.order.OrderStatusView.PAYMENT_PENDING;
import static io.sited.user.web.service.UserProvider.SESSION_USER_ID;

import java.util.Map;

import javax.inject.Inject;

import com.caej.order.OrderWebService;
import com.caej.order.order.OrderView;
import com.caej.product.api.ProductFormWebService;
import com.caej.site.checkout.CheckoutSessionRequest;
import com.caej.site.user.service.UserService;

import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.exception.UnauthorizedException;
import io.sited.user.api.user.UserResponse;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class PlaceOrderAJAXController {
    @Inject
    ProductFormWebService productFormWebService;
    @Inject
    UserService userService;
    @Inject
    OrderWebService orderWebService;

    @Path("/ajax/place-order")
    @POST
    public void checkout(Request request) {
        CheckoutSessionRequest checkoutSessionRequest = request.body(CheckoutSessionRequest.class);

        OrderView orderView = orderWebService.get(checkoutSessionRequest.checkoutId);

        if (orderView != null && !orderView.orderStatus.equals(DRAFT) && !orderView.orderStatus.equals(PAYMENT_PENDING)) {
        throw new UnauthorizedException("order can not place");
        }

        if (checkoutSessionRequest.value.containsKey("info")) {
            Map<String, String> info = JSON.convert(checkoutSessionRequest.value.get("info"), Map.class);
            UserResponse anonymUser = userService.createAnonymUser(info.get("phoneOrEmail"));
            if (anonymUser != null) {
                request.session().set(SESSION_USER_ID, anonymUser.id);
            }
        }
        productFormWebService.validate(checkoutSessionRequest.productId, checkoutSessionRequest.value);
        request.session().set("checkout-request:" + checkoutSessionRequest.checkoutId, JSON.toJSON(checkoutSessionRequest));
    }
}
