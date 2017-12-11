package com.caej.insurance.api.vendor;

/**
 * @author chi
 */
public class InsuranceVendorQuery {
    public String name;
    public Integer page = 1;
    public Integer limit = 10;
    public InsuranceVendorLevelView level;
    public String order;
    public Boolean desc;
}
