package app.dealer.dealer.web;

import app.dealer.DealerModuleImpl;
import app.dealer.SiteRule;
import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerUserQuery;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.dealer.DealerUserStatusView;
import app.dealer.api.dealer.UpdateDealerUserRequest;
import app.dealer.dealer.domain.DealerUser;
import app.dealer.dealer.domain.DealerUserStatus;
import com.google.common.collect.Lists;
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
import java.util.UUID;

/**
 * @author miller
 */
public class DealerUserWebServiceImplTest {
    @Rule
    public final SiteRule SITED = new SiteRule(new DealerModuleImpl());
    private DealerUser dealerUser;
    private ObjectId id;
    private MongoCollection<DealerUser> collection;

    @Before
    public void before() throws Exception {
        dealerUser = new DealerUser();
        dealerUser.userId = UUID.randomUUID().toString();
        dealerUser.dealerId = new ObjectId().toHexString();
        dealerUser.roles = Lists.newArrayList("admin");
        dealerUser.createdTime = LocalDateTime.now();
        dealerUser.createdBy = "init";
        dealerUser.updatedTime = LocalDateTime.now();
        dealerUser.updatedBy = "init";
        dealerUser.status = DealerUserStatus.ACTIVE;
        collection = SITED.module(DBModule.class).require(MongoConfig.class).db().getCollection("dealer_user", DealerUser.class);
        collection.insertOne(dealerUser);
        id = dealerUser.id;
    }

    @After
    public void after() throws Exception {
        collection.deleteOne(new Document("_id", id));
    }

    @Test
    public void create() throws Exception {
        CreateDealerUserRequest request = new CreateDealerUserRequest();
        request.dealerId = new ObjectId().toHexString();
        request.userId = UUID.randomUUID().toString();
        request.roles = Lists.newArrayList("sale");
        request.status = DealerUserStatusView.AUDITING;
        request.requestBy = "test";
        ServerResponse response = SITED.post("/api/dealer/user").body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        DealerUserResponse dealerUserResponse = response.body(DealerUserResponse.class);
        Assert.assertNotNull(dealerUserResponse.id);
        Assert.assertEquals(request.dealerId, dealerUserResponse.dealerId);
        Assert.assertEquals(request.userId, dealerUserResponse.userId);
    }

    @Test
    public void delete() throws Exception {
        ServerResponse response = SITED.delete("/api/dealer/user/" + dealerUser.userId).execute();
        Assert.assertEquals(204, response.statusCode());
        long count = collection.count(new Document("_id", id));
        Assert.assertEquals(0L, count);
    }

    @Test
    public void update() throws Exception {
        UpdateDealerUserRequest request = new UpdateDealerUserRequest();
        request.roles = Lists.newArrayList("newRole");
        request.requestBy = "test";
        ServerResponse response = SITED.put("/api/dealer/user/" + dealerUser.userId).body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        DealerUserResponse dealerUserResponse = response.body(DealerUserResponse.class);
        Assert.assertEquals(dealerUserResponse.id, id);
        Assert.assertNotEquals(dealerUser.roles, dealerUserResponse.roles);
        Assert.assertEquals(request.roles, dealerUserResponse.roles);
    }

    @Test
    public void find() throws Exception {
        DealerUserQuery query = new DealerUserQuery();
        query.dealerId = dealerUser.dealerId;
        query.roles = Lists.newArrayList(dealerUser.roles);
        query.page = 1;
        query.limit = 10;
        ServerResponse response = SITED.put("/api/dealer/user/find").body(query).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<DealerUserResponse> findView = response.body(FindView.class);
        Assert.assertEquals(1, findView.items.size());
        DealerUserResponse dealerUserResponse = findView.items.get(0);
        Assert.assertEquals(dealerUser.id, dealerUserResponse.id);
    }
}