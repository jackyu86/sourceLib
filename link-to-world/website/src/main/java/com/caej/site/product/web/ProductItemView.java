package com.caej.site.product.web;

import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.product.api.price.ProductPriceResponse;
import com.caej.product.api.product.FormView;

public class ProductItemView {
    public String id;
    public String name;
    public String displayName;
    public String highlightContent;
    public FormView form;
    public ProductPriceResponse price;
    public InsuranceVendorResponse vendor;
    public String specialFunction;
    public String specialRule;
}
