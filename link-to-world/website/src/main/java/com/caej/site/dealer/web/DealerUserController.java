package com.caej.site.dealer.web;

import com.google.common.collect.Maps;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UserResponse;

import javax.inject.Inject;
import java.util.Map;

/**
 * @author miller
 */
public class DealerUserController {
    @Inject
    UserWebService userWebService;

    @Path("/account/dealer/user/create")
    @GET
    public Response create(Request request) {
        return Response.template("/user/dealer/dealer-user-create.html", Maps.newHashMap());
    }

    @Path("/account/dealer/user/:id/update")
    @GET
    public Response update(Request request) throws Exception {
        String userId = request.pathParam("id");
        UserResponse userResponse = userWebService.get(userId);
        Map<String, Object> data = Maps.newHashMap();
        data.put("user", userResponse);
        return Response.template("/user/dealer/dealer-user-update.html", data);
    }
}
