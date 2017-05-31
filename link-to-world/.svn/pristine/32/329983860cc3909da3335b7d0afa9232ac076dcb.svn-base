package com.caej.order.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.order.domain.Order;
import com.caej.order.domain.OrderItem;
import com.caej.order.order.CustomerView;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.order.UpdateOrderUnderwritingStatusRequest;
import com.caej.order.payment.PaymentMethodView;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.product.DealerProductView;
import io.sited.StandardException;
import io.sited.db.FindView;
import io.sited.db.JDBCConfig;
import io.sited.db.JDBCRepository;
import io.sited.db.Transaction;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Inject
    JDBCConfig jdbcConfig;
    @Inject
    JDBCRepository<Order> repository;
    @Inject
    OrderItemService orderItemService;
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerProductWebService dealerProductWebService;

    public void create(List<OrderView> orders, CustomerView customer, String requestBy) {
        Transaction transaction = jdbcConfig.transaction();

        for (OrderView orderView : orders) {
            Order order = new Order();
            order.id = orderView.id;
            order.productId = orderView.productId;
            order.vendorId = orderView.vendorId;
            order.productName = orderView.productName;
            order.productDisplayName = orderView.productDisplayName;
            order.productDescription = orderView.productDescription;
            order.customerId = customer.id;
            order.customerName = customer.customerName;
            order.dealerId = customer.dealerId;
            if (customer.dealerId != null) {
                Optional<DealerResponse> dealer = dealerWebService.get(customer.dealerId);
                dealer.ifPresent(dealerResponse -> order.dealerName = dealerResponse.name);
                Optional<DealerProductView> dealerProduct = dealerProductWebService.getByDealerIdAndProductName(customer.dealerId, order.productName);
                dealerProduct.ifPresent(dealerProductView -> order.commissionRate = dealerProductView.rate);
            }
            order.form = JSON.toJSON(orderView.form);
            order.planStartTime = orderView.planStartTime;
            order.periodDisplayName = orderView.periodDisplayName;
            order.periodUnit = orderView.periodUnit;
            order.periodValue = orderView.periodValue;
            order.unit = orderView.unit;
            order.price = orderView.price;
            order.shippingFee = orderView.shippingFee;
            order.discount = orderView.discount;
            order.total = orderView.total;
            order.orderDate = orderView.orderDate;
            order.orderStatus = orderView.orderStatus;
            order.shippingStatus = orderView.shippingStatus;
            order.updatedTime = LocalDateTime.now();
            order.updatedBy = requestBy;
            order.createdBy = requestBy;
            order.paymentMethod = orderView.paymentMethod;
            order.insuredName = orderView.insuredName;
            order.insuredIdType = orderView.insuredIdType;
            order.insuredIdNumber = orderView.insuredIdNumber;
            order.insuredPhone = orderView.insuredPhone;
            order.insuredEmail = orderView.insuredEmail;
            order.policyHolderName = orderView.policyHolderName;
            order.policyHolderIdType = orderView.policyHolderIdType;
            order.policyHolderIdNumber = orderView.policyHolderIdNumber;
            order.policyHolderPhone = orderView.policyHolderPhone;
            order.policyHolderEmail = orderView.policyHolderEmail;

            if (repository.get(order.id) != null) {
                repository.update(order.id, order);
                orderItemService.removeByOrderId(order.id);
            } else {
                repository.insert(order);
            }

            for (int i = 0; i < orderView.items.size(); i++) {
                OrderView.OrderItemView orderItemView = orderView.items.get(i);
                OrderItem orderItem = new OrderItem();
                orderItem.id = orderItemView.id;
                orderItem.orderId = order.id;
                orderItem.name = orderItemView.name;
                orderItem.form = JSON.toJSON(orderItemView.form);
                orderItem.price = orderItemView.price;
                orderItem.createdBy = requestBy;
                orderItem.updatedBy = requestBy;
                orderItem.orderDate = orderView.orderDate;
                orderItem.updatedTime = LocalDateTime.now();
                orderItem.orderStatus = orderItemView.orderStatus;

                orderItemService.insert(orderItem);
            }
        }

        try {
            transaction.commit();
        } catch (SQLException e) {
            logger.error("failed to create order", e);
            transaction.rollback();
            throw new StandardException(e);
        }
    }

    public Double sum(String customerId) {
        List<Order> orders = repository.query("customer_id=? and status=?", customerId, OrderStatusView.VENDOR_INSURED).findMany();
        Double sum = 0.0;
        for (Order order : orders) {
            sum += order.price;
        }
        return sum;
    }

    public FindView<Order> searchPaymentComplete(SearchOrderRequest request) {
        return repository.query(" 1=1 and status='PAYMENT_COMPLETED' ").page(request.page).limit(request.limit).find();
    }

    public FindView<Order> search(SearchOrderRequest request) {
        List<Object> params = Lists.newArrayList();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(" 1=1 ");
        if (!Strings.isNullOrEmpty(request.vendorId)) {
            queryBuilder.append("and vendor_id=? ");
            params.add(request.vendorId);
        }
        if (!Strings.isNullOrEmpty(request.dealerId)) {
            queryBuilder.append("and dealer_id=? ");
            params.add(request.dealerId);
        }
        if (request.dealerIds != null) {
            appendDealerIdsCriteria(request.dealerIds, queryBuilder, params);
        }
        if (!Strings.isNullOrEmpty(request.customerId)) {
            queryBuilder.append("and customer_id=? ");
            params.add(request.customerId);
        }
        if (!Strings.isNullOrEmpty(request.orderId)) {
            queryBuilder.append("and order_id=? ");
            params.add(request.orderId);
        }
        if (!Strings.isNullOrEmpty(request.applyCode)) {
            queryBuilder.append("and apply_code=? ");
            params.add(request.applyCode);
        }
        if (!Strings.isNullOrEmpty(request.productName)) {
            queryBuilder.append("and product_name=? ");
            params.add(request.productName);
        }
        if (null != request.orderDateFrom) {
            queryBuilder.append("and order_date>=? ");
            params.add(request.orderDateFrom);
        }
        if (null != request.orderDateTo) {
            queryBuilder.append("and order_date<=? ");
            params.add(request.orderDateTo);
        }
        if (!Strings.isNullOrEmpty(request.policyHolderEmail)) {
            queryBuilder.append("and policy_holder_email=? ");
            params.add(request.policyHolderEmail);
        }
        if (!Strings.isNullOrEmpty(request.policyHolderIdNumber)) {
            queryBuilder.append("and policy_holder_id_number=? ");
            params.add(request.policyHolderIdNumber);
        }
        if (!Strings.isNullOrEmpty(request.policyHolderIdType)) {
            queryBuilder.append("and policy_holder_id_type=? ");
            params.add(request.policyHolderIdType);
        }
        if (!Strings.isNullOrEmpty(request.policyHolderName)) {
            queryBuilder.append("and policy_holder_name=? ");
            params.add(request.policyHolderName);
        }
        if (!Strings.isNullOrEmpty(request.policyHolderPhone)) {
            queryBuilder.append("and policy_holder_phone=? ");
            params.add(request.policyHolderPhone);
        }
        if (!Strings.isNullOrEmpty(request.insuredEmail)) {
            queryBuilder.append("and insured_email=? ");
            params.add(request.insuredEmail);
        }
        if (!Strings.isNullOrEmpty(request.insuredIdNumber)) {
            queryBuilder.append("and insured_id_number=? ");
            params.add(request.insuredIdNumber);
        }
        if (!Strings.isNullOrEmpty(request.insuredIdType)) {
            queryBuilder.append("and insured_id_type=? ");
            params.add(request.insuredIdType);
        }
        if (!Strings.isNullOrEmpty(request.insuredName)) {
            queryBuilder.append("and insured_name=? ");
            params.add(request.insuredName);
        }
        if (!Strings.isNullOrEmpty(request.insuredPhone)) {
            queryBuilder.append("and insured_phone=? ");
            params.add(request.insuredPhone);
        }
        if (null != request.status) {
            if (request.status == OrderStatusView.PAYMENT_COMPLETED) {
                queryBuilder.append("and status!='DRAFT' and status!='PAYMENT_PENDING' and status!='PAYMENT_FAILED'");
            } else {
                queryBuilder.append("and status=? ");
                params.add(request.status.name());
            }
        }
        if (!Strings.isNullOrEmpty(request.paymentId)) {
            queryBuilder.append("and payment_id=? ");
            params.add(request.paymentId);
        }
        if (null != request.paymentMethod) {
            queryBuilder.append("and payment_method=? ");
            params.add(request.paymentMethod.name());
        }
        long count = repository.query(queryBuilder.toString(), params.toArray()).count();
        if (count > 0) {
            appendOrderIdsCriteria(orderItemService.search(request), queryBuilder, params);
        }
        return repository.query(queryBuilder.toString(), params.toArray()).page(request.page).limit(request.limit).find();
    }

    private void appendDealerIdsCriteria(List<String> dealerIds, StringBuilder sql, List<Object> params) {
        sql.append("and dealer_id in (");
        if (!dealerIds.isEmpty()) {
            dealerIds.forEach(dealerId -> {
                sql.append("?,");
                params.add(dealerId);
            });
            sql.deleteCharAt(sql.length() - 1);
        } else {
            sql.append("''");
        }
        sql.append(")");
    }

    private void appendOrderIdsCriteria(List<OrderItem> orderItems, StringBuilder sql, List<Object> params) {
        sql.append("and id in (");
        if (!orderItems.isEmpty()) {
            orderItems.forEach(orderItem -> {
                sql.append("?,");
                params.add(orderItem.orderId);
            });
            sql.deleteCharAt(sql.length() - 1);
        } else {
            sql.append("''");
        }
        sql.append(")");
    }

    public Order findById(String id) {
        return repository.query("id=?", id).findOne().orElse(null);
    }

    public void delete(String id) {
        repository.delete(id);
    }

    public void updateUnderwriting(String orderId, UpdateOrderUnderwritingStatusRequest request) {
        repository.query("update orders set status=?,trans_no=?,errors=?,apply_code=? where id=?", request.status.name(), request.transNo, request.errors, request.applyCode, orderId).execute();
        if (OrderStatusView.VENDOR_INSURED.equals(request.status)) {
            request.itemStatusList.forEach(updateOrderItemStatusRequest -> {
                orderItemService.updateUnderwritingStatus(updateOrderItemStatusRequest);
            });
        } else {
            orderItemService.updateAllUnderwritingStatus(orderId, OrderStatusView.VENDOR_REJECT);
        }
    }

    public void updateStatus(String paymentId, OrderStatusView status) {
        repository.query("update orders set status=? where payment_id=?", status.name(), paymentId).execute();
        FindView<Order> findView = repository.query("payment_id=?", paymentId).find();
        findView.forEach(order -> {
            orderItemService.updatePayStatus(order.id, status);
        });
    }

    public void updateByPayNotify(String paymentId, OrderStatusView status, String outTradeNo) {
        repository.query("update orders set status=?,out_trade_no=? where payment_id=?", status.name(), outTradeNo, paymentId).execute();
        FindView<Order> findView = repository.query("payment_id=?", paymentId).find();
        findView.forEach(order -> {
            orderItemService.updatePayStatus(order.id, status);
        });
    }

    public void insure(String orderId, List<OrderItem> orderItems) {
        Transaction transaction = jdbcConfig.transaction();
        try {
            updateOrderStatus(orderId, OrderStatusView.VENDOR_INSURED);
            orderItemService.update(orderItems);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new StandardException(e);
        }
    }

    public void updateOrderStatus(String orderId, OrderStatusView status) {
        repository.query("update orders set status=? where id=?", status.name(), orderId).execute();
        FindView<Order> findView = repository.query("id=?", orderId).find();
        findView.forEach(order -> orderItemService.updatePayStatus(order.id, status));
    }

    public void updateOrderPayment(String orderId, String paymentId, OrderStatusView status, PaymentMethodView paymentMethod, String outTradeNo) {
        repository.query("update orders set status=?,payment_id=?,payment_method=?,out_trade_no=? where id=?", status.name(), paymentId, paymentMethod.name(), outTradeNo, orderId).execute();
        FindView<Order> findView = repository.query("payment_id=?", paymentId).find();
        findView.forEach(order -> {
            orderItemService.updatePayStatus(order.id, status);
        });
    }

    public void updateArchiveUrl(String orderId, String archiveUrl) {
        repository.query("update orders set archive_url=? where id=?", archiveUrl, orderId).execute();
    }

    public OrderView orderView(Order order) {
        OrderView orderView = new OrderView();
        orderView.id = order.id;
        orderView.customerId = order.customerId;
        orderView.paymentId = order.paymentId;
        orderView.orderDate = order.orderDate;
        orderView.orderStatus = order.orderStatus;
        orderView.shippingStatus = order.shippingStatus;

        orderView.vendorId = order.vendorId;
        orderView.productId = order.productId;
        orderView.productName = order.productName;
        orderView.productDisplayName = order.productDisplayName;
        orderView.productDescription = order.productDescription;
        orderView.form = JSON.fromJSON(order.form, Map.class);
        orderView.price = order.price;
        orderView.shippingFee = order.shippingFee;
        orderView.discount = order.discount;
        orderView.total = order.total;
        orderView.periodDisplayName = order.periodDisplayName;
        orderView.periodUnit = order.periodUnit;
        orderView.periodValue = order.periodValue;
        orderView.unit = order.unit;

        orderView.items = Lists.newArrayList();
        List<OrderItem> orderItems = orderItemService.findByOrderId(order.id);
        orderView.items.addAll(orderItems.stream().map(this::itemView).collect(Collectors.toList()));

        orderView.transNo = order.transNo;
        orderView.transType = order.transType;
        orderView.errors = order.errors;
        orderView.dealerId = order.dealerId;
        orderView.dealerName = order.dealerName;
        orderView.updatedTime = order.updatedTime;
        orderView.applyCode = order.applyCode;
        orderView.paymentMethod = order.paymentMethod;

        orderView.insuredEmail = order.insuredEmail;
        orderView.insuredIdNumber = order.insuredIdNumber;
        orderView.insuredIdType = order.insuredIdType;
        orderView.insuredName = order.insuredName;
        orderView.insuredPhone = order.insuredPhone;

        orderView.policyHolderEmail = order.policyHolderEmail;
        orderView.policyHolderIdNumber = order.policyHolderIdNumber;
        orderView.policyHolderIdType = order.policyHolderIdType;
        orderView.policyHolderName = order.policyHolderName;
        orderView.policyHolderPhone = order.policyHolderPhone;
        orderView.planStartTime = order.planStartTime;

        return orderView;
    }

    private OrderView.OrderItemView itemView(OrderItem orderItem) {
        OrderView.OrderItemView orderItemView = new OrderView.OrderItemView();
        orderItemView.id = orderItem.id;
        orderItemView.name = orderItem.name;
        orderItemView.form = JSON.fromJSON(orderItem.form, Map.class);
        orderItemView.orderStatus = orderItem.orderStatus;
        orderItemView.price = orderItem.price;
        orderItemView.orderDate = orderItem.orderDate;
        orderItemView.policyCode = orderItem.policyCode;
        orderItemView.policyAddress = orderItem.policyAddress;
        return orderItemView;
    }
}
