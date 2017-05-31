package com.caej.order.web;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.order.OrderWebService;
import com.caej.order.domain.Order;
import com.caej.order.domain.OrderItem;
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
import com.caej.order.service.OrderItemService;
import com.caej.order.service.OrderService;
import com.caej.order.service.PaymentService;

import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class OrderWebServiceImpl implements OrderWebService {
    private final Logger logger = LoggerFactory.getLogger(OrderWebServiceImpl.class);

    @Inject
    OrderService orderService;
    @Inject
    OrderItemService orderItemService;
    @Inject
    PaymentService paymentService;

    @Override
    public CheckoutResponse checkout(CheckoutRequest request) {
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        try {
            orderService.create(request.orders, request.customer, request.requestedBy);
        } catch (Throwable e) {
            logger.error("failed to checkout, id={}", request.id, e);
            CheckoutResponse.Error error = new CheckoutResponse.Error();
            error.errorCode = e.getClass().getSimpleName();
            error.errorMessage = e.getMessage();
            checkoutResponse.error = error;
        }
        return checkoutResponse;
    }

    @Override
    public FindView<OrderView> search(SearchOrderRequest request) {
        return FindView.map(orderService.search(request), this::response);
    }

    @Override
    public OrderSumResponse sum(OrderSumRequest request) {
        OrderSumResponse response = new OrderSumResponse();
        response.customerId = request.customerId;
        response.total = orderService.sum(request.customerId);
        return response;
    }

    @Override
    public FindView<OrderView> searchPaymentComplete(SearchOrderRequest request) {
        return FindView.map(orderService.searchPaymentComplete(request), this::response);
    }

    @Override
    public OrderView get(@PathParam("orderId") String orderId) {
        Order order = orderService.findById(orderId);
        return order != null ? response(order) : null;
    }

    @Override
    public void delete(@PathParam("orderId") String orderId) {
        orderService.delete(orderId);
    }

    @Override
    public void updateUnderwriting(@PathParam(":orderId") String orderId, UpdateOrderUnderwritingStatusRequest request) {
        orderService.updateUnderwriting(orderId, request);
    }

    @Override
    public void updateOrderPayment(UpdateOrderPaymentRequest updateOrderPaymentRequest) {
        updateOrderPaymentRequest.ids.forEach(id -> {
            orderService.updateOrderPayment(id, updateOrderPaymentRequest.paymentId, updateOrderPaymentRequest.status, updateOrderPaymentRequest.paymentMethod, updateOrderPaymentRequest.outTradeNo);
        });
    }

    @Override
    public void updateByNotify(@PathParam("paymentId") String paymentId, UpdateOrderByNotify request) {
        orderService.updateByPayNotify(paymentId, request.status, request.outTradeNo);
    }

    @Override
    public void updateOrderStatus(@PathParam("paymentId") String orderId, UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, request.status);
    }

    @Override
    public OrderItemResponse getItem(@PathParam("itemId") String itemId) {
        return orderItemResponse(orderItemService.findById(itemId));
    }

    private OrderItemResponse orderItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.id = orderItem.id;
        response.name = orderItem.name;
        response.form = JSON.fromJSON(orderItem.form, Map.class);
        response.price = orderItem.price;
        response.orderStatus = orderItem.orderStatus;
        response.orderDate = orderItem.orderDate;
        response.updatedTime = orderItem.updatedTime;
        response.updatedBy = orderItem.updatedBy;
        response.createdBy = orderItem.createdBy;
        response.policyCode = orderItem.policyCode;
        response.policyAddress = orderItem.policyAddress;
        return response;
    }

    public OrderView response(Order order) {
        return orderService.orderView(order);
    }
}
