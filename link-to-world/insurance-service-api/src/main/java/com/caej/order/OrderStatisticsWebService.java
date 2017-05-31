package com.caej.order;

import com.caej.order.statistics.AdminStatisticsCountRequest;
import com.caej.order.statistics.AdminStatisticsCountResponse;
import com.caej.order.statistics.SearchSettlementRequest;
import com.caej.order.statistics.SearchSettlementResponse;
import com.caej.order.statistics.StatisticsCountRequest;
import com.caej.order.statistics.StatisticsCountResponse;

import io.sited.db.FindView;
import io.sited.http.PUT;
import io.sited.http.Path;

public interface OrderStatisticsWebService {
    @PUT
    @Path("/api/order/statistics/count")
    FindView<StatisticsCountResponse> statistics(StatisticsCountRequest request);

    @PUT
    @Path("/api/order/settlement")
    FindView<SearchSettlementResponse> searchSettlement(SearchSettlementRequest request);

    @PUT
    @Path("/api/admin/order/statistics/count")
    FindView<AdminStatisticsCountResponse> adminStatistics(AdminStatisticsCountRequest request);
}
