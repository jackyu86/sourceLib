package io.sited.customer.web.controller;

import io.sited.customer.api.CustomerIdentificationWebService;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class CustomerIdentificationAJAXController {
    @Inject
    CustomerIdentificationWebService customerIdentificationWebService;

    @Path("/ajax/customer/:customerId/identifications")
    @GET
    public Response get(Request request) throws IOException {
        return Response.body(customerIdentificationWebService.findByCustomerId(request.pathParam("customerId")));
    }
}
