package com.caej.insurance.web;

import java.time.LocalDateTime;
import java.util.Optional;

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
import com.caej.insurance.api.subject.InsuranceSubjectQuery;
import com.caej.insurance.api.subject.InsuranceSubjectResponse;
import com.caej.insurance.domain.InsuranceSubject;
import com.caej.product.ProductModuleImpl;
import com.mongodb.client.MongoCollection;

import io.sited.db.DBModule;
import io.sited.db.FindView;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;

/**
 * @author miller
 */
public class InsuranceSubjectWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new ProductModuleImpl(), new InsuranceModuleImpl());
    private MongoCollection<InsuranceSubject> collection;
    private InsuranceSubject defaultTarget;
    private ObjectId id;

    @Before
    public void setUp() throws Exception {
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("insurance_subject", InsuranceSubject.class);
        defaultTarget = new InsuranceSubject();
        defaultTarget.name = "default";
        defaultTarget.description = "default";
        defaultTarget.createdBy = "default";
        defaultTarget.createdTime = LocalDateTime.now();
        collection.insertOne(defaultTarget);
        id = defaultTarget.id;
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
        ServerResponse response = site.get("/api/insurance/subject/" + id).execute();
        Assert.assertEquals(200, response.statusCode());
        Optional<InsuranceSubjectResponse> targetOptional = response.body(Optional.class);
        Assert.assertTrue(targetOptional.isPresent());
        InsuranceSubjectResponse target = targetOptional.get();
        Assert.assertEquals(defaultTarget.name, target.name);
        Assert.assertEquals(defaultTarget.description, target.description);
        Assert.assertEquals(defaultTarget.id, target.id);
        Assert.assertEquals(defaultTarget.createdBy, target.createdBy);
    }

    @Test
    public void find() throws Exception {
        InsuranceSubjectQuery query = new InsuranceSubjectQuery();
        query.page = 1;
        query.limit = 20;
        query.name = defaultTarget.name;
        ServerResponse response = site.post("/api/insurance/subject/find").body(query).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<InsuranceSubjectResponse> findView = response.body(FindView.class);
        Assert.assertEquals(1, (int) findView.page);
        Assert.assertEquals(20, (int) findView.limit);
        InsuranceSubjectResponse target = findView.items.get(0);
        Assert.assertEquals(id, target.id);
        Assert.assertEquals(defaultTarget.name, target.name);
        Assert.assertEquals(defaultTarget.description, target.description);
    }

    @Test
    public void delete() throws Exception {
        ServerResponse response = site.delete("/api/insurance/subject/" + id).execute();
        Assert.assertEquals(204, response.statusCode());
        Assert.assertEquals(0, collection.count(new Document("_id", id)));
    }

}