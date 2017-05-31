package com.caej.order.web;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.caej.SiteRule;
import com.caej.insurance.InsuranceModuleImpl;
import com.caej.order.OrderModule;
import com.caej.order.OrderModuleImpl;
import com.caej.order.domain.Order;
import com.caej.order.order.CheckoutRequest;
import com.caej.order.order.CheckoutResponse;
import com.caej.order.order.CustomerView;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.order.order.ShippingStatus;
import com.caej.product.ProductModuleImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.db.FindView;
import io.sited.db.JDBCRepository;
import io.sited.http.ServerResponse;
import io.sited.util.JSON;
import io.sited.util.Types;

/**
 * @author chi
 */
@Ignore
public class OrderWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new OrderModuleImpl(), new InsuranceModuleImpl(), new ProductModuleImpl());

    @Test
    public void checkout() throws Exception {
        ServerResponse response = site.post("/api/order").body(request()).execute();
        CheckoutResponse checkoutResponse = response.body(CheckoutResponse.class);
        Assert.assertNull(checkoutResponse.error);
    }

    private CheckoutRequest request() {
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        OrderView order = new OrderView();
        order.id = UUID.randomUUID().toString();
        order.orderDate = LocalDateTime.now();
        order.orderStatus = OrderStatusView.DRAFT;
        order.shippingStatus = ShippingStatus.NONE;
        OrderView.OrderItemView item = new OrderView.OrderItemView();
        item.id = order.id + '-' + 1;
        item.form = Maps.newHashMap();
        item.price = 1000.0;
        order.items = Lists.newArrayList(item);
        checkoutRequest.orders = Lists.newArrayList(order);

        CustomerView customer = new CustomerView();
        customer.id = "1";
        customer.customerName = "customer name";
        customer.dealerId = "1";
        checkoutRequest.customer = customer;

        checkoutRequest.requestedBy = "test";
        return checkoutRequest;
    }

    @Test
    public void search() throws Exception {
        JDBCRepository<Order> repository = site.module(OrderModule.class).require(Types.generic(JDBCRepository.class, Order.class));
        Order order = new Order();
        order.id = UUID.randomUUID().toString();
        order.customerId = UUID.randomUUID().toString();
        order.orderStatus = OrderStatusView.DRAFT;
        order.shippingStatus = ShippingStatus.NONE;
        order.form = JSON.toJSON(Maps.newHashMap());
        repository.insert(order);
        SearchOrderRequest request = new SearchOrderRequest();
        request.customerId = order.customerId;
        request.page = 1;
        request.limit = 10;
        ServerResponse response = site.put("/api/order/search").body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<OrderView> findView = response.body(FindView.class);
        Assert.assertFalse(findView.items.isEmpty());
        OrderView orderView = findView.items.get(0);
        Assert.assertEquals(orderView.id, order.id);
    }
}