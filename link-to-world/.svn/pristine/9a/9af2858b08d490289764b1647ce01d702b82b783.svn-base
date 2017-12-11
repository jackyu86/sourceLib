package com.caej.insurance.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.country.AllInsuranceCountryResponse;
import com.caej.insurance.api.country.BatchGetCityRequest;
import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.api.country.InsuranceCityResponse;
import com.caej.insurance.api.country.InsuranceCountryResponse;
import com.caej.insurance.api.country.InsuranceProvinceResponse;
import com.caej.insurance.api.country.InsuranceWardResponse;
import com.caej.insurance.domain.InsuranceCity;
import com.caej.insurance.domain.InsuranceCountry;
import com.caej.insurance.domain.InsuranceProvince;
import com.caej.insurance.service.InsuranceCountryService;

import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.QueryParam;

/**
 * @author chi
 */
public class InsuranceCountryWebServiceImpl implements InsuranceCountryWebService {
    @Inject
    InsuranceCountryService countryService;

    @Override
    public Optional<InsuranceProvinceResponse> provinceByShortName(@PathParam("shortName") String shortName) {
        Optional<InsuranceProvince> provinceOptional = countryService.getProvinceByShortName(shortName);
        if (provinceOptional.isPresent()) {
            return Optional.of(response(provinceOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<InsuranceProvinceResponse> provinces(@QueryParam("countryId") String countryId) {
        InsuranceCountry country = countryService.country(new ObjectId(countryId));
        List<InsuranceProvince> provinces = countryService.findProvinceByCountryId(country.id);
        return provinces.stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public FindView<InsuranceProvinceResponse> provinces(FindProvincesRequest request) {
        return FindView.map(countryService.find(request), this::response);
    }

    @Override
    public List<InsuranceProvinceResponse> provinceByCountryCode(@QueryParam("countryCode") String countryCode) {
        List<InsuranceProvince> provinces = countryService.findProvinceByCountryCode(countryCode);
        return provinces.stream().map(province -> {
            InsuranceProvinceResponse response = new InsuranceProvinceResponse();
            response.id = province.id;
            response.countryId = province.countryId;
            response.name = province.name;
            response.provinceCode = province.provinceCode;
            response.createdBy = province.createdBy;
            response.createdTime = province.createdTime;
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCityResponse> city(@QueryParam("id") String provinceId) {
        List<InsuranceCity> cities = countryService.findCityByProvinceId(new ObjectId(provinceId));

        return cities.stream().map(city -> {
            InsuranceCityResponse response = new InsuranceCityResponse();
            response.cityCode = city.cityCode;
            response.zipCode = city.zipCode;
            response.createdBy = city.createdBy;
            response.createdTime = city.createdTime;
            response.name = city.name;
            response.id = city.id;
            response.provinceId = city.provinceId;
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceWardResponse> ward(@QueryParam("id") String cityId) {
        return countryService.findWardByCityId(new ObjectId(cityId)).stream()
            .map(ward -> {
                InsuranceWardResponse response = new InsuranceWardResponse();
                response.cityId = ward.cityId;
                response.name = ward.name;
                response.wardCode = ward.wardCode;
                response.zipCode = ward.zipCode;
                response.id = ward.id;
                response.createdTime = ward.createdTime;
                response.createdBy = ward.createdBy;
                return response;
            }).collect(Collectors.toList());
    }

    @Override
    public AllInsuranceCountryResponse country() {
        AllInsuranceCountryResponse all = new AllInsuranceCountryResponse();
        all.list = countryService.findCountry().stream().map(insuranceCountry -> {
            InsuranceCountryResponse response = new InsuranceCountryResponse();
            response.countryCode = insuranceCountry.countryCode;
            response.id = insuranceCountry.id;
            response.name = insuranceCountry.name;
            response.createdTime = insuranceCountry.createdTime;
            response.createdBy = insuranceCountry.createdBy;
            return response;
        }).collect(Collectors.toList());
        return all;
    }

    @Override
    public List<InsuranceCityResponse> batchGetCity(BatchGetCityRequest batchGetCityRequest) {
        return countryService.batchGetCity(batchGetCityRequest.cityIds).stream().map(city -> {
            InsuranceCityResponse response = new InsuranceCityResponse();
            response.cityCode = city.cityCode;
            response.zipCode = city.zipCode;
            response.createdBy = city.createdBy;
            response.createdTime = city.createdTime;
            response.name = city.name;
            response.id = city.id;
            response.provinceId = city.provinceId;
            return response;
        }).collect(Collectors.toList());
    }

    private InsuranceProvinceResponse response(InsuranceProvince province) {
        InsuranceProvinceResponse response = new InsuranceProvinceResponse();
        response.id = province.id;
        response.countryId = province.countryId;
        response.name = province.name;
        response.provinceCode = province.provinceCode;
        response.createdBy = province.createdBy;
        response.createdTime = province.createdTime;
        return response;
    }

}
