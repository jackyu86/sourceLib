package com.caej.admin.insuranceenum;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.EnumValueWebService;
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
public class EnumValueAdminController {
    @Inject
    EnumValueWebService enumValueWebService;

    @Path("/admin/ajax/enum-type/:name/values")
    @GET
    public List<EnumValueResponse> values(@PathParam("name") String name) {
        return enumValueWebService.values(name);
    }

    @Path("/admin/ajax/enum-type/type/:type/values")
    @GET
    public List<EnumValueResponse> valuesByType(@PathParam("type") String type) {
        return enumValueWebService.valuesByType(type);
    }

    @Path("/admin/ajax/enum-type/:name")
    @POST
    public EnumValueResponse create(@PathParam("name") String name, EnumTypeValueRequest request) {
        return enumValueWebService.create(name, request);
    }

    @Path("/admin/ajax/enum-type/:name/:id")
    @PUT
    public EnumValueResponse update(@PathParam("name") String name, @PathParam("id") String id, EnumTypeValueRequest request) {
        return enumValueWebService.update(name, id, request);
    }

    @Path("/admin/ajax/enum-type/:name/:id")
    @DELETE
    public void delete(@PathParam("name") String name, @PathParam("id") String id) {
        enumValueWebService.delete(name, id);
    }
}
