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
import com.caej.insurance.api.vendor.InsuranceVendorQuery;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.insurance.domain.InsuranceVendor;
import com.mongodb.client.MongoCollection;

import io.sited.db.DBModule;
import io.sited.db.FindView;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;

/**
 * @author miller
 */
public class InsuranceVendorWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new InsuranceModuleImpl());
    private MongoCollection<InsuranceVendor> collection;
    private InsuranceVendor defaultInsuranceVendor;
    private ObjectId id;

    @Before
    public void setUp() throws Exception {
        defaultInsuranceVendor = new InsuranceVendor();
        defaultInsuranceVendor.name = "vendor";
        defaultInsuranceVendor.description = "vendorDescription";
        defaultInsuranceVendor.imageURL = "vendorImgUrl";
        defaultInsuranceVendor.createdTime = LocalDateTime.now();
        defaultInsuranceVendor.createdBy = "test";
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("insurance_vendor", InsuranceVendor.class);
        collection.insertOne(defaultInsuranceVendor);
        id = defaultInsuranceVendor.id;
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
        ServerResponse response = site.get("/api/insurance/vendor/" + id.toHexString()).execute();
        Assert.assertEquals(200, response.statusCode());

        InsuranceVendorResponse vendor = response.body(InsuranceVendorResponse.class);

        Assert.assertEquals(defaultInsuranceVendor.name, vendor.name);
        Assert.assertEquals(defaultInsuranceVendor.description, vendor.description);
        Assert.assertEquals(defaultInsuranceVendor.imageURL, vendor.imageURL);
    }

    @Test
    public void find() throws Exception {
        InsuranceVendorQuery query = new InsuranceVendorQuery();
        query.name = defaultInsuranceVendor.name;
        query.page = 1;
        query.limit = 20;
        ServerResponse response = site.post("/api/insurance/vendor/find").body(query).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<InsuranceVendorResponse> findView = response.body(FindView.class);
        Assert.assertEquals(20, (int) findView.limit);
        Assert.assertEquals(1, (int) findView.page);
        InsuranceVendorResponse insuranceVendorResponse = findView.items.get(0);
        Assert.assertEquals(defaultInsuranceVendor.name, insuranceVendorResponse.name);
        Assert.assertEquals(defaultInsuranceVendor.description, insuranceVendorResponse.description);
        Assert.assertEquals(defaultInsuranceVendor.imageURL, insuranceVendorResponse.imageURL);
    }

    @Test
    public void delete() throws Exception {
        ServerResponse response = site.delete("/api/insurance/vendor/" + id.toHexString()).execute();
        Assert.assertEquals(204, response.statusCode());
        Assert.assertEquals(0, collection.count(new Document("_id", id)));
    }

}