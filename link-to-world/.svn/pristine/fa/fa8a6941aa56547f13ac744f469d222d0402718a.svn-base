package io.sited.user.admin.controller;


import com.google.common.collect.Maps;
import io.sited.Site;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Response;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author chi
 */
public class UserAdminController {
    @Inject
    Site site;

    @Path("/admin/login")
    @GET
    public Response login() {
        Map<String, Object> context = Maps.newHashMap();
        context.put("site", site);
        return Response.template("/admin/user/login.html", context);
    }
}
