package io.sited.http.impl;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Response;

/**
 * @author chi
 */
public class HealthCheckController {
    @Path("/health-check")
    @GET
    public Response healthCheck() {
        return Response.empty().setStatusCode(200);
    }
}
