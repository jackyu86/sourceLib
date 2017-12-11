package com.caej.insurance.api;

import java.util.List;
import com.caej.insurance.api.enumtype.EnumBankTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumBankTypeWebService {
    @Path("/api/enum/bank-type")
    @GET
    List<EnumBankTypeResponse> findAll();
}
