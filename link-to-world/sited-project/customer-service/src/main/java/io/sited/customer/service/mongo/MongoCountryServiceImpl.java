package io.sited.customer.service.mongo;

import io.sited.customer.api.country.CreateCountryRequest;
import io.sited.customer.domain.Country;
import io.sited.customer.service.CountryService;
import io.sited.db.MongoRepository;
import io.sited.util.JSON;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;

/**
 * @author chi
 */
public class MongoCountryServiceImpl implements CountryService {
    @Inject
    MongoRepository<Country> repository;

    @Override
    public List<Country> findAll() {
        return repository.query().findMany();
    }

    @Override
    public Country get(String id) {
        return repository.get(id);
    }

    @Override
    public Optional<Country> findByCode(String countryCode) {
        return repository.query("country_code", countryCode).findOne();
    }

    @Override
    public Country create(CreateCountryRequest request) {
        Country country = new Country();
        country.id = UUID.randomUUID().toString();
        country.flagImageURL = request.flagImageURL;
        country.countryCode = request.countryCode;
        country.displayName = request.displayName;
        if (request.currencies != null) {
            country.currencies = JSON.toJSON(request.currencies);
        }
        country.createdTime = now();
        country.createdBy = request.requestBy;
        country.updatedTime = now();
        country.updatedBy = request.requestBy;
        return country;
    }
}
