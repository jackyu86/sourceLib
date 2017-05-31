package com.caej.order.job;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import javax.inject.Inject;

import com.caej.cart.api.CartWebService;
import com.caej.cart.api.cart.AddCartRequest;
import com.caej.cart.api.cart.CartTypeView;
import com.caej.order.config.PayConfig;
import com.caej.order.domain.Order;
import com.caej.order.domain.Payment;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.payment.PaymentStatusView;
import com.caej.order.service.OrderService;
import com.caej.order.service.PaymentService;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.ProductResponse;
import com.caej.scheduler.service.JobSchedulerService;

import io.sited.db.FindView;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class PaymentTimeoutJob implements Runnable {
    private static final String JOB_NAME = "paymentTimeout";
    @Inject
    PaymentService paymentService;
    @Inject
    PayConfig payConfig;
    @Inject
    OrderService orderService;
    @Inject
    CartWebService cartWebService;
    @Inject
    ProductWebService productWebService;
    @Inject
    JobSchedulerService jobSchedulerService;

    @Override
    public void run() {
        if (!jobSchedulerService.canRun(JOB_NAME)) return;
        try {
            LocalDateTime timeout = LocalDateTime.now();
            timeout.plus(payConfig.paymentTimeout, ChronoUnit.SECONDS);
            FindView<Payment> findView = paymentService.searchTimeout(timeout);
            findView.forEach(payment -> {
                paymentService.updateStatusByJob(payment, PaymentStatusView.PAYMENT_FAILED);
                SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
                searchOrderRequest.paymentId = payment.id;
                searchOrderRequest.page = 1;
                searchOrderRequest.limit = Integer.MAX_VALUE;
                FindView<Order> orders = orderService.search(searchOrderRequest);
                orderService.updateStatus(payment.id, OrderStatusView.PAYMENT_FAILED);
                orders.forEach(order -> {
                    ProductResponse product = productWebService.get(order.productId);
                    AddCartRequest addCartRequest = new AddCartRequest();
                    addCartRequest.productId = product.id;
                    addCartRequest.productName = product.displayName;
                    addCartRequest.form = JSON.fromJSON(order.form, Map.class);
                    addCartRequest.type = CartTypeView.CUSTOMER;
                    addCartRequest.requestBy = "job";
                    cartWebService.add(payment.customerId, addCartRequest);
                });
            });
            jobSchedulerService.toWait(JOB_NAME);
        } catch (Exception e) {
            jobSchedulerService.toError(JOB_NAME, JSON.toJSON(e));
        }
    }
}
