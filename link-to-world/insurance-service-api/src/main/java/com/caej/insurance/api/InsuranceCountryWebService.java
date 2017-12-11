package com.caej.insurance.api;

import java.util.List;
import java.util.Optional;

import com.caej.insurance.api.country.AllInsuranceCountryResponse;
import com.caej.insurance.api.country.BatchGetCityRequest;
import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.api.country.InsuranceCityResponse;
import com.caej.insurance.api.country.InsuranceProvinceResponse;
import com.caej.insurance.api.country.InsuranceWardResponse;

import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceCountryWebService {

    @Path("/api/country/province/:shortName")
    @GET
    Optional<InsuranceProvinceResponse> provinceByShortName(@PathParam("shortName") String shortName);

    @Path("/api/country/:id/province")
    @GET
    List<InsuranceProvinceResponse> provinces(@PathParam("countryId") String countryId);

    @Path("/api/country/province")
    @PUT
    FindView<InsuranceProvinceResponse> provinces(FindProvincesRequest request);

    @Path("/api/country/:countryCode/province-by-country-code")
    @GET
    List<InsuranceProvinceResponse> provinceByCountryCode(@PathParam("countryCode") String countryCode);

    @Path("/api/country/province/:id/city")
    @GET
    List<InsuranceCityResponse> city(@PathParam("id") String provinceId);

    @Path("/api/country/city/:id/ward")
    @GET
    List<InsuranceWardResponse> ward(@PathParam("id") String cityId);

    @Path("/api/country/all")
    @GET
    AllInsuranceCountryResponse country();

    @Path("/api/country/city/batch")
    @PUT
    List<InsuranceCityResponse> batchGetCity(BatchGetCityRequest batchGetCityRequest);
}
