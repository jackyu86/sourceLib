package com.caej.insurance.web;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.caej.insurance.InsuranceModuleImpl;

import io.sited.http.ServerResponse;
import io.sited.test.SiteRule;

/**
 * @author chi
 */
public class EnumValueWebServiceImplTest {
    @Rule
    public SiteRule siteRule = new SiteRule(new InsuranceModuleImpl());

    @Test
    public void values() throws Exception {
        ServerResponse response = siteRule.get("/api/enum-type/enum_account_type/values").execute();
        Assert.assertEquals(200, response.statusCode());

        List list = response.body(List.class);
        Assert.assertFalse(list.isEmpty());
    }

}