package com.caej.admin.customer.admin;

import io.sited.customer.api.customer.CustomerResponse;
import io.sited.user.api.user.UserResponse;

/**
 * @author chi
 */
public class CustomerDetailResponse {
    public CustomerResponse customer;
    public UserResponse user;
    public Double totalOrder;
}
