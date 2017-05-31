package io.sited.http.impl;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author chi
 */
public class RoutePathTest {
    @Test
    public void fill() {
        RoutePath routePath = new RoutePath(null, "/product/:id");
        HashMap<String, String> pathParams = Maps.newHashMap();
        pathParams.put("id", "12345");
        Assert.assertEquals("/product/12345", routePath.fill(pathParams));
    }
}