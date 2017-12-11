package io.sited.http.client.local.module.web;

import io.sited.http.Request;
import io.sited.http.Response;

import java.io.IOException;

/**
 * @author chi
 */
public class LocalController {
    public Response get(Request request) throws IOException {
        return Response.empty();
    }
}
