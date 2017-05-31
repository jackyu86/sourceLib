package io.sited.customer.api;

import io.sited.customer.api.identification.CustomerIdentificationResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.PathParam;

import java.util.List;

/**
 * @author chi
 */
public interface CustomerIdentificationWebService {
    @GET
    @Path("/api/customer/:customerId/identifications")
    List<CustomerIdentificationResponse> findByCustomerId(@PathParam("customerId") String customerId);
}
