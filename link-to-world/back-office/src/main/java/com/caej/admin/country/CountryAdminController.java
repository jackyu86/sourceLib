package com.caej.admin.country;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.country.BatchGetCityRequest;
import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.api.country.InsuranceCityResponse;
import com.caej.insurance.api.country.InsuranceProvinceResponse;
import com.caej.insurance.api.country.InsuranceWardResponse;

import io.sited.admin.AdminUser;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * Created by hubery.chen on 2017/1/5.
 */
public class CountryAdminController {
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;

    @Path("/admin/ajax/province/:id")
    @GET
    public List<InsuranceCityResponse> children(Request request) {
        String id = request.pathParam("id");
        return insuranceCountryWebService.city(id);
    }

    @Path("/admin/ajax/city/:id")
    @GET
    public List<InsuranceWardResponse> cityChildren(Request request) {
        String id = request.pathParam("id");
        return insuranceCountryWebService.ward(id);
    }

    @Path("/admin/ajax/province")
    @GET
    public List<InsuranceProvinceResponse> province(Request request) {
        AdminUser adminUser = request.require(AdminUser.class);

        if (adminUser.hasRole("ProvinceAdmin")) {
            List<String> states = adminUser.roles.stream().filter(role -> role.contains("ProvinceAdmin")).map(role -> role.replace("ProvinceAdmin", "")).collect(Collectors.toList());
            FindProvincesRequest findProvincesRequest = new FindProvincesRequest();
            findProvincesRequest.shortNames = states;
            return insuranceCountryWebService.provinces(findProvincesRequest).items;
        }
        return insuranceCountryWebService.provinceByCountryCode("CHN");
    }

    @Path("/admin/ajax/city/batch")
    @PUT
    public List<InsuranceCityResponse> batchGetCity(Request request) {
        BatchGetCityRequest batchGetCityRequest = request.body(BatchGetCityRequest.class);
        return insuranceCountryWebService.batchGetCity(batchGetCityRequest);
    }
}
