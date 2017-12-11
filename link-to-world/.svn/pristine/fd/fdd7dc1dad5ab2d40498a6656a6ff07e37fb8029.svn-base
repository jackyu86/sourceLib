package com.caej.insurance.web;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.caej.insurance.InsuranceModuleImpl;
import com.caej.insurance.api.enumtype.EnumTypeResponse;

import io.sited.http.ServerResponse;
import io.sited.test.SiteRule;

/**
 * @author chi
 */
public class EnumTypeWebServiceImplTest {
    @Rule
    public SiteRule siteRule = new SiteRule(new InsuranceModuleImpl());

    @Test
    public void values() throws Exception {
        ServerResponse response = siteRule.get("/api/enum-type").execute();
        Assert.assertEquals(200, response.statusCode());
        List<EnumTypeResponse> results = response.body(List.class);
        Assert.assertNotNull(results);
    }
}