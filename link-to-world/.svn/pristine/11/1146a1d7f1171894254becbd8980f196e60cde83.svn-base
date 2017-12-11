package io.sited.web.impl.web;


import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.http.impl.ContentTypes;
import io.sited.util.source.FolderSourceRepository;
import io.sited.util.source.Source;
import io.sited.web.AssetsConfig;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author chi
 */
public class AssetsController {
    @Inject
    AssetsConfig assetsConfig;

    @Path("/static/*")
    @GET
    public Response resources(Request request) {
        return handle(request);
    }

    @Path("/favicon.ico")
    @GET
    public Response favicon(Request request) {
        return handle(request);
    }

    @Path("/robots.txt")
    @GET
    public Response robots(Request request) {
        return handle(request);
    }

    protected Response handle(Request request) {
        String path = request.path();
        Optional<Source> sourceOptional = assetsConfig.get(path);

        if (!sourceOptional.isPresent()) {
            throw new NotFoundException(request.path());
        }

        Source source = sourceOptional.get();

        if (assetsConfig.isCacheEnabled()) {
            Optional<String> etag = request.header("If-None-Match");
            if (etag.isPresent() && source.hash().equals(etag.get())) {
                return Response.empty().setStatusCode(304);
            }

            if (source instanceof FolderSourceRepository.FileSource) {
                return Response.body(((FolderSourceRepository.FileSource) source).file)
                    .setHeader("Cache-Control", "max-age=" + Integer.MAX_VALUE)
                    .setHeader("Etag", source.hash());
            } else {
                return Response.body(source.bytes()).setContentType(ContentTypes.of(request.path()))
                    .setHeader("Cache-Control", "max-age=" + Integer.MAX_VALUE)
                    .setHeader("Etag", source.hash());
            }
        } else {
            if (source instanceof FolderSourceRepository.FileSource) {
                return Response.body(((FolderSourceRepository.FileSource) source).file);
            } else {
                return Response.body(source.bytes()).setContentType(ContentTypes.of(request.path()));
            }
        }
    }
}
