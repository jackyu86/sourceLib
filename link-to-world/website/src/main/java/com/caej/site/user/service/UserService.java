package com.caej.site.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.site.dealer.web.ajax.CreateDealerAJAXRequest;
import com.caej.site.dealer.web.ajax.CreateDealerUserAJAXRequest;
import com.caej.site.dealer.web.ajax.DealerInfoUpdateAJAXRequest;
import com.caej.site.dealer.web.ajax.RegisterDealerRequest;
import com.caej.site.dealer.web.ajax.UpdateDealerUserAJAXRequest;
import com.google.common.collect.Lists;

import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.GenderView;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.http.Request;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.AuthenticationRequest;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UpdatePasswordRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UpdateUserStatusRequest;
import io.sited.user.api.user.UserResponse;
import io.sited.user.api.user.UserStatusView;
import io.sited.user.web.User;

public class UserService {
    @Inject
    UserWebService userWebService;
    @Inject
    CustomerWebService customerWebService;

    public UserResponse get(String userId) {
        return userWebService.get(userId);
    }

    public void delete(String id) {
        customerWebService.delete(id);
        userWebService.delete(id);
    }

    public void updateStatus(String id, UserStatusView status) {
        UpdateUserStatusRequest updateUserStatusRequest = new UpdateUserStatusRequest();
        updateUserStatusRequest.status = status;
        userWebService.updateStatus(id, updateUserStatusRequest);
    }

    public void updateUser(String requestBy, UpdateDealerUserAJAXRequest request) {
        UpdateUserRequest updateUserRequest = updateUserRequest(request.fullName, request.phone, request.email);
        updateUserRequest.requestBy = requestBy;
        userWebService.update(request.id, updateUserRequest);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.password = request.password;
        updatePasswordRequest.requestBy = requestBy;
        userWebService.updatePassword(request.id, updatePasswordRequest);
    }

    public void updateUser(String id, DealerInfoUpdateAJAXRequest request) {
        UpdateUserRequest updateUserRequest = updateUserRequest(request.fullName, request.phone, request.email);
        userWebService.update(id, updateUserRequest);
        updateCustomer(id, request.contactIdNumber);
    }

    public void updateUser(String userId, String contactIdNumber) {
        updateCustomer(userId, contactIdNumber);
    }

    public UserResponse createAnonymUser(String phoneOrEmail) {
        Optional<UserResponse> userOptional = userWebService.findByUsername(phoneOrEmail);
        UserResponse userResponse = null;
        if (!userOptional.isPresent()) {
            CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.username = phoneOrEmail;
            createUserRequest.roles = Lists.newArrayList("anonym");
            createUserRequest.requestBy = "kdlins-website";
            userResponse = userWebService.create(createUserRequest);

            CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
            createCustomerRequest.id = userResponse.id;
            createCustomerRequest.requestBy = "kdlins-website";
            customerWebService.create(createCustomerRequest);
        }
        return userResponse;
    }

    public User getCommonAnonym() {
        UserResponse anonymResponse = userWebService.findByUsername("anonym").get();
        User anonym = new User();
        anonym.id = anonymResponse.id;
        anonym.username = anonymResponse.username;
        anonym.fullName = anonymResponse.fullName;
        anonym.phone = anonymResponse.phone;
        anonym.email = anonymResponse.email;
        anonym.imageURL = anonymResponse.imageURL;
        anonym.roles = anonymResponse.roles;
        anonym.provider = anonymResponse.provider;
        anonym.fields = anonymResponse.fields;
        anonym.status = anonymResponse.status;
        return anonym;
    }

    public UserResponse createUser(CreateDealerUserAJAXRequest createDealerUserAJAXRequest, Request request) {
        CreateUserRequest createUserRequest = createUserRequest(createDealerUserAJAXRequest.username, createDealerUserAJAXRequest.password, null, null);
        createUserRequest.fullName = createDealerUserAJAXRequest.fullName;
        createUserRequest.roles = Lists.newArrayList("dealerUser");
        UserResponse userResponse = userWebService.create(createUserRequest);

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.id = userResponse.id;
        createCustomerRequest.channelId = channelId(request);
        createCustomerRequest.campaignId = campaignId(request);
        createCustomerRequest.source = source(request);
        createCustomerRequest.requestBy = "kdlins-website";
        customerWebService.create(createCustomerRequest);
        return userResponse;
    }

