package com.caej.site.customer.web.ajax;

import io.sited.customer.api.AddressWebService;
import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.address.AddressResponse;
import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.api.address.UpdateAddressRequest;
import io.sited.customer.api.customer.CustomerQuery;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.GenderView;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.db.FindView;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.web.User;

import javax.inject.Inject;
import java.util.List;

/**
 * @author chi
 */
public class CustomerAJAXController {
    @Inject
    CustomerWebService customerWebService;
    @Inject
    UserWebService userWebService;
    @Inject
    AddressWebService addressWebService;

    @Path("/ajax/customer/invite")
    @PUT("GET")
    public Response invites(Request request) {
        CustomerQuery customerQuery = new CustomerQuery();
        customerQuery.channelId = request.queryParam("channelId").get();
        customerQuery.source = request.queryParam("source").get();
        customerQuery.page = 1;
        customerQuery.limit = 20;
        return Response.body(FindView.map(customerWebService.find(customerQuery), (customer -> userWebService.get(customer.id))));
    }

    @Path("/ajax/customer/update")
    @PUT
    public void update(Request request) {
        User user = request.require(User.class);
        CustomerInfoUpdateAJAXRequest updateCustomerRequest = request.body(CustomerInfoUpdateAJAXRequest.class);

        CustomerResponse customerResponse = customerWebService.get(user.id);
        UpdateCustomerRequest customerRequest = customerRequest(customerResponse);

        if (updateCustomerRequest.identification != null) {
            IdentificationView identificationView = new IdentificationView();
            identificationView.number = updateCustomerRequest.identification;
            identificationView.type = updateCustomerRequest.idType;
            customerRequest.identification = identificationView;
        }
        if (updateCustomerRequest.fullName != null) {
            customerRequest.gender = GenderView.valueOf(updateCustomerRequest.gender.name());
            customerRequest.birthday = updateCustomerRequest.birthday;
        }
        customerRequest.state = updateCustomerRequest.state;
        customerRequest.city = updateCustomerRequest.city;
        customerRequest.ward = updateCustomerRequest.ward;
        CustomerResponse customer = customerWebService.update(user.id, customerRequest);

        List<AddressResponse> addressList = addressWebService.findByCustomerId(customerResponse.id);
        if (!addressList.isEmpty()) {
            AddressResponse addressResponse = addressList.get(0);
            UpdateAddressRequest updateAddressRequest = updateAddressRequest(addressResponse);
            updateAddressRequest.state = updateCustomerRequest.state;
            updateAddressRequest.city = updateCustomerRequest.city;
            updateAddressRequest.ward = updateCustomerRequest.ward;
            updateAddressRequest.address1 = updateCustomerRequest.address;
            addressWebService.update(customerResponse.id, addressResponse.id, updateAddressRequest);
        } else {
            CreateAddressRequest createAddressRequest = new CreateAddressRequest();
            createAddressRequest.fullName = user.fullName;
            createAddressRequest.countryCode = "CHN";
            createAddressRequest.state = customer.state;
            createAddressRequest.city = customer.city;
            createAddressRequest.ward = customer.ward;
            createAddressRequest.address1 = updateCustomerRequest.address;
            createAddressRequest.phone = user.phone;
            createAddressRequest.requestBy = "kdlins-website";
            addressWebService.create(customer.id, createAddressRequest);
        }

        UpdateUserRequest updateUserRequest = updateUserRequest(user);
        updateUserRequest.fullName = updateCustomerRequest.fullName;
        updateUserRequest.email = updateCustomerRequest.email;
        updateUserRequest.phone = updateCustomerRequest.phone;
        updateUserRequest.requestBy = user.id;
        userWebService.update(user.id, updateUserRequest);
    }

    private UpdateAddressRequest updateAddressRequest(AddressResponse addressResponse) {
        UpdateAddressRequest updateAddressRequest = new UpdateAddressRequest();
        updateAddressRequest.fullName = addressResponse.fullName;
        updateAddressRequest.countryCode = addressResponse.countryCode;
        updateAddressRequest.state = addressResponse.state;
        updateAddressRequest.city = addressResponse.city;
        updateAddressRequest.ward = addressResponse.ward;
        updateAddressRequest.address1 = addressResponse.address1;
        updateAddressRequest.address2 = addressResponse.address2;
        updateAddressRequest.zipCode = addressResponse.zipCode;
        updateAddressRequest.phone = addressResponse.phone;
        updateAddressRequest.requestBy = "kdlins-website";
        return updateAddressRequest;
    }

    private UpdateUserRequest updateUserRequest(User user) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.fullName = user.fullName;
        updateUserRequest.email = user.email;
        updateUserRequest.phone = user.phone;
        updateUserRequest.imageURL = user.imageURL;
        updateUserRequest.roles = user.roles;
        updateUserRequest.fields = user.fields;
        updateUserRequest.status = user.status;
        updateUserRequest.requestBy = user.id;
        return updateUserRequest;
    }

    private UpdateCustomerRequest customerRequest(CustomerResponse customerResponse) {
        UpdateCustomerRequest customerRequest = new UpdateCustomerRequest();
        customerRequest.identification = customerResponse.identification;
        customerRequest.birthday = customerResponse.birthday;
        customerRequest.gender = customerResponse.gender;
        customerRequest.countryCode = customerResponse.countryCode;
        customerRequest.state = customerResponse.state;
        customerRequest.city = customerResponse.city;
        customerRequest.ward = customerResponse.ward;
        customerRequest.currencyCode = customerResponse.currencyCode;
        customerRequest.requestBy = "kdlins-website";
        return customerRequest;
    }
}
