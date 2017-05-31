package com.caej.product.service.client;

import java.util.List;
import java.util.Optional;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.country.AllInsuranceCountryResponse;
import com.caej.insurance.api.country.BatchGetCityRequest;
import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.api.country.InsuranceCityResponse;
import com.caej.insurance.api.country.InsuranceProvinceResponse;
import com.caej.insurance.api.country.InsuranceWardResponse;

import io.sited.cache.Cache;
import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceCountryWebServiceClient implements InsuranceCountryWebService {
    private final Cache<AllInsuranceCountryResponse> cache;
    private final InsuranceCountryWebService insuranceCountryWebService;

    public InsuranceCountryWebServiceClient(Cache<AllInsuranceCountryResponse> countryCache, InsuranceCountryWebService insuranceCountryWebService) {
        this.cache = countryCache;
        this.insuranceCountryWebService = insuranceCountryWebService;
    }

    @Override
    public Optional<InsuranceProvinceResponse> provinceByShortName(@PathParam("shortName") String shortName) {
        return insuranceCountryWebService.provinceByShortName(shortName);
    }

    @Override
    public List<InsuranceProvinceResponse> provinces(@PathParam("countryId") String countryId) {
        return insuranceCountryWebService.provinces(countryId);
    }

    @Override
    public FindView<InsuranceProvinceResponse> provinces(FindProvincesRequest request) {
        return insuranceCountryWebService.provinces(request);
    }

    @Override
    public List<InsuranceProvinceResponse> provinceByCountryCode(@PathParam("countryCode") String countryCode) {
        return insuranceCountryWebService.provinceByCountryCode(countryCode);
    }

    @Override
    public List<InsuranceCityResponse> city(@PathParam("id") String provinceId) {
        return insuranceCountryWebService.city(provinceId);
    }

    @Override
    public List<InsuranceWardResponse> ward(@PathParam("id") String cityId) {
        return insuranceCountryWebService.ward(cityId);
    }

    @Override
    public AllInsuranceCountryResponse country() {
        final String prefix = "all";
        Optional<AllInsuranceCountryResponse> optional = cache.get(prefix);
        if (optional.isPresent()) return optional.get();
        AllInsuranceCountryResponse country = insuranceCountryWebService.country();
        cache.put(prefix, country);
        return country;
    }

    @Override
    public List<InsuranceCityResponse> batchGetCity(BatchGetCityRequest batchGetCityRequest) {
        return insuranceCountryWebService.batchGetCity(batchGetCityRequest);
    }
}
