package io.sited.http.test;

import io.sited.http.POST;
import io.sited.http.Path;

public class TestController {
    @Path("/")
    @POST
    public TestResponse insert(TestRequest request) {
        TestResponse response = new TestResponse();
        response.field = request.field;
        return response;
    }
}
