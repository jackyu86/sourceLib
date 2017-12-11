package com.caej.admin.insuranceenum;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.EnumTypeWebService;
import com.caej.insurance.api.enumtype.EnumTypeResponse;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public class EnumTypeAdminController {
    @Inject
    EnumTypeWebService enumTypeWebService;

    @Path("/admin/ajax/enum-type")
    @GET
    public List<EnumTypeResponse> findAll() throws IOException {
        return enumTypeWebService.findAll();
    }

    @Path("/admin/ajax/enum-type/:name")
    @GET
    public EnumTypeResponse get(@PathParam("name") String name) throws IOException {
        return enumTypeWebService.get(name);
    }
}
