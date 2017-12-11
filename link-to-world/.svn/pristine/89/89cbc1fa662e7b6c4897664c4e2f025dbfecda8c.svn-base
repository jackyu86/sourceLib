package com.caej.insurance.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.domain.InsuranceCity;
import com.caej.insurance.domain.InsuranceCountry;
import com.caej.insurance.domain.InsuranceProvince;
import com.caej.insurance.domain.InsuranceWard;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class InsuranceCountryService {
    @Inject
    MongoRepository<InsuranceCity> cityCollection;

    @Inject
    MongoRepository<InsuranceProvince> provinceCollection;

    @Inject
    MongoRepository<InsuranceCountry> countryCollection;

    @Inject
    MongoRepository<InsuranceWard> wardRepository;

    public InsuranceCountry country(ObjectId id) {
        return countryCollection.query(new Document("_id", id)).findOne().get();
    }

    public List<InsuranceCity> findCityByProvinceId(ObjectId provinceId) {
        return cityCollection.query("province_id", provinceId).findMany();
    }

    public List<InsuranceCountry> findCountry() {
        return countryCollection.query().findMany();
    }

    public List<InsuranceProvince> findProvinceByCountryCode(String countryCode) {
        InsuranceCountry country = findCountryByCode(countryCode);
        if (country == null) {
            throw new NotFoundException("missing country, code={}", countryCode);
        }
        return findProvinceByCountryId(country.id);
    }

    private InsuranceCountry findCountryByCode(String countryCode) {
        return countryCollection.query(new Document("country_code", countryCode)).findOne().get();
    }

    public List<InsuranceProvince> findProvinceByCountryId(ObjectId countryId) {
        return provinceCollection.query(new Document("country_id", countryId)).findMany();
    }

    public List<InsuranceWard> findWardByCityId(ObjectId cityId) {
        return wardRepository.query(new Document("city_id", cityId)).findMany();
    }

    public List<InsuranceCity> batchGetCity(List<ObjectId> ids) {
        return cityCollection.query(new Document("_id", new Document("$in", ids))).findMany();
    }

    public Optional<InsuranceProvince> getProvinceByShortName(String shortName) {
        return provinceCollection.query("short_name", shortName).findOne();
    }

    public FindView<InsuranceProvince> find(FindProvincesRequest request) {
        Document filter = new Document();
        if (!request.shortNames.isEmpty()) {
            filter.append("short_name", new Document("$in", request.shortNames));
        }
        if (!request.names.isEmpty()) {
            filter.append("name", new Document("$in", request.names));
        }
        return provinceCollection.query(filter).find();
    }
}
