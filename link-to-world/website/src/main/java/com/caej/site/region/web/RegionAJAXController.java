package com.caej.site.region.web;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceAreaWebService;
import com.caej.insurance.api.area.InsuranceAreaResponse;

import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author chi
 */
public class RegionAJAXController {
    @Inject
    InsuranceAreaWebService insuranceAreaWebService;

    @Path("/ajax/region")
    @GET
    public List<InsuranceAreaResponse> all() {
        return insuranceAreaWebService.find();
    }
}
