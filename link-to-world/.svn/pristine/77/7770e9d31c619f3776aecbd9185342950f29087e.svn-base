package com.caej.site.customer.service;

import java.time.LocalDate;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.site.customer.web.GenderModel;
import com.google.common.base.Strings;

import io.sited.customer.api.AddressWebService;
import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.GenderView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.web.User;
import io.sited.util.JSON;

/**
 * TODO: move to event handler
 *
 * @author chi
 */
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Inject
    CustomerWebService customerWebService;
    @Inject
    UserWebService userWebService;
    @Inject
    AddressWebService addressWebService;

    public void autoFillUserInfo(User user, Map<String, Object> policyHolder) {
        try {
            if (user.fullName == null) {
                resetUser(user, policyHolder);
            }
            CustomerResponse customerResponse = customerWebService.get(user.id);
            if (customerResponse.identification == null) {
                resetCustomer(user, customerResponse, policyHolder);
            }
        } catch (Throwable e) {
            logger.error("failed to fill user info, userId={}", user.id, e);
        }
    }

    private void resetUser(User user, Map<String, Object> value) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.fullName = parse(value.get("name"), user.fullName);
        updateUserRequest.phone = parse(value.get("phone"), user.phone);
        updateUserRequest.email = parse(value.get("email"), user.email);
        userWebService.update(user.id, updateUserRequest);
    }

    private String parse(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return String.valueOf(value);
    }

    private void resetCustomer(User user, CustomerResponse customerResponse, Map<String, Object> value) {
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.birthday = value.containsKey("birthDate") && !Strings.isNullOrEmpty(String.valueOf(value.get("birthDate"))) ? LocalDate.parse(String.valueOf(value.get("birthDate"))) : customerResponse.birthday;
        updateCustomerRequest.gender = value.containsKey("gender") ? GenderView.valueOf(GenderModel.value(String.valueOf(value.get("gender"))).name()) : customerResponse.gender;
        if (value.containsKey("address")) {
            Map<String, Object> addressMap = JSON.convert(value.get("address"), Map.class);
            updateCustomerRequest.countryCode = parse(addressMap.get("countryCode"), null);
            updateCustomerRequest.state = parse(addressMap.get("state"), null);
            updateCustomerRequest.city = parse(addressMap.get("city"), null);
            updateCustomerRequest.ward = parse(addressMap.get("ward"), null);

            CreateAddressRequest createAddressRequest = new CreateAddressRequest();
            createAddressRequest.fullName = user.fullName;
            createAddressRequest.countryCode = "CHN";
            createAddressRequest.state = updateCustomerRequest.state;
            createAddressRequest.city = updateCustomerRequest.city;
            createAddressRequest.ward = updateCustomerRequest.ward;
            createAddressRequest.address1 = parse(addressMap.get("address1"), null);
            createAddressRequest.address2 = parse(addressMap.get("address2"), null);
            createAddressRequest.zipCode = parse(addressMap.get("postalCode"), null);
            createAddressRequest.phone = user.phone;
            createAddressRequest.requestBy = "kdlins-website";
            addressWebService.create(user.id, createAddressRequest);
        } else {
            updateCustomerRequest.countryCode = customerResponse.countryCode;
            updateCustomerRequest.state = customerResponse.state;
            updateCustomerRequest.city = customerResponse.city;
            updateCustomerRequest.ward = customerResponse.ward;
        }
        updateCustomerRequest.requestBy = "kdlins-website";
        customerWebService.update(user.id, updateCustomerRequest);
    }

}
