package com.caej.order.service;

import java.util.List;

import javax.inject.Inject;

import com.caej.order.domain.OrderItem;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.order.UpdateOrderItemUnderwritingStatusRequest;
import com.google.common.base.Strings;

import io.sited.db.JDBCRepository;

/**
 * @author chi
 */
public class OrderItemService {
    @Inject
    JDBCRepository<OrderItem> repository;

    public void insert(OrderItem orderItem) {
        repository.insert(orderItem);
    }

    public OrderItem findById(String id) {
        return repository.query("id=?", id).findOne().get();
    }

    public List<OrderItem> findByOrderId(String orderId) {
        return repository.query("order_id=?", orderId).findMany();
    }

    public void updateUnderwritingStatus(UpdateOrderItemUnderwritingStatusRequest request) {
        repository.query("update order_item set order_status=?,policy_code=?,policy_address=? where id=?", request.orderStatus.name(), request.policyCode, request.policyAddress, request.id).execute();
    }

    public void updateAllUnderwritingStatus(String orderId, OrderStatusView status) {
        repository.query("update order_item set order_status=? where order_id=?", status.name(), orderId).execute();
    }

    public void updatePayStatus(String orderId, OrderStatusView status) {
        repository.query("update order_item set order_status=? where order_id=?", status.name(), orderId).execute();
    }

    public List<OrderItem> findByName(String name) {
        return repository.query("name like '%" + name + "%'").findMany();
    }

    public List<OrderItem> search(SearchOrderRequest request) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" 1=1 ");
        if (!Strings.isNullOrEmpty(request.insuredName)) {
            queryBuilder.append("and name like '%").append(request.insuredName).append("%' ");
        }
        if (!Strings.isNullOrEmpty(request.policyCode)) {
            queryBuilder.append("and policy_code = '").append(request.policyCode).append("'");
        }
        return repository.query(queryBuilder.toString()).findMany();
    }

    public void removeByOrderId(String orderId) {
        List<OrderItem> orderItems = repository.query("order_id=?", orderId).findMany();
        orderItems.forEach(orderItem -> {
            repository.delete(orderItem.id);
        });
    }

    public void update(List<OrderItem> items) {
        items.forEach(orderItem -> repository.update(orderItem.id, orderItem));
    }
}
