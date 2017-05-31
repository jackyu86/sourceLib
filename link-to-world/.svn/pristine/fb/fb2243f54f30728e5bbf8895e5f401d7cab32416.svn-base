package io.sited.customer.api;

import io.sited.customer.api.country.CountryResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.PathParam;

import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public interface CountryWebService {
    @Path("/api/country")
    @GET
    List<CountryResponse> find();

    @Path("/api/country/:id")
    @GET
    CountryResponse get(@PathParam("id") String id);

    @Path("/api/country/code/:code")
    @GET
    Optional<CountryResponse> findByCode(@PathParam("code") String code);
}

