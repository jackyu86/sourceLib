package app.dealer.policyholder.web;

import app.dealer.DealerModuleImpl;
import app.dealer.SiteRule;
import app.dealer.api.policyholder.CreatePolicyHolderRequest;
import app.dealer.api.policyholder.PolicyHolderResponse;
import app.dealer.api.policyholder.UpdatePolicyHolderRequest;
import app.dealer.policyholder.domain.PolicyHolder;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author miller
 */
public class PolicyHolderWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new DealerModuleImpl());
    private PolicyHolder policyHolder;
    private ObjectId id;
    private MongoCollection<PolicyHolder> collection;

    @Before
    public void before() throws Exception {
        policyHolder = new PolicyHolder();
        policyHolder.dealerId = new ObjectId().toHexString();
        policyHolder.name = "policyHolder";
        policyHolder.gender = "male";
        policyHolder.birthDate = LocalDate.now();
        policyHolder.idNumber = "idNumber";
        policyHolder.phone = "phone";
        policyHolder.email = "email";
        policyHolder.createdBy = "test";
        policyHolder.updatedBy = "test";
        policyHolder.createTime = LocalDateTime.now();
        policyHolder.updateTime = LocalDateTime.now();
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("policy_holder", PolicyHolder.class);
        collection.insertOne(policyHolder);
        id = policyHolder.id;
    }

    @After
    public void after() throws Exception {
        collection.deleteOne(new Document("_id", id));
    }

    @Test
    public void create() throws Exception {
        CreatePolicyHolderRequest request = new CreatePolicyHolderRequest();
        request.dealerId = new ObjectId().toHexString();
        request.name = "newHolder";
        request.gender = "male";
        request.birthDate = LocalDate.now();
        request.idNumber = "newIdNumber";
        request.phone = "newPhone";
        request.email = "email";
        request.createdBy = "test";
        ServerResponse response = site.post("/api/policy-holder").body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        PolicyHolderResponse policyHolder = response.body(PolicyHolderResponse.class);
        Assert.assertNotNull(policyHolder.id);
        Assert.assertEquals(request.name, policyHolder.name);
        Assert.assertEquals(request.gender, policyHolder.gender);
        Assert.assertEquals(request.birthDate, policyHolder.birthDate);
        Assert.assertEquals(request.idNumber, policyHolder.idNumber);
        Assert.assertEquals(request.phone, policyHolder.phone);
        Assert.assertEquals(request.email, policyHolder.email);
    }

    @Test
    public void get() throws Exception {
        ServerResponse response = site.get("/api/policy-holder/" + id).execute();
        Assert.assertEquals(200, response.statusCode());
        Optional<PolicyHolderResponse> policyHolderResponseOptional = response.body(PolicyHolderResponse.class);
        Assert.assertTrue(policyHolderResponseOptional.isPresent());
        PolicyHolderResponse policyHolderResponse = policyHolderResponseOptional.get();
        Assert.assertEquals(id, policyHolderResponse.id);
        Assert.assertEquals(policyHolder.name, policyHolderResponse.name);
    }

    @Test
    public void update() throws Exception {
        UpdatePolicyHolderRequest request = new UpdatePolicyHolderRequest();
        request.name = "newUpdateName";
        request.phone = policyHolder.phone;
        request.birthday = policyHolder.birthDate;
        request.email = policyHolder.email;
        request.gender = policyHolder.gender;
        request.idNumber = policyHolder.idNumber;
        request.updatedBy = "test";
        ServerResponse response = site.put("/api/policy-holder/" + id.toHexString()).body(request).execute();
        Assert.assertEquals(200, response.statusCode());
        PolicyHolderResponse policyHolderResponse = response.body(PolicyHolderResponse.class);
        Assert.assertNotEquals(policyHolder.name, policyHolderResponse.name);
        Assert.assertEquals(policyHolder.phone, policyHolderResponse.phone);
    }

    @Test
    public void delete() throws Exception {
        ServerResponse response = site.delete("/api/policy-holder/" + id).execute();
        Assert.assertEquals(204, response.statusCode());
        PolicyHolder check = collection.find(new Document("_id", id)).first();
        Assert.assertEquals(null, check);
    }

    @Test
    public void find() throws Exception {
        ServerResponse response = site.get("/api/policy-holder/dealer/" + policyHolder.dealerId).execute();
        Assert.assertEquals(200, response.statusCode());
        FindView<PolicyHolderResponse> findView = response.body(FindView.class);
        Assert.assertEquals(1, findView.items.size());
        PolicyHolderResponse policyHolderResponse = findView.items.get(0);
        Assert.assertEquals(id, policyHolderResponse.id);
    }

}