    public UserResponse createUser(CreateDealerAJAXRequest createDealerAJAXRequest, Request request) {
        CreateUserRequest createUserRequest = createUserRequest(createDealerAJAXRequest.username, createDealerAJAXRequest.password, createDealerAJAXRequest.email, createDealerAJAXRequest.phone);
        createUserRequest.roles = Lists.newArrayList("dealerAdmin");
        UserResponse userResponse = userWebService.create(createUserRequest);

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.id = userResponse.id;
        createCustomerRequest.identification = new IdentificationView();
        createCustomerRequest.identification.type = "Id";
        createCustomerRequest.identification.number = createDealerAJAXRequest.identification;
        createCustomerRequest.gender = GenderView.UNDEFINED;
        createCustomerRequest.state = createDealerAJAXRequest.state;
        createCustomerRequest.city = createDealerAJAXRequest.city;
        createCustomerRequest.ward = createDealerAJAXRequest.ward;
        createCustomerRequest.channelId = channelId(request);
        createCustomerRequest.campaignId = campaignId(request);
        createCustomerRequest.source = source(request);
        createCustomerRequest.requestBy = "kdlins-website";
        customerWebService.create(createCustomerRequest);
        return userResponse;
    }

    public UserResponse createUser(RegisterDealerRequest registerDealerRequest, Request request) {
        String username = registerDealerRequest.username;
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.username = username;
        if (createUserRequest.username.contains("@")) {
            createUserRequest.email = username;
        } else {
            createUserRequest.phone = username;
        }
        createUserRequest.password = registerDealerRequest.password;
        createUserRequest.requestBy = "init";
        createUserRequest.roles = Lists.newArrayList("dealerAdmin");
        createUserRequest.status = UserStatusView.AUDITING;
        UserResponse userResponse = userWebService.create(createUserRequest);

        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.id = userResponse.id;
        createCustomerRequest.channelId = channelId(request);
        createCustomerRequest.campaignId = campaignId(request);
        createCustomerRequest.source = source(request);
        customerWebService.create(createCustomerRequest);
        return userResponse;
    }

    public void authenticate(AuthenticationRequest authenticationRequest) {
        userWebService.authenticate(authenticationRequest);
    }

    public List<UserResponse> find(List<String> userIds) {
        return userIds.stream().map(userId -> userWebService.get(userId)).collect(Collectors.toList());
    }

    private String channelId(Request request) {
        return request.cookie("channel-id").map(String::toLowerCase).orElse(null);
    }

    private String campaignId(Request request) {
        return request.cookie("campaign-id").map(String::toLowerCase).orElse(null);
    }

    private String source(Request request) {
        return request.cookie("source").map(String::toLowerCase).orElse(null);
    }

    private UpdateUserRequest updateUserRequest(String fullName, String phone, String email) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.fullName = fullName;
        updateUserRequest.phone = phone;
        updateUserRequest.email = email;
        return updateUserRequest;
    }

    private CreateUserRequest createUserRequest(String username, String password, String email, String cellphone) {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.username = username;
        createUserRequest.password = password;
        createUserRequest.email = email;
        createUserRequest.phone = cellphone;
        createUserRequest.status = UserStatusView.ACTIVE;
        createUserRequest.requestBy = "kdlins-website";
        return createUserRequest;
    }

    private void updateCustomer(String userId, String contactIdNumber) {
        CustomerResponse customerResponse = customerWebService.get(userId);
        UpdateCustomerRequest customerRequest = new UpdateCustomerRequest();
        customerRequest.identification = new IdentificationView();
        customerRequest.identification.type = "Id";
        customerRequest.identification.number = contactIdNumber;
        customerRequest.birthday = customerResponse.birthday;
        customerRequest.gender = customerResponse.gender;
        customerRequest.countryCode = customerResponse.countryCode;
        customerRequest.state = customerResponse.state;
        customerRequest.city = customerResponse.city;
        customerRequest.ward = customerResponse.ward;
        customerRequest.currencyCode = customerResponse.currencyCode;
        customerRequest.requestBy = "kdlins-website";
        customerWebService.update(customerResponse.id, customerRequest);
    }
}
