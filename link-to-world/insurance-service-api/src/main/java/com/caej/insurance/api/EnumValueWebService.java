package com.caej.insurance.api;

import java.util.List;
import com.caej.insurance.api.enumtype.EnumTypeValueRequest;
import com.caej.insurance.api.enumtype.EnumValueResponse;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface EnumValueWebService {
    @Path("/api/enum-type/:name/values")
    @GET
    List<EnumValueResponse> values(@PathParam("name") String name);

    @Path("/api/enum-type/type/:type/values")
    @GET
    List<EnumValueResponse> valuesByType(@PathParam("type") String type);

    @Path("/api/enum-type/:name")
    @POST
    EnumValueResponse create(@PathParam("name") String name, EnumTypeValueRequest request);

    @Path("/api/enum-type/:name/:id")
    @PUT
    EnumValueResponse update(@PathParam("name") String name, @PathParam("id") String id, EnumTypeValueRequest request);

    @Path("/api/enum-type/:name/:id")
    @DELETE
    void delete(@PathParam("name") String name, @PathParam("id") String id);
}
