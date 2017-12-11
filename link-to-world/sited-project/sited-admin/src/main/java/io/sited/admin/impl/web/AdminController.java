package io.sited.admin.impl.web;

import com.google.common.collect.Maps;
import io.sited.admin.Console;
import io.sited.admin.impl.service.ConsoleScriptBuilder;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.web.impl.web.AssetsController;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

/**
 * @author chi
 */
public class AdminController extends AssetsController {
    @Inject
    Console console;

    @Path("/admin/")
    @GET
    public Response console() {
        Map<String, Object> context = Maps.newHashMap();
        ConsoleScriptBuilder b = new ConsoleScriptBuilder(console);
        context.put("consoleScript", b.build());
        return Response.template("/admin/index.html", context);
    }

    @Path("/admin")
    @GET
    public Response admin() {
        return Response.redirect("/admin/");
    }

    @Path("/admin/*")
    @GET
    public Response assets(Request request) throws IOException {
        return handle(request);
    }
}
