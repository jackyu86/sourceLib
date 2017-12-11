package io.sited.customer.api;

import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.CustomerQuery;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface CustomerWebService {
    @Path("/api/customer/:customerId")
    @GET
    CustomerResponse get(@PathParam("customerId") String id);

    @Path("/api/customer")
    @POST
    CustomerResponse create(CreateCustomerRequest request);

    @Path("/api/customer")
    @PUT
    FindView<CustomerResponse> find(CustomerQuery query);

    @Path("/api/customer/:customerId")
    @PUT
    CustomerResponse update(@PathParam("customerId") String id, UpdateCustomerRequest request);

    @Path("/api/customer/:customerId")
    @DELETE
    void delete(@PathParam("customerId") String id);
}
