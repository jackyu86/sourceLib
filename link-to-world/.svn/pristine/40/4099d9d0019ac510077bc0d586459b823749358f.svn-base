package com.caej.esb.api;

import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public interface ESBRecordWebService {
    @Path("/api/esb/record")
    @POST
    void create(CreateESBRecordRequest request);

    @Path("/api/esb/record/:serialNumber")
    @GET
    ESBRecordResponse get(@PathParam("serialNumber") String serialNumber);
}
