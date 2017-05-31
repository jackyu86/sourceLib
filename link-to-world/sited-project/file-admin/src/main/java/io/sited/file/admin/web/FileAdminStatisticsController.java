package io.sited.file.admin.web;

import io.sited.file.admin.web.statistics.FileStatisticsResponse;
import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.FileWebService;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Response;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class FileAdminStatisticsController {
    @Inject
    DirectoryWebService directoryWebService;
    @Inject
    FileWebService fileWebService;

    @Path("/admin/ajax/file/statistics")
    @GET
    public Response statistics() throws IOException {
        FileStatisticsResponse fileStatisticsResponse = new FileStatisticsResponse();
        fileStatisticsResponse.directoryCount = directoryWebService.statistics().count;
        fileStatisticsResponse.fileCount = fileWebService.statistics().count;
        return Response.body(fileStatisticsResponse);
    }
}
