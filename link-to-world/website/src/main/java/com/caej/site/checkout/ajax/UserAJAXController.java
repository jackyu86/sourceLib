package com.caej.site.checkout.ajax;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.api.country.InsuranceCityResponse;
import com.caej.insurance.api.country.InsuranceProvinceResponse;
import com.caej.site.customer.web.GenderModel;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UpdatePasswordRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.web.User;

/**
 * @author Jonathan.Guo
 */
public class UserAJAXController {
    @Inject
    UserWebService userWebService;
    @Inject
    CustomerWebService customerWebService;
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;

    @Path("/ajax/user/info")
    @GET
    public CheckoutUserAJAXResponse info(Request request) {
        User user = request.require(User.class, null);
        CheckoutUserAJAXResponse userAJAXResponse = new CheckoutUserAJAXResponse();
        if (user == null) {
            throw new UnauthenticatedException("not login");
        }
        userAJAXResponse.name = user.fullName;
        userAJAXResponse.phone = user.phone;
        userAJAXResponse.email = user.email;
        CustomerResponse customerResponse = customerWebService.get(user.id);
        userAJAXResponse.id = customerResponse.identification;
        userAJAXResponse.birthDate = customerResponse.birthday;
        userAJAXResponse.gender = GenderModel.valueOf(customerResponse.gender.name()).value;

        FindProvincesRequest findProvincesRequest = new FindProvincesRequest();
        findProvincesRequest.names.add(customerResponse.state);
        FindView<InsuranceProvinceResponse> provinces = insuranceCountryWebService.provinces(findProvincesRequest);
        if (provinces.total > 0) {
            userAJAXResponse.address = Maps.newHashMap();
            userAJAXResponse.address.put("state", customerResponse.state);
            String provinceId = provinces.items.get(0).id.toHexString();
            userAJAXResponse.address.put("stateId", provinceId);
            List<InsuranceCityResponse> cities = insuranceCountryWebService.city(provinceId);
            if (!cities.isEmpty()) {
                userAJAXResponse.address.put("city", customerResponse.city);
                userAJAXResponse.address.put("cityId", cities.stream().filter(city -> city.name.equals(customerResponse.city)).findFirst().get().id.toHexString());
                userAJAXResponse.address.put("ward", customerResponse.ward);
            }
        }

        return userAJAXResponse;
    }

    @Path("/ajax/anonym/self/password")
    @PUT
    public void changePassword(Request request) throws IOException {
        User user = request.require(User.class);

        if (!user.roles.contains("anonym")) {
            throw new UnauthenticatedException("user can not anonymous action");
        }
        AnonymChangePasswordRequest resetPasswordRequest = request.body(AnonymChangePasswordRequest.class);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.password = resetPasswordRequest.newPassword;
        updatePasswordRequest.requestBy = resetPasswordRequest.requestBy;
        userWebService.updatePassword(user.id, updatePasswordRequest);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.email = user.email;
        updateUserRequest.roles = Lists.newArrayList();
        userWebService.update(user.id, updateUserRequest);
    }

}
