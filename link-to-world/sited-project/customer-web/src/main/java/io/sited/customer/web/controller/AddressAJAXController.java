package io.sited.customer.web.controller;

import io.sited.customer.api.AddressWebService;
import io.sited.customer.api.address.AddressResponse;
import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.api.address.UpdateAddressRequest;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.user.web.User;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @author chi
 */
public class AddressAJAXController {
    @Inject
    AddressWebService addressWebService;

    @Path("/ajax/customer/self/address")
    @GET
    public List<AddressResponse> findByCustomerId(Request request) {
        return addressWebService.findByCustomerId(request.require(User.class).id);
    }

    @Path("/ajax/customer/self/address")
    @POST
    public AddressResponse create(Request request) throws IOException {
        CreateAddressRequest addressRequest = request.body(CreateAddressRequest.class);
        return addressWebService.create(request.require(User.class).id, addressRequest);
    }

    @Path("/ajax/customer/self/address/:addressId")
    @GET
    public AddressResponse findOneById(Request request) {
        String addressId = request.pathParam("addressId");
        return addressWebService.get(request.require(User.class).id, addressId);
    }

    @Path("/ajax/customer/self/address/:addressId")
    @PUT
    public AddressResponse update(Request request) throws IOException {
        String addressId = request.pathParam("addressId");
        UpdateAddressRequest addressRequest = request.body(UpdateAddressRequest.class);
        return addressWebService.update(request.require(User.class).id, addressId, addressRequest);
    }

    @Path("/ajax/customer/self/address/:addressId")
    @DELETE
    public void delete(Request request) {
        String addressId = request.pathParam("addressId");
        addressWebService.delete(request.require(User.class).id, addressId);
    }
}
