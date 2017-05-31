package com.caej.site.underwriting.web.ajax;

import static com.caej.order.order.OrderStatusView.SURRENDERING;

import java.util.Optional;

import javax.inject.Inject;

import com.caej.order.OrderWebService;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.UpdateOrderStatusRequest;
import com.caej.underwriting.api.UnderwritingWebService;
import com.caej.underwriting.api.underwriting.DischargeRequest;
import com.caej.underwriting.api.underwriting.DischargeResponse;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerUserResponse;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.web.User;

/**
 * @author miller
 */
public class UnderwritingAJAXController {
    @Inject
    UnderwritingWebService underwritingWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    OrderWebService orderWebService;

    @Path("/ajax/underwriting/discharge")
    @PUT
    public Response discharge(Request request) {
        User user = request.require(User.class, null);
        if (user == null) {
            throw new UnauthenticatedException("user not login");
        }
        Optional<DealerUserResponse> optional = dealerUserWebService.get(user.id);
        if (!optional.isPresent()) {
            throw new UnauthenticatedException("operation forbidden");
        }
        DischargeRequest dischargeRequest = request.body(DischargeRequest.class);
        OrderView orderView = orderWebService.get(dischargeRequest.orderId);
        if (!orderView.dealerId.equals(optional.get().dealerId)) {
            throw new UnauthenticatedException("operation forbidden");
        }
        if (!OrderStatusView.VENDOR_INSURED.name().equals(orderView.orderStatus.name())) {
            throw new UnauthenticatedException("invalid order status");
        }

        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.status = SURRENDERING;
        orderWebService.updateOrderStatus(dischargeRequest.orderId, updateOrderStatusRequest);

        DischargeResponse dischargeResponse = underwritingWebService.discharge(dischargeRequest);
        return Response.body(dischargeResponse);
    }
}
