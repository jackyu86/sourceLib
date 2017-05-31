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
import com.caej.insurance.api.clause.InsuranceClauseQuery;
import com.caej.insurance.api.clause.InsuranceClauseResponse;
import com.caej.insurance.domain.InsuranceClause;
import com.caej.product.ProductModuleImpl;
import com.mongodb.client.MongoCollection;

import io.sited.db.DBModule;
import io.sited.db.FindView;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;

/**
 * @author miller
 */
public class InsuranceClauseWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new ProductModuleImpl(), new InsuranceModuleImpl());
    private MongoCollection<InsuranceClause> collection;
    private InsuranceClause insuranceClause;
    private ObjectId id;

    @Before
    public void setUp() throws Exception {
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("insurance_clause", InsuranceClause.class);
        insuranceClause = new InsuranceClause();
        insuranceClause.serialNumber = "default";
        insuranceClause.contentURL = "default";
        insuranceClause.name = "default";
        insuranceClause.wordURL = "default";
        insuranceClause.pdfURL = "default";
        insuranceClause.createdBy = "default";
        insuranceClause.createdTime = LocalDateTime.now();
        collection.insertOne(insuranceClause);
        id = insuranceClause.id;
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
        ServerResponse response = site.get("/api/insurance/clause/" + id).execute();
        Assert.assertEquals(200, response.statusCode());
        InsuranceClauseResponse terms = response.body(InsuranceClauseResponse.class);
        Assert.assertEquals(id, terms.id);
        Assert.assertEquals(insuranceClause.serialNumber, terms.serialNumber);
        Assert.assertEquals(insuranceClause.contentURL, terms.contentURL);
    }

    @Test
    public void find() throws Exception {
        InsuranceClauseQuery query = new InsuranceClauseQuery();
        query.page = 1;
        query.limit = 20;
        query.name = "default";
        query.serialNumber = "default";
        ServerResponse response = site.post("/api/insurance/clause/find").body(query).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<InsuranceClauseResponse> findView = response.body(FindView.class);
        InsuranceClauseResponse terms = findView.items.get(0);
        Assert.assertEquals(id, terms.id);
        Assert.assertEquals(insuranceClause.serialNumber, terms.serialNumber);
        Assert.assertEquals(insuranceClause.contentURL, terms.contentURL);
    }

    @Test
    public void delete() throws Exception {
        ServerResponse response = site.delete("/api/insurance/clause/" + id).execute();
        Assert.assertEquals(204, response.statusCode());
        Assert.assertEquals(0, collection.count(new Document("_id", id)));
    }

    @Test
    public void list() throws Exception {
       /* BatchGetInsuranceTermRequest request = new BatchGetInsuranceTermRequest();
        request.ids = Lists.newArrayList(id);
        LocalResponse response = localRule.post("/api/insurance/term/batch").body(request).execute();
        Assert.assertEquals(200, response.statusCode);
        List<InsuranceTermResponse> list = response.body();
        InsuranceTermResponse terms = list.get(0);
        Assert.assertEquals(id, terms.id);
        Assert.assertEquals(defaultTerms.contentURL, terms.contentURL);*/
    }

}