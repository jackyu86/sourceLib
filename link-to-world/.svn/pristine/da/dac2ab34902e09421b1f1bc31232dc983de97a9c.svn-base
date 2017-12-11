package com.caej.order.web;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;

import com.caej.SiteRule;
import com.caej.insurance.InsuranceModuleImpl;
import com.caej.order.OrderModuleImpl;
import com.caej.order.domain.Order;
import com.caej.order.domain.OrderItem;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.ShippingStatus;
import com.google.common.collect.Lists;

import io.sited.db.DBModule;
import io.sited.db.JDBCConfig;
import io.sited.db.JDBCRepository;
import io.sited.util.Types;

/**
 * Created by hubery.chen on 2016/12/26.
 */
public class OrderStatisticsServiceTest {
    @Rule
    public final SiteRule site = new SiteRule(new OrderModuleImpl(), new InsuranceModuleImpl());

    @Before
    public void before() throws Exception {
        Order order = new Order();
        order.id = UUID.randomUUID().toString();
        order.dealerId = "1";
        order.orderStatus = OrderStatusView.AUDITING;
        order.shippingStatus = ShippingStatus.NONE;
        order.form = "{}";
        order.orderDate = LocalDateTime.of(2016, 12, 1, 1, 0, 0);
        DBModule dbModule = site.module(DBModule.class);
        dbModule.require(JDBCConfig.class).entity(Order.class);
        dbModule.require(JDBCConfig.class).entity(OrderItem.class);
        ((JDBCRepository) dbModule.require(Types.generic(JDBCRepository.class, Order.class))).insert(order);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.id = UUID.randomUUID().toString();
        orderItem1.orderId = order.id;
        orderItem1.orderStatus = OrderStatusView.AUDITING;
        orderItem1.price = 1d;

        ((JDBCRepository) dbModule.require(Types.generic(JDBCRepository.class, OrderItem.class))).batchInsert(Lists.newArrayList(orderItem1));
    }

//    @Test
//    public void testStatistics() throws Exception {
//        StatisticsCountRequest request = new StatisticsCountRequest();
//        request.customerIds = Lists.newArrayList("1");
//        request.startTime = LocalDateTime.of(2016, 12, 20, 0, 0);
//        request.endTime = LocalDateTime.of(2017, 1, 1, 0, 0);
//        ServerResponse response = site.post("/api/order/statistics/count").body(request).execute();
//        Assert.assertEquals(200, response.statusCode());
//        FindView results = response.body(FindView.class);
//        Assert.assertEquals(Long.valueOf(1), results.total);
//    }
}
