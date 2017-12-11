package com.caej.admin.dealer;

import java.util.List;

import com.caej.insurance.api.country.InsuranceProvinceResponse;

import app.dealer.api.credit.CreditResponse;
import app.dealer.api.dealer.DealerResponse;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.user.api.user.UserResponse;

/**
 * Created by hubery.chen on 2017/1/4.
 */
public class DealerUpdateView {
    public DealerResponse dealer;
    public UserResponse user;
    public CustomerResponse customer;
    public String password;
    public List<InsuranceProvinceResponse> states;
    public CreditResponse credit;
}
