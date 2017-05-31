package com.caej.insurance.api;

import java.util.List;
import com.caej.insurance.api.enumtype.EnumYesOrNotTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumYesOrNotTypeWebService {
    @Path("/api/enum/yes-or-not-type")
    @GET
    List<EnumYesOrNotTypeResponse> findAll();
}
