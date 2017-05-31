package com.caej.insurance.api;

import io.sited.http.GET;
import io.sited.http.Path;

import java.util.Map;

/**
 * @author miller
 */
public interface EnumPayModeWebService {
    @Path("/api/enum/pay-mode")
    @GET
    Map<String,String> findAll();
}
