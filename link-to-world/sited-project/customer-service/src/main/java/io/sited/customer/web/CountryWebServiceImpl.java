package io.sited.customer.web;

import io.sited.customer.api.CountryWebService;
import io.sited.customer.api.country.CountryResponse;
import io.sited.customer.domain.Country;
import io.sited.customer.service.CountryService;
import io.sited.util.JSON;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class CountryWebServiceImpl implements CountryWebService {
    @Inject
    CountryService countryService;

    @Override
    public List<CountryResponse> find() {
        return countryService.findAll().stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public CountryResponse get(String id) {
        return response(countryService.get(id));
    }

    @Override
    public Optional<CountryResponse> findByCode(String code) {
        Optional<Country> countryOptional = countryService.findByCode(code);
        countryOptional.ifPresent(country -> Optional.of(response(country)));
        return Optional.empty();
    }

    private CountryResponse response(Country country) {
        CountryResponse response = new CountryResponse();
        response.id = country.id;
        response.flagImageURL = country.flagImageURL;
        response.countryCode = country.countryCode;
        response.displayName = country.displayName;

        if (country.currencies != null) {
            response.currencies = JSON.fromJSON(country.currencies, List.class);
        }

        response.createdTime = country.createdTime;
        response.createdBy = country.createdBy;
        response.updatedTime = country.updatedTime;
        response.updatedBy = country.updatedBy;
        return response;
    }
}
