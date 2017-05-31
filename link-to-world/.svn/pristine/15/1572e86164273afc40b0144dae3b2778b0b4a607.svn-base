package io.sited.admin.impl.web.ajax;


import io.sited.admin.AdminUser;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;

import java.io.IOException;

/**
 * @author chi
 */
public class AdminUserAJAXController {
    @Path("/admin/ajax/user/self")
    @GET
    public AdminUser self(Request request) throws IOException {
        return request.require(AdminUser.class);
    }
}
