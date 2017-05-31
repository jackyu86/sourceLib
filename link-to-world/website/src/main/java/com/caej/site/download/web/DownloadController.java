package com.caej.site.download.web;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Jonathan.Guo
 */
public class DownloadController {
    @Path("/download/file/:fileName")
    @GET
    public Response file(Request request) throws IOException {
        String path = request.pathParam("fileName");

        File file = new File(this.getClass().getClassLoader().getResource("file/" + path).getPath());
        if (!file.exists()) {
            throw new NotFoundException("not found");
        }
        return Response.body(new FileInputStream(file));
    }
}
