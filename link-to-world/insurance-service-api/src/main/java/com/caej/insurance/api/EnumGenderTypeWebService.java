package com.caej.insurance.api;

import com.caej.insurance.api.enumtype.AllEnumGenderTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumGenderTypeWebService {
    @Path("/api/enum/gender-type")
    @GET
    AllEnumGenderTypeResponse findAll();
}
