package com.caej.insurance.api.insurance;

import java.util.List;

/**
 * @author miller
 */
public class InsurancePriceTableAdminView {
    public InsurancePaymentMethod method;
    public String engine;
    public Double fixedPrice;
    public List<InsurancePaymentPeriodView> periods;

    public List<String> xFactors;
    public String yFactor;
    public String table;
    public Double base;
    public String baseFactor;

}
