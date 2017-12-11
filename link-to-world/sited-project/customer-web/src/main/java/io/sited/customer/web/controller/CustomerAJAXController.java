package io.sited.customer.web.controller;

import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.user.web.User;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class CustomerAJAXController {
    @Inject
    CustomerWebService customerWebService;

    @Path("/ajax/customer/:customerId")
    @GET
    public CustomerResponse get(Request request) {
        String id = request.pathParam("customerId");
        return customerWebService.get(id);
    }

    @Path("/ajax/customer/self")
    @GET
    public CustomerResponse self(Request request) {
        return customerWebService.get(request.require(User.class).id);
    }


    @Path("/ajax/customer/self")
    @PUT
    public CustomerResponse update(Request request) throws IOException {
        UpdateCustomerRequest customerRequest = request.body(UpdateCustomerRequest.class);
        return customerWebService.update(request.require(User.class).id, customerRequest);
    }
}
