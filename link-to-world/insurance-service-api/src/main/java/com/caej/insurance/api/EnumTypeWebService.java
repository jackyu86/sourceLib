package com.caej.insurance.api;

import java.util.List;
import com.caej.insurance.api.enumtype.EnumTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface EnumTypeWebService {
    @Path("/api/enum-type")
    @GET
    List<EnumTypeResponse> findAll();

    @Path("/api/enum-type/:name")
    @GET
    EnumTypeResponse get(@PathParam("name") String name);
}
