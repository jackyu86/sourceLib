package com.caej.site.about.web;

import com.google.common.collect.Maps;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

/**
 * @author Jonathan.Guo
 */
public class AboutController {
    @Path("/about/about")
    @GET
    public Response about(Request request) {
        return Response.template("/about/about.html", Maps.newHashMap());
    }

    @Path("/about/contact")
    @GET
    public Response contact(Request request) {
        return Response.template("/about/contact.html", Maps.newHashMap());
    }

    @Path("/about/law")
    @GET
    public Response law(Request request) {
        return Response.template("/about/law.html", Maps.newHashMap());
    }
    @Path("/about/user-protocol")
    @GET
    public Response userProtocol(Request request) {
        return Response.template("/about/user-protocol.html", Maps.newHashMap());
    }
    @Path("/about/dealer-protocol")
    @GET
    public Response dealerProtocol(Request request) {
        return Response.template("/about/dealer-protocol.html", Maps.newHashMap());
    }
}
