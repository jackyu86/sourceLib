package com.caej.insurance.api;

import java.util.List;
import com.caej.insurance.api.enumtype.EnumOverManageTypeResponse;
import io.sited.http.GET;
import io.sited.http.Path;

/**
 * @author miller
 */
public interface EnumOverManageTypeWebService {
    @Path("/api/enum/over-manage-type")
    @GET
    List<EnumOverManageTypeResponse> findAll();
}
