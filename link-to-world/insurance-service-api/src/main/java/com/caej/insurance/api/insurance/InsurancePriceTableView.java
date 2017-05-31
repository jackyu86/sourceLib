package com.caej.insurance.api.insurance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InsurancePriceTableView {
    public InsurancePaymentMethod method;
    public String engine;
    public Double fixedPrice;
    public List<InsurancePaymentPeriodView> periods;

    public List<String> xFactors;
    public String yFactor;
    public Map<String, Map<String, Double>> table;
    public Double base;
    public String baseFactor;

    public LocalDateTime createdTime;
    public String createdBy;
}
