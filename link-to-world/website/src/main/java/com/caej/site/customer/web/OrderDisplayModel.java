package com.caej.site.customer.web;


import static com.caej.order.order.OrderStatusView.DOCUMENTED;
import static com.caej.order.order.OrderStatusView.SURRENDER_FAILED;
import static com.caej.order.order.OrderStatusView.VENDOR_INSURED;
import static com.caej.order.order.OrderStatusView.VENDOR_REJECT;

import java.time.LocalDateTime;
import java.util.List;

import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;

/**
 * @author Hubery.Chen
 */
public class OrderDisplayModel {
    public String id;
    public String productDisplayName;
    public LocalDateTime planStartTime;
    public List<OrderView.OrderItemView> items;
    public String periodDisplayName;
    public Double total;
    public OrderStatusView orderStatus;
    public Boolean surrenderMark = false;

    public Boolean canSurrendering() {
        return surrenderMark != null && surrenderMark && (orderStatus.equals(VENDOR_INSURED) || orderStatus.equals(SURRENDER_FAILED) || orderStatus.equals(DOCUMENTED) || orderStatus.equals(VENDOR_REJECT));
    }

    public Boolean canEdit() {
        return orderStatus == OrderStatusView.DRAFT || orderStatus == OrderStatusView.PAYMENT_PENDING;
    }

    public Boolean canViewDetail() {
        return orderStatus != OrderStatusView.DRAFT;
    }
}
