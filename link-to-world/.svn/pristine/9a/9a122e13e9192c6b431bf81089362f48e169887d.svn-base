package io.sited.customer.web.controller;

import io.sited.StandardException;
import io.sited.customer.api.CountryWebService;
import io.sited.customer.api.country.CountryResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class CountryAJAXController {
    @Inject
    CountryWebService countryWebService;

    @Path("/ajax/country")
    @GET
    public List<CountryResponse> countries() {
        return countryWebService.find();
    }

    @Path("/ajax/country/:id")
    @GET
    public CountryResponse country(Request request) {
        String id = request.pathParam("id");
        return countryWebService.get(id);
    }

    @Path("/ajax/country/code/:code")
    @GET
    public CountryResponse findCountryByCode(Request request) {
        String countryCode = request.pathParam("code", String.class);
        Optional<CountryResponse> country = countryWebService.findByCode(countryCode);
        if (!country.isPresent()) {
            throw new StandardException("missing country, code={}", countryCode);
        }
        return country.get();
    }
}
