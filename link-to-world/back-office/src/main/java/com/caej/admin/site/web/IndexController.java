package com.caej.admin.site.web;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

import java.io.IOException;

/**
 * @author chi
 */
public class IndexController {
    @Path("/")
    @GET
    public Response get(Request request) throws IOException {
        return Response.redirect("/admin/");
    }
}
