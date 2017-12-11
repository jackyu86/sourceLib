package com.caej.insurance.api.insurance;

import java.util.List;

/**
 * @author miller
 */
public class InsuranceAdminRequest {
    public String name;
    public Boolean master;
    public Double maxAmount;
    public List<InsuranceLiabilityRefView> liabilities;
    public InsurancePriceTableAdminView priceTable;
    public String priceTableURL;
    public String requestBy;
}
