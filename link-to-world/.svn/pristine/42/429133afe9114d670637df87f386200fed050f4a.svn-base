package com.caej.order.web;

import static com.caej.order.order.OrderStatusView.VENDOR_INSURED;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.order.OrderStatisticsWebService;
import com.caej.order.domain.AdminStatisticsCountResult;
import com.caej.order.domain.SearchSettlementResult;
import com.caej.order.domain.StatisticsCountResult;
import com.caej.order.order.OrderStatusView;
import com.caej.order.service.OrderStatisticsService;
import com.caej.order.statistics.AdminStatisticsCountRequest;
import com.caej.order.statistics.AdminStatisticsCountResponse;
import com.caej.order.statistics.SearchSettlementRequest;
import com.caej.order.statistics.SearchSettlementResponse;
import com.caej.order.statistics.StatisticsCountRequest;
import com.caej.order.statistics.StatisticsCountResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.sited.db.FindView;

/**
 * Created by cyl on 2016/12/19.
 */
public class OrderStatisticsWebServiceImpl implements OrderStatisticsWebService {
    @Inject
    OrderStatisticsService orderStatisticsService;

    @Override
    public FindView<StatisticsCountResponse> statistics(StatisticsCountRequest request) {
        Map<String, StatisticsCountResult> insuredMap = orderStatisticsService.statistics(VENDOR_INSURED, request).items.stream().collect(Collectors.toMap(result -> result.customerId, result -> result));
        Map<String, StatisticsCountResult> surrenderedMap = orderStatisticsService.statistics(OrderStatusView.SURRENDERED, request).items.stream().collect(Collectors.toMap(result -> result.customerId, result -> result));
        Map<String, StatisticsCountResult> rejectMap = orderStatisticsService.statistics(OrderStatusView.VENDOR_REJECT, request).items.stream().collect(Collectors.toMap(result -> result.customerId, result -> result));
        FindView<StatisticsCountResponse> responses = new FindView<>();

        List<String> customerIds;
        if (request.customerIds != null) {
            customerIds = request.customerIds;
        } else {
            customerIds = merge(insuredMap.keySet(), surrenderedMap.keySet(), rejectMap.keySet());
        }

        responses.total = (long) customerIds.size();
        customerIds.forEach(customerId -> {
            StatisticsCountResponse response = new StatisticsCountResponse();
            response.customerId = customerId;
            if (insuredMap.containsKey(customerId)) {
                StatisticsCountResult insuredResult = insuredMap.get(customerId);
                response.insuredFee = format(insuredResult.sum);
                response.insuredNum = insuredResult.count;
                response.insuredInsuredNum = insuredResult.insuredCount;
            }
            if (surrenderedMap.containsKey(customerId)) {
                StatisticsCountResult surrenderedResult = surrenderedMap.get(customerId);
                response.surrenderFee = format(surrenderedResult.sum);
                response.surrenderNum = surrenderedResult.count;
                response.surrenderInsuredNum = surrenderedResult.insuredCount;
            }
            if (rejectMap.containsKey(customerId)) {
                StatisticsCountResult rejectResult = rejectMap.get(customerId);
                response.rejectFee = format(rejectResult.sum);
                response.rejectNum = rejectResult.count;
                response.rejectInsuredNum = rejectResult.insuredCount;
            }
            responses.items.add(response);
        });
        return responses;
    }

    @Override
    public FindView<SearchSettlementResponse> searchSettlement(SearchSettlementRequest request) {
        return FindView.map(orderStatisticsService.searchSettlement(request), this::response);
    }

    @Override
    public FindView<AdminStatisticsCountResponse> adminStatistics(AdminStatisticsCountRequest request) {
        List<String> dealerIds = request.dealerIds;

        if (dealerIds == null || dealerIds.isEmpty()) {
            return new FindView<>();
        }

        Map<String, AdminStatisticsCountResult> insuredMap = orderStatisticsService.adminStatistics(VENDOR_INSURED, request).items.stream().collect(Collectors.toMap(result -> result.dealerId, result -> result));
        Map<String, AdminStatisticsCountResult> surrenderedMap = orderStatisticsService.adminStatistics(OrderStatusView.SURRENDERED, request).items.stream().collect(Collectors.toMap(result -> result.dealerId, result -> result));
        Map<String, AdminStatisticsCountResult> rejectMap = orderStatisticsService.adminStatistics(OrderStatusView.VENDOR_REJECT, request).items.stream().collect(Collectors.toMap(result -> result.dealerId, result -> result));
        FindView<AdminStatisticsCountResponse> responses = new FindView<>();

        responses.total = (long) dealerIds.size();
        dealerIds.forEach(dealerId -> {
            AdminStatisticsCountResponse response = new AdminStatisticsCountResponse();
            response.dealerId = dealerId;
            if (insuredMap.containsKey(dealerId)) {
                AdminStatisticsCountResult insuredResult = insuredMap.get(dealerId);
                response.insuredFee = format(insuredResult.sum);
                response.insuredNum = insuredResult.count;
                response.insuredInsuredNum = insuredResult.insuredCount;
            }
            if (surrenderedMap.containsKey(dealerId)) {
                AdminStatisticsCountResult surrenderedResult = surrenderedMap.get(dealerId);
                response.surrenderFee = format(surrenderedResult.sum);
                response.surrenderNum = surrenderedResult.count;
                response.surrenderInsuredNum = surrenderedResult.insuredCount;
            }
            if (rejectMap.containsKey(dealerId)) {
                AdminStatisticsCountResult rejectResult = rejectMap.get(dealerId);
                response.rejectFee = format(rejectResult.sum);
                response.rejectNum = rejectResult.count;
                response.rejectInsuredNum = rejectResult.insuredCount;
            }
            responses.items.add(response);
        });
        return responses;
    }

    private double format(Double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private List<String> merge(Set<String>... sets) {
        Set<String> listSet = Sets.newHashSet();
        for (Set<String> set : sets) {
            listSet.addAll(set);
        }
        return Lists.newArrayList(listSet);
    }

    private SearchSettlementResponse response(SearchSettlementResult result) {
        SearchSettlementResponse response = new SearchSettlementResponse();
        response.dealerName = result.dealerName;
        response.commissionRate = result.commissionRate;
        response.productName = result.productName;
        response.orderId = result.orderId;
        response.policyCode = result.policyCode;
        response.policyHolderName = result.policyHolderName;
        response.insuredName = result.insuredName;
        response.insuredIdNumber = result.insuredIdNumber;
        response.status = result.status;
        response.orderDate = result.orderDate;
        return response;
    }
}
