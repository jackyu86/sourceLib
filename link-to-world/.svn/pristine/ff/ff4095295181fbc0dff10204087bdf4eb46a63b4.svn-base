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
import com.caej.insurance.api.infomation.InsuranceInformationQuestionResponse;
import com.caej.insurance.domain.InsuranceDeclaration;
import com.caej.product.ProductModuleImpl;
import com.mongodb.client.MongoCollection;

import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;

/**
 * @author miller
 */
public class InsuranceDeclarationWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new ProductModuleImpl(), new InsuranceModuleImpl());
    private MongoCollection<InsuranceDeclaration> collection;
    private InsuranceDeclaration defaultNotification;
    private ObjectId id;

    @Before
    public void setUp() throws Exception {
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("insurance_declaration", InsuranceDeclaration.class);
        defaultNotification = new InsuranceDeclaration();
        defaultNotification.question = "new";
        defaultNotification.createdTime = LocalDateTime.now();
        defaultNotification.createdBy = "new";
        collection.insertOne(defaultNotification);
        id = defaultNotification.id;
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
        ServerResponse response = site.get("/api/insurance/declaration/" + id).execute();
        Assert.assertEquals(200, response.statusCode());
        InsuranceInformationQuestionResponse notification = response.body(InsuranceInformationQuestionResponse.class);
        Assert.assertEquals(id, notification.id);
        Assert.assertEquals(defaultNotification.question, notification.question);
        Assert.assertEquals(defaultNotification.createdTime, notification.createdTime);
        Assert.assertEquals(defaultNotification.createdBy, notification.createdBy);
    }

}