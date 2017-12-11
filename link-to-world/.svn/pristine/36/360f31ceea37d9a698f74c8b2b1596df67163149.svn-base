package com.caej.insurance.web;

import java.time.LocalDateTime;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.caej.SiteRule;
import com.caej.insurance.InsuranceModuleImpl;
import com.caej.insurance.api.claim.InsuranceClaimResponse;
import com.caej.insurance.domain.InsuranceClaim;
import com.caej.product.ProductModuleImpl;
import com.mongodb.client.MongoCollection;

import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;

/**
 * @author miller
 */
public class InsuranceClaimWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new ProductModuleImpl(), new InsuranceModuleImpl());
    private MongoCollection<InsuranceClaim> collection;
    private InsuranceClaim defaultGuide;
    private ObjectId id;

    @Before
    public void setUp() throws Exception {
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("insurance_claim", InsuranceClaim.class);
        defaultGuide = new InsuranceClaim();
        defaultGuide.name = "name";
        defaultGuide.content = "new";
        defaultGuide.createdBy = "new";
        defaultGuide.createdTime = LocalDateTime.now();
        collection.insertOne(defaultGuide);
        id = defaultGuide.id;
    }

    @After
    public void tearDown() throws Exception {
        Bson document = new Document("_id", id);
        if (collection.count(document) > 0) {
            collection.deleteOne(document);
        }
    }

    @Test
    public void get() throws Exception {
        ServerResponse response = site.get("/api/insurance/claim/" + id).execute();
        Assert.assertEquals(200, response.statusCode());
        InsuranceClaimResponse guide = response.body(InsuranceClaimResponse.class);
        Assert.assertEquals(id, guide.id);
        Assert.assertEquals(defaultGuide.content, guide.content);
        Assert.assertEquals(defaultGuide.createdBy, guide.createdBy);
        Assert.assertEquals(defaultGuide.createdTime, guide.createdTime);
    }
}