package com.caej.api.esb;

import io.sited.http.POST;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface KdlinsESBWebService {
    @Path("/api/esb")
    @POST
    ESBResponse response(ESBRequest request);
}
