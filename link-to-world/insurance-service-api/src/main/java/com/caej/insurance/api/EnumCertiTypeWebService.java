package com.caej.insurance.api;

import com.caej.insurance.api.enumtype.AllEnumCertiTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumCertiTypeWebService {
    @Path("/api/enum/certi-type")
    @GET
    AllEnumCertiTypeResponse findAll();
}
