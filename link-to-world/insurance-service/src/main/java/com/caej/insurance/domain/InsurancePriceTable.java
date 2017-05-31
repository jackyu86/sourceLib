package com.caej.insurance.domain;

import io.sited.db.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author miller
 */
public class InsurancePriceTable {
    @Field(name = "engine")
    public String engine;

    @Field(name = "fixed_price")
    public Double fixedPrice;

    @Field(name = "base")
    public Double base;
    @Field(name = "base_factor")
    public String baseFactor;

    @Field(name = "x_factors")
    public List<String> xFactors;
    @Field(name = "y_factor")
    public String yFactor;
    @Field(name = "table")
    public Map<String, Map<String, Double>> table;

    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
}
