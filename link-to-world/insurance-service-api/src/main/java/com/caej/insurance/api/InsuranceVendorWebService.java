package com.caej.insurance.api;

import com.caej.insurance.api.vendor.BatchDeleteInsuranceVendorRequest;
import com.caej.insurance.api.vendor.CreateInsuranceVendorRequest;
import com.caej.insurance.api.vendor.InsuranceVendorQuery;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.insurance.api.vendor.UpdateInsuranceVendorRequest;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface InsuranceVendorWebService {
    @Path("/api/insurance/vendor/:id")
    @GET
    InsuranceVendorResponse get(@PathParam("id") String id);

    @Path("/api/insurance/vendor/find")
    @POST
    FindView<InsuranceVendorResponse> find(InsuranceVendorQuery query);

    @Path("/api/insurance/vendor/:id")
    @DELETE
    void delete(@PathParam("id") String id);

    @Path("/api/insurance/vendor/:id")
    @PUT
    void update(@PathParam("id") String id, UpdateInsuranceVendorRequest request);

    @Path("/api/insurance/vendor")
    @POST
    InsuranceVendorResponse create(CreateInsuranceVendorRequest request);

    @Path("/api/insurance/vendor/delete")
    @PUT
    void batchDelete(BatchDeleteInsuranceVendorRequest request);
}
