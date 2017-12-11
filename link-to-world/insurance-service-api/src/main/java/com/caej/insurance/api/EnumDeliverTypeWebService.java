package com.caej.insurance.api;

import com.caej.insurance.api.enumtype.AllEnumDeliverTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumDeliverTypeWebService {
    @Path("/api/enum/deliver-type")
    @GET
    AllEnumDeliverTypeResponse findAll();
}
