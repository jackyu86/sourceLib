package io.sited.customer.service;

import io.sited.customer.api.country.CreateCountryRequest;
import io.sited.customer.domain.Country;

import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public interface CountryService {
    List<Country> findAll();

    Country get(String id);

    Optional<Country> findByCode(String countryCode);

    Country create(CreateCountryRequest request);
}
