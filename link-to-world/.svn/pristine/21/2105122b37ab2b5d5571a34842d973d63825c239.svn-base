package io.sited.file.site.web;


import io.sited.file.api.FileConfig;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author chi
 */
public class FileController {
    @Inject
    FileConfig fileConfig;

    @Path("/file/*")
    @GET
    public Response file(Request request) throws IOException {
        String path = request.path().substring("/file".length());
        Optional<InputStream> inputStream = fileConfig.repository().get(path);
        if (!inputStream.isPresent()) {
            throw new NotFoundException(request.path());
        }
        return Response.body(inputStream.get());
    }
}
