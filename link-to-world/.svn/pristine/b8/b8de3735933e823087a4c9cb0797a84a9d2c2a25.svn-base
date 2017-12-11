package io.sited.admin.impl.web.ajax;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.i18n.I18nConfig;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class MessageAJAXController {
    @Inject
    I18nConfig i18nConfig;

    @Path("/admin/ajax/i18n/:module")
    @GET
    public Response get(Request request) throws IOException {
        String module = request.pathParam("module");
        return Response.body(i18nConfig.messages(module, request.locale()));
    }
}
