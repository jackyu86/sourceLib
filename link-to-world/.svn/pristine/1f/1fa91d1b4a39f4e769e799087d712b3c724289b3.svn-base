package com.caej.order.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import com.caej.order.domain.AdminStatisticsCountResult;
import com.caej.order.domain.SearchSettlementResult;
import com.caej.order.domain.StatisticsCountResult;
import com.caej.order.order.OrderStatusView;
import com.caej.order.statistics.AdminStatisticsCountRequest;
import com.caej.order.statistics.SearchSettlementRequest;
import com.caej.order.statistics.StatisticsCountRequest;
import com.google.common.base.Strings;

import io.sited.db.FindView;

/**
 * Created by cyl on 2016/12/19.
 */
public class OrderStatisticsService {
    @Inject
    StatisticsQuery<StatisticsCountResult> query;
    @Inject
    StatisticsQuery<AdminStatisticsCountResult> adminQuery;
    @Inject
    StatisticsQuery<SearchSettlementResult> settlementQuery;

    public FindView<StatisticsCountResult> statistics(OrderStatusView status, StatisticsCountRequest request) {
        StatisticsSqlBuilder builder = new StatisticsSqlBuilder();
        StringBuilder sql = builder.sql;
        List<Object> params = builder.params;
        sql.append("SELECT o.customer_id as customer_id, count(o.id) as num_count, sum(oi.price) as price_sum, count(distinct o.insured_name) as insured_count")
                .append(" FROM orders o LEFT JOIN order_item oi ON o.id = oi.order_id WHERE oi.order_status = ? ");
        builder.params.add(status.toString());
        appendOrderDateCriteria(request.startTime, request.endTime, sql, params);
        appendCustomerIdsCriteria(request.customerIds, sql, params);
        builder.conditions.append(" group by o.customer_id");
        return query.query(builder);
    }

    public FindView<AdminStatisticsCountResult> adminStatistics(OrderStatusView status, AdminStatisticsCountRequest request) {
        StatisticsSqlBuilder builder = new StatisticsSqlBuilder();
        StringBuilder sql = builder.sql;
        List<Object> params = builder.params;
        sql.append("SELECT o.dealer_id as dealer_id, count(o.id) as num_count, sum(oi.price) as price_sum, count(distinct o.insured_name) as insured_count")
                .append(" FROM orders o LEFT JOIN order_item oi ON o.id = oi.order_id WHERE oi.order_status = ? ");
        builder.params.add(status.toString());
        appendVendorCriteria(request.vendorId, sql, params);
        appendOrderDateCriteria(request.startTime, request.endTime, sql, params);
        appendDealerIdsCriteria(request.dealerIds, sql, params);
        builder.conditions.append(" group by o.dealer_id");
        return adminQuery.query(builder);
    }

    public FindView<SearchSettlementResult> searchSettlement(SearchSettlementRequest request) {
        StatisticsSqlBuilder builder = new StatisticsSqlBuilder();
        StringBuilder sql = builder.sql;
        sql.append("SELECT o.dealer_id as dealer_id, o.dealer_name as dealer_name, o.product_name as product_name, o.id as order_id, o.commission_rate as commission_rate, oi.policy_code as policy_code,")
                .append(" o.policy_holder_name as policy_holder_name, o.insured_name as insured_name, o.insured_id_number as insured_id_number,")
                .append(" o.status as status, oi.order_date as order_date")
                .append(" FROM orders o LEFT JOIN order_item oi ON o.id = oi.order_id where 1=1");
        List<Object> params = builder.params;
        appendVendorCriteria(request.vendorId, sql, params);
        appendOrderDateCriteria(request.startTime, request.endTime, sql, params);
        appendCustomerIdsCriteria(request.customerIds, sql, params);
        appendDealerIdsCriteria(request.dealerIds, sql, params);
        if (!Strings.isNullOrEmpty(request.productName)) {
            sql.append(" AND o.product_name = ?");
            params.add(request.productName);
        }
        return settlementQuery.query(builder);
    }

    private void appendVendorCriteria(String vendorId, StringBuilder sql, List<Object> params) {
        if (vendorId != null) {
            sql.append(" AND o.vendor_id = ?");
            params.add(vendorId);
        }
    }

    private void appendOrderDateCriteria(LocalDateTime startTime, LocalDateTime endTime, StringBuilder sql, List<Object> params) {
        if (startTime != null) {
            sql.append(" AND oi.order_date > ? ");
            params.add(startTime);
        }
        if (endTime != null) {
            sql.append(" AND oi.order_date < ? ");
            params.add(endTime);
        }
    }

    private void appendCustomerIdsCriteria(List<String> customerIds, StringBuilder sql, List<Object> params) {
        if (customerIds != null && !customerIds.isEmpty()) {
            sql.append(" AND o.customer_id IN (");
            for (String customerId : customerIds) {
                sql.append("?,");
                params.add(customerId);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(')');
        }
    }

    private void appendDealerIdsCriteria(List<String> dealerIds, StringBuilder sql, List<Object> params) {
        sql.append(" AND o.dealer_id IN (");
        if (!dealerIds.isEmpty()) {
            dealerIds.forEach(dealerId -> {
                sql.append("?,");
                params.add(dealerId);
            });
            sql.deleteCharAt(sql.length() - 1);
        } else {
            sql.append("''");
        }
        sql.append(')');
    }

}
