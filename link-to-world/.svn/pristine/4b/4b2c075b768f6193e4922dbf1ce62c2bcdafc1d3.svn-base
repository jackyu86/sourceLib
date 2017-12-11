package com.caej.insurance.web;

import java.time.LocalDateTime;
import java.util.List;

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
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.insurance.api.category.InsuranceCategoryQuery;
import com.caej.insurance.api.category.InsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryResponse;
import com.caej.insurance.domain.InsuranceCategory;
import com.caej.product.ProductModuleImpl;
import com.mongodb.client.MongoCollection;

import io.sited.db.DBModule;
import io.sited.db.FindView;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;

/**
 * @author miller
 */
public class InsuranceCategoryWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new ProductModuleImpl(), new InsuranceModuleImpl());
    private MongoCollection<InsuranceCategory> collection;
    private InsuranceCategory defaultCategory;
    private InsuranceCategory child;
    private ObjectId id;

    @Before
    public void setup() throws Exception {
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("insurance_category", InsuranceCategory.class);
        defaultCategory = new InsuranceCategory();
        defaultCategory.name = "ca";
        defaultCategory.displayOrder = 1;
        defaultCategory.recommended = true;
        defaultCategory.description = "description";
        defaultCategory.createdBy = "default";
        defaultCategory.updatedBy = "default";
        defaultCategory.createdTime = LocalDateTime.now();
        defaultCategory.updatedTime = LocalDateTime.now();
        collection.insertOne(defaultCategory);
        id = defaultCategory.id;
        child = new InsuranceCategory();
        child.name = "child";
        child.displayOrder = 2;
        child.recommended = false;
        child.description = "child";
        child.parentId = id;
        child.createdBy = "child";
        child.updatedBy = "child";
        child.createdTime = LocalDateTime.now();
        child.updatedTime = LocalDateTime.now();
        collection.insertOne(child);
    }

    @After
    public void teardown() throws Exception {
        Bson document = new Document("_id", id);
        if (collection.count(document) > 0) {
            collection.deleteOne(document);
        }
    }

    @Test
    public void find() throws Exception {
        InsuranceCategoryQuery query = new InsuranceCategoryQuery();
        query.page = 1;
        query.limit = 20;
        query.name = defaultCategory.name;
        ServerResponse response = site.post("/api/insurance/category/find").body(query).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<InsuranceCategoryResponse> findView = response.body(FindView.class);
        Assert.assertEquals(1, (int) findView.page);
        Assert.assertEquals(20, (int) findView.limit);
        InsuranceCategoryResponse category = findView.items.get(0);
        Assert.assertEquals(id, category.id);
        Assert.assertEquals(defaultCategory.name, category.name);
        Assert.assertEquals(defaultCategory.displayOrder, category.displayOrder);
        Assert.assertEquals(defaultCategory.recommended, category.recommended);
        Assert.assertEquals(defaultCategory.description, category.description);
        Assert.assertEquals(defaultCategory.createdBy, category.createdBy);
        Assert.assertEquals(defaultCategory.updatedBy, category.updatedBy);
    }

    @Test
    public void create() throws Exception {
        InsuranceCategoryRequest request = new InsuranceCategoryRequest();
        request.name = "new";
        request.displayOrder = 1;
        request.recommended = true;
        request.description = "des";
        request.requestBy = "create";
        ServerResponse response = site.post("/api/insurance/category").body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        InsuranceCategoryResponse category = response.body(InsuranceCategoryResponse.class);
        Assert.assertNotNull(category.id);
        Assert.assertEquals(request.name, category.name);
        Assert.assertEquals(request.displayOrder, category.displayOrder);
        Assert.assertEquals(request.recommended, category.recommended);
        Assert.assertEquals(request.description, category.description);
        Assert.assertEquals(request.requestBy, category.createdBy);
        Assert.assertEquals(request.requestBy, category.updatedBy);
    }

    @Test
    public void update() throws Exception {
        InsuranceCategoryRequest request = new InsuranceCategoryRequest();
        request.name = "newName";
        request.displayOrder = 2;
        request.recommended = false;
        request.description = "newDes";
        request.requestBy = "udpate";
        ServerResponse response = site.put("/api/insurance/category/" + id).body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        InsuranceCategoryResponse category = response.body(InsuranceCategoryResponse.class);
        Assert.assertEquals(id, category.id);
        Assert.assertEquals(request.name, category.name);
        Assert.assertEquals(request.displayOrder, category.displayOrder);
        Assert.assertEquals(request.recommended, category.recommended);
        Assert.assertEquals(request.description, category.description);
        Assert.assertEquals(request.requestBy, category.updatedBy);
        response = site.get("/api/insurance/category/" + id).execute();
        Assert.assertEquals(200, response.statusCode());
        category = response.body(InsuranceCategoryResponse.class);
        Assert.assertEquals(defaultCategory.id, category.id);
        Assert.assertNotEquals(defaultCategory.name, category.name);
        Assert.assertNotEquals(defaultCategory.displayOrder, category.displayOrder);
        Assert.assertNotEquals(defaultCategory.description, category.description);
        Assert.assertNotEquals(defaultCategory.recommended, category.recommended);
        Assert.assertEquals(defaultCategory.createdBy, category.createdBy);
        Assert.assertNotEquals(defaultCategory.updatedBy, category.updatedBy);
    }

    @Test
    public void delete() throws Exception {
        ServerResponse response = site.delete("/api/insurance/category/" + id).execute();
        Assert.assertEquals(204, response.statusCode());
        Assert.assertEquals(0, collection.count(new Document("_id", id)));
    }

    @Test
    public void tree() throws Exception {
        ServerResponse response = site.get("/api/insurance/category/tree").execute();
        Assert.assertEquals(200, response.statusCode());
        List<InsuranceCategoryNodeResponse> list = response.body(List.class);
        InsuranceCategoryNodeResponse node = new InsuranceCategoryNodeResponse();
        for (InsuranceCategoryNodeResponse insuranceCategoryNodeResponse : list) {
            if (insuranceCategoryNodeResponse.category.id.equals(id)) {
                node = insuranceCategoryNodeResponse;
            }
        }
        InsuranceCategoryResponse parent = node.category;
        Assert.assertEquals(defaultCategory.name, parent.name);
        Assert.assertEquals(defaultCategory.displayOrder, parent.displayOrder);
        List<InsuranceCategoryNodeResponse> children = node.children;
        InsuranceCategoryResponse childNode = children.get(0).category;
        Assert.assertEquals(child.id, childNode.id);
        Assert.assertEquals(child.name, childNode.name);
        Assert.assertEquals(child.displayOrder, childNode.displayOrder);
    }

}