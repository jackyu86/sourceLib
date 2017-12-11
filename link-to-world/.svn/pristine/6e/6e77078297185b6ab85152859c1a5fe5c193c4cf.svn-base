package com.caej.site.region.web;

import java.util.List;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.country.InsuranceCityResponse;
import com.caej.insurance.api.country.InsuranceProvinceResponse;
import com.caej.insurance.api.country.InsuranceWardResponse;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author chi
 */
public class CountryAJAXController {
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;

    @Path("/ajax/province")
    @GET
    public List<InsuranceProvinceResponse> province() {
        return insuranceCountryWebService.provinceByCountryCode("CHN");
    }

    @Path("/ajax/province/:id")
    @GET
    public List<InsuranceCityResponse> children(Request request) {
        String id = request.pathParam("id");
        return insuranceCountryWebService.city(id);
    }

    @Path("/ajax/province/city/:id")
    @GET
    public List<InsuranceWardResponse> ward(Request request) {
        String id = request.pathParam("id");
        return insuranceCountryWebService.ward(id);
    }
}
