package com.caej.order;


import com.caej.order.order.CheckoutRequest;
import com.caej.order.order.CheckoutResponse;
import com.caej.order.order.OrderItemResponse;
import com.caej.order.order.OrderSumRequest;
import com.caej.order.order.OrderSumResponse;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.order.UpdateOrderByNotify;
import com.caej.order.order.UpdateOrderPaymentRequest;
import com.caej.order.order.UpdateOrderStatusRequest;
import com.caej.order.order.UpdateOrderUnderwritingStatusRequest;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface OrderWebService {
    @POST
    @Path("/api/order")
    CheckoutResponse checkout(CheckoutRequest request);

    @PUT
    @Path("/api/order/search")
    FindView<OrderView> search(SearchOrderRequest request);

    @PUT
    @Path("/api/order/sum")
    OrderSumResponse sum(OrderSumRequest request);

    @PUT
    @Path("/api/order/search/payment-complete")
    FindView<OrderView> searchPaymentComplete(SearchOrderRequest request);

    @GET
    @Path("/api/order/:orderId")
    OrderView get(@PathParam("orderId") String orderId);

    @DELETE
    @Path("/api/order/:orderId")
    void delete(@PathParam("orderId") String orderId);

    @PUT
    @Path("/api/order/:orderId/underwriting")
    void updateUnderwriting(@PathParam("orderId") String orderId, UpdateOrderUnderwritingStatusRequest request);

    @PUT
    @Path("/api/order/payment")
    void updateOrderPayment(UpdateOrderPaymentRequest updateOrderPaymentRequest);

    @PUT
    @Path("/api/order/payment/:paymentId")
    void updateByNotify(@PathParam("paymentId") String paymentId, UpdateOrderByNotify request);

    @PUT
    @Path("/api/order/:orderId/status")
    void updateOrderStatus(@PathParam("orderId") String orderId, UpdateOrderStatusRequest request);

    @GET
    @Path("/api/oder/item/:itemId")
    OrderItemResponse getItem(@PathParam("itemId") String itemId);
}
