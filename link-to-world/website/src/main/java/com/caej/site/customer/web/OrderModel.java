package com.caej.site.customer.web;

import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;

/**
 * @author miller
 */
public class OrderModel {
    public OrderView orderView;

    public Boolean isDraft() {
        return orderView.orderStatus == OrderStatusView.DRAFT;
    }

    public Boolean isPayed() {
        return orderView.orderStatus == OrderStatusView.PAYMENT_COMPLETED;
    }

}
