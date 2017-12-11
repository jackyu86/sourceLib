package com.caej.site.order.web;

import static com.caej.order.order.OrderStatusView.DRAFT;
import static com.caej.order.order.OrderStatusView.PAYMENT_PENDING;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.client.ProductWebServiceClient;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.order.OrderWebService;
import com.caej.order.order.OrderView;
import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.product.FormView;
import com.caej.product.api.product.ProductResponse;
import com.caej.site.checkout.CheckoutSessionRequest;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.product.DealerProductView;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.user.web.User;
import io.sited.util.JSON;

/**
 * @author Jonathan.Guo
 */
public class OrderController {
    @Inject
    ProductWebServiceClient productWebServiceClient;
    @Inject
    ProductFormWebService productFormWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    InsuranceVendorWebService insuranceVendorWebService;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    OrderWebService orderWebService;

    @Path("/order/:orderId/edit")
    @GET
    public Response checkout(Request request) {
        String orderId = request.pathParam("orderId");
        OrderView order = orderWebService.get(orderId);

        User user = request.require(User.class);
        if (!order.customerId.equals(user.id)) {
            throw new UnauthorizedException("order is not belong to user");
        }

        if (!order.orderStatus.equals(DRAFT) && !order.orderStatus.equals(PAYMENT_PENDING)) {
            throw new UnauthorizedException("order can not place");
        }

        Map<String, Object> context = Maps.newHashMap();
        context.put("id", order.productId);
        context.put("orderId", order.id);
        context.put("checkoutId", order.id);

        ProductResponse product;
        if (Strings.isNullOrEmpty(order.productName)) {
            product = productWebServiceClient.get(order.productId);
        } else {
            product = productWebServiceClient.getByName(order.productName);
        }
        context.put("product", product);

        FormView formView = productFormWebService.checkout(order.productId, Maps.newHashMap(order.form));
        Optional<DealerUserResponse> dealerUserResponse = dealerUserWebService.get(user.id);
        if (dealerUserResponse.isPresent()) {
            Optional<DealerProductView> optional = dealerProductWebService.getByDealerIdAndProductName(dealerUserResponse.get().dealerId, product.name);
            if (!optional.isPresent()) return Response.redirect("/checkout/forbidden");
        }
        context.put("isDealer", dealerUserResponse.isPresent());
        context.put("form", formView);
        context.put("vendor", insuranceVendorWebService.get(product.insuranceVendorId.toHexString()));
        return Response.template("/checkout/checkout.html", context);
    }

    @Path("/order/:orderId/preview")
    @GET
    public Response preview(Request request) {
        String orderId = request.pathParam("orderId");
        String checkoutId = "checkout-request:" + orderId;
        Optional<String> checkoutRequestOptional = request.session().get(checkoutId);
        if (!checkoutRequestOptional.isPresent()) {
            throw new NotFoundException("missing checkout request");
        }
        CheckoutSessionRequest checkoutSessionRequest = JSON.fromJSON(checkoutRequestOptional.get(), CheckoutSessionRequest.class);
        ProductResponse product = productWebServiceClient.get(checkoutSessionRequest.productId);
        Map<String, Object> context = Maps.newHashMap();
        context.put("id", checkoutSessionRequest.productId);
        context.put("orderId", orderId);
        context.put("product", product);
        context.put("vendor", insuranceVendorWebService.get(product.insuranceVendorId.toString()));
        Map<String, Object> value = Maps.newHashMap(checkoutSessionRequest.value);
        context.put("form", productFormWebService.checkoutPreview(checkoutSessionRequest.productId, value));
        return Response.template("/checkout/preview.html", context);
    }
}
