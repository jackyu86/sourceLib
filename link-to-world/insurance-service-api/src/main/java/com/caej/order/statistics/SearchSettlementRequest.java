package com.caej.order.statistics;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Hubery.Chen
 */
public class SearchSettlementRequest extends StatisticsRequest {
    public List<String> dealerIds = Lists.newArrayList();
    public List<String> states;
    public String vendorId;
    public List<String> customerIds = Lists.newArrayList();
    public String productName;
    public Integer page;
    public Integer limit;
}
