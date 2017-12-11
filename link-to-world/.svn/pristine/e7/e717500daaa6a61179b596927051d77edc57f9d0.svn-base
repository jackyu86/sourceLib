package io.sited.web.impl.web;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.http.impl.ContentTypes;
import io.sited.template.TemplateConfig;
import io.sited.util.source.Source;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

/**
 * @author chi
 */
public class WebController {
    @Inject
    TemplateConfig templateConfig;

    @Path("/*")
    @GET
    public Response get(Request request) throws IOException {
        String path = "/".equals(request.path()) ? "/index.html" : request.path();
        Optional<Source> source = templateConfig.repository().get(path);
        if (!source.isPresent()) {
            throw new NotFoundException(request.path());
        }
        return Response.body(source.get().bytes())
            .setContentType(ContentTypes.of(path));
    }

    @Path("/")
    @GET
    public Response index(Request request) throws IOException {
        return get(request);
    }
}
