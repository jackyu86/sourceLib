package io.sited.file.admin.web;


import io.sited.file.admin.FileAdminConfig;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

import javax.inject.Inject;
import java.util.Collections;

/**
 * @author Jonathan.Guo
 */
public class DirectoryPathAdminController {
    @Inject
    FileAdminConfig fileAdminConfig;

    @Path("/admin/ajax/file/path/suggest")
    @GET
    public Response suggest(Request request) {
        String name = request.queryParam("name").orElse("");
        String path = fileAdminConfig.pathConverter().apply(name);
        return Response.body(Collections.singletonMap("path", path));
    }
}
