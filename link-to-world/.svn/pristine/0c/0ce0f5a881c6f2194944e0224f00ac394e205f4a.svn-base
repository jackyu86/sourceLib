package io.sited.customer.api;

import io.sited.customer.api.address.AddressResponse;
import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.api.address.UpdateAddressRequest;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

import java.util.List;

/**
 * @author chi
 */
public interface AddressWebService {
    @Path("/api/customer/:customerId/address")
    @GET
    List<AddressResponse> findByCustomerId(@PathParam("customerId") String customerId);

    @Path("/api/customer/:customerId/address/:addressId")
    @GET
    AddressResponse get(@PathParam("customerId") String customerId, @PathParam("addressId") String addressId);

    @Path("/api/customer/:customerId/address")
    @POST
    AddressResponse create(@PathParam("customerId") String customerId, CreateAddressRequest request);

    @Path("/api/customer/:customerId/address/:addressId")
    @PUT
    AddressResponse update(@PathParam("customerId") String customerId, @PathParam("addressId") String addressId, UpdateAddressRequest request);

    @Path("/api/customer/:customerId/address/:addressId")
    @DELETE
    void delete(@PathParam("customerId") String customerId, @PathParam("addressId") String addressId);
}