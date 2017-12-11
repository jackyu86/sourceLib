package io.sited.http.impl;

import io.sited.http.Handler;
import io.sited.http.HttpMethod;
import io.sited.http.Request;
import io.sited.http.Route;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @author chi
 */
public class RouterTest {
    @Test
    public void priority() {
        Router router = new Router();
        HandlerRef handlerRef = new HandlerRef();
        handlerRef.handler = new TestHandler();
        handlerRef.httpMethod = HttpMethod.GET;

        router.add("/test/*", handlerRef);

        HandlerRef handlerRef2 = new HandlerRef();
        handlerRef2.httpMethod = HttpMethod.DELETE;
        handlerRef2.handler = new TestHandler();
        router.add("/test/1", handlerRef2);

        Optional<Route> route = router.find(HttpMethod.GET, "/test/1");
        Assert.assertEquals(route.get().handler, handlerRef.handler);
    }

    @Test
    public void variable() {
        Router router = new Router();
        HandlerRef handlerRef = new HandlerRef();
        handlerRef.handler = new TestHandler();
        handlerRef.httpMethod = HttpMethod.DELETE;

        router.add("/ajax/order/:id", handlerRef);

        HandlerRef handlerRef2 = new HandlerRef();
        handlerRef2.httpMethod = HttpMethod.DELETE;
        handlerRef2.handler = new TestHandler();
        router.add("/ajax/order/:paymentId/:id", handlerRef2);

        Optional<Route> route = router.find(HttpMethod.DELETE, "/ajax/order/some");
        Assert.assertEquals(handlerRef.handler, route.get().handler);
    }

    private class TestHandler implements Handler {
        @Override
        public Object handle(Request request) throws Exception {
            return null;
        }
    }
}