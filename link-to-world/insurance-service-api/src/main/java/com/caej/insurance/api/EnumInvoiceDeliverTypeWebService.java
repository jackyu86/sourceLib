package com.caej.insurance.api;

import com.caej.insurance.api.enumtype.AllEnumInvoiceDeliverTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumInvoiceDeliverTypeWebService {
    @Path("/api/enum/invoice-deliver-type")
    @GET
    AllEnumInvoiceDeliverTypeResponse findAll();

    @Path("/api/enum/invoice-deliver-type/:value/price")
    @GET
    Double price(String value);
}
