package app.dealer.dealer.web;

import app.dealer.DealerModuleImpl;
import app.dealer.SiteRule;
import app.dealer.api.dealer.CreateDealerRequest;
import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerStatusView;
import app.dealer.api.dealer.SearchDealerRequest;
import app.dealer.api.dealer.UpdateDealerRequest;
import app.dealer.dealer.domain.Dealer;
import app.dealer.dealer.domain.DealerLevel;
import app.dealer.dealer.domain.DealerStatus;
import com.mongodb.client.MongoCollection;
import io.sited.db.DBModule;
import io.sited.db.FindView;
import io.sited.db.MongoConfig;
import io.sited.http.ServerResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author miller
 */
public class DealerWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new DealerModuleImpl());
    private Dealer defaultDealer;
    private ObjectId id;
    private MongoCollection<Dealer> collection;

    @Before
    public void setUp() throws Exception {
        defaultDealer = new Dealer();
        defaultDealer.name = "testDealer";
        defaultDealer.email = "1@2.c";
        defaultDealer.contactName = "contactMan";
        defaultDealer.level = DealerLevel.LEVEL1;
        defaultDealer.status = DealerStatus.ACTIVE;
        defaultDealer.createdBy = "test";
        defaultDealer.createdTime = LocalDateTime.now();
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("dealer", Dealer.class);
        collection.insertOne(defaultDealer);
        id = defaultDealer.id;
    }

    @After
    public void tearDown() throws Exception {
        collection.deleteOne(new Document("_id", id));
    }

    @Test
    public void create() throws Exception {
        CreateDealerRequest request = new CreateDealerRequest();
        request.name = "newDealer";
        request.level = DealerLevelView.LEVEL1;
        request.requestBy = "test";
        request.status = DealerStatusView.ACTIVE;
        ServerResponse response = site.post("/api/dealer").body(request).execute();

        Assert.assertEquals(200, response.statusCode());
        DealerResponse dealer = response.body(DealerResponse.class);
        Assert.assertNotNull(dealer.id);
        Assert.assertEquals(request.name, dealer.name);
        Assert.assertEquals(request.level, dealer.level);
        Assert.assertEquals(DealerStatusView.ACTIVE, dealer.status);
    }

    @Test
    public void get() throws Exception {
        ServerResponse response = site.get("/api/dealer/" + id.toHexString()).execute();
        Assert.assertEquals(200, response.statusCode());

        Optional<DealerResponse> dealerOptional = response.body(Optional.class);
        Assert.assertTrue(dealerOptional.isPresent());
        DealerResponse dealer = dealerOptional.get();

        Assert.assertEquals(defaultDealer.name, dealer.name);
        Assert.assertEquals(defaultDealer.email, dealer.email);
        Assert.assertEquals(defaultDealer.contactName, dealer.contactName);
        Assert.assertEquals(defaultDealer.level.name(), dealer.level.name());
        Assert.assertEquals(defaultDealer.status.name(), dealer.status.name());
    }

    @Test
    public void update() throws Exception {
        UpdateDealerRequest request = new UpdateDealerRequest();
        request.name = "newDealerName";
        request.email = defaultDealer.email;
        request.contactName = defaultDealer.contactName;
        request.level = DealerLevelView.LEVEL1;
        request.requestBy = "test";

        ServerResponse localResponse = site.put("/api/dealer/" + defaultDealer.id).body(request).execute();
        Assert.assertEquals(200, localResponse.statusCode());
        DealerResponse response = localResponse.body(DealerResponse.class);
        Assert.assertNotEquals(defaultDealer.name, response.name);
    }

    @Test
    public void delete() throws Exception {
        ServerResponse localResponse = site.delete("/api/dealer/" + defaultDealer.id).execute();
        Assert.assertEquals(204, localResponse.statusCode());
    }

    /*@Test
    public void approve() throws Exception {
        ServerResponse localResponse = site.get("/api/dealer/" + defaultDealer.id + "/approve").execute();
        Assert.assertEquals(204, localResponse.statusCode());
        Dealer dealer = collection.find(new Document("_id", defaultDealer.id)).first();
        Assert.assertEquals(DealerStatus.ACTIVE, dealer.status);
    }*/

    @Test
    public void children() throws Exception {
        CreateDealerRequest createChild = new CreateDealerRequest();
        createChild.name = "child";
        createChild.level = DealerLevelView.LEVEL2;
        createChild.requestBy = "test";
        createChild.parentDealerId = defaultDealer.id.toHexString();
        createChild.status = DealerStatusView.ACTIVE;
        ServerResponse childResponse = site.post("/api/dealer").body(createChild).execute();
        DealerResponse child = childResponse.body(DealerResponse.class);
        CreateDealerRequest createGrandChild = new CreateDealerRequest();
        createGrandChild.name = "grandChild";
        createGrandChild.level = DealerLevelView.LEVEL3;
        createGrandChild.requestBy = "test";
        createGrandChild.parentDealerId = child.id;
        createGrandChild.status = DealerStatusView.ACTIVE;
        ServerResponse grandChildResponse = site.post("/api/dealer").body(createChild).execute();
        DealerResponse grandChild = grandChildResponse.body(DealerResponse.class);
        SearchDealerRequest request = new SearchDealerRequest();
        request.page = 1;
        request.limit = 10;
        request.parentId = defaultDealer.id.toHexString();
        ServerResponse response = site.put("/api/dealer/children").body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<DealerResponse> findView = response.body(FindView.class);
        Assert.assertEquals(2, findView.items.size());
        DealerResponse grandActual = findView.items.get(0);
        Assert.assertEquals(grandChild.id, grandActual.id);
        DealerResponse childActual = findView.items.get(1);
        Assert.assertEquals(child.id, childActual.id);
    }

    @Test
    public void testLike() throws Exception {
        CreateDealerRequest createChild = new CreateDealerRequest();
        createChild.name = "child";
        createChild.contactName = "dfsafaaf";
        createChild.level = DealerLevelView.LEVEL2;
        createChild.requestBy = "test";
        createChild.parentDealerId = defaultDealer.id.toHexString();
        createChild.status = DealerStatusView.ACTIVE;
        ServerResponse childResponse = site.post("/api/dealer").body(createChild).execute();
        DealerResponse child = childResponse.body(DealerResponse.class);
        SearchDealerRequest request = new SearchDealerRequest();
        request.name = "hi";
        request.page = 1;
        request.limit = 10;
        request.parentId = defaultDealer.id.toHexString();
        request.contactName = "saf";
        ServerResponse response = site.put("/api/dealer/children").body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<DealerResponse> findView = response.body(FindView.class);
        Assert.assertEquals(1, findView.items.size());
        DealerResponse childActual = findView.items.get(0);
        Assert.assertEquals(child.id, childActual.id);
    }

    @Test
    public void root() throws Exception {
        Dealer child = new Dealer();
        child.name = "child";
        child.email = "email";
        child.contactName = "name";
        child.contactIdNumber = "id";
        child.cellphone = "phone";
        child.parentDealerId = id.toHexString();
        child.level = DealerLevel.LEVEL2;
        child.createdTime = LocalDateTime.now();
        child.updatedTime = LocalDateTime.now();
        child.createdBy = "test";
        child.updatedBy = "test";
        collection.insertOne(child);
        ServerResponse response = site.get("/api/dealer/" + id.toHexString() + "/root").execute();
        Assert.assertEquals(200, response.statusCode());
        DealerResponse dealerResponse = response.body(DealerResponse.class);
        Assert.assertEquals(id.toHexString(), dealerResponse.id);
    }
}