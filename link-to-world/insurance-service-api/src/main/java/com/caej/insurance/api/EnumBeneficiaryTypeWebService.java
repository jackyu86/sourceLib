package com.caej.insurance.api;

import com.caej.insurance.api.enumtype.AllEnumBeneficiaryTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumBeneficiaryTypeWebService {
    @Path("/api/enum/beneficiary-type")
    @GET
    AllEnumBeneficiaryTypeResponse findAll();
}
