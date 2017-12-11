package com.caej.site.checkout.ajax;

import java.util.UUID;

import com.caej.site.checkout.CheckoutSessionRequest;

import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class CheckoutAJAXController {
    @Path("/ajax/checkout")
    @POST
    public CheckoutResponse checkout(Request request) {
        CheckoutSessionRequest checkoutSessionRequest = request.body(CheckoutSessionRequest.class);
        checkoutSessionRequest.checkoutId = UUID.randomUUID().toString();
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.checkoutId = checkoutSessionRequest.checkoutId;
        request.session().set("checkout-request:" + checkoutResponse.checkoutId, JSON.toJSON(checkoutSessionRequest));
        return checkoutResponse;
    }
}
