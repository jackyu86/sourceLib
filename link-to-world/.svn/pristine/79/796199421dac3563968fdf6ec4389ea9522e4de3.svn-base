package app.dealer.product.web;

import app.dealer.DealerModuleImpl;
import app.dealer.SiteRule;
import app.dealer.api.product.SearchDealerProductRequest;
import app.dealer.product.domain.DealerProduct;
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

import java.util.UUID;

/**
 * @author miller
 */
public class DealerProductWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new DealerModuleImpl());
    private DealerProduct defaultDealerProduct;
    private ObjectId id;
    private ObjectId categoryId;
    private String productName;
    private String dealerId;
    private MongoCollection<DealerProduct> collection;

    @Before
    public void setUp() throws Exception {
        defaultDealerProduct = new DealerProduct();
        dealerId = new ObjectId().toHexString();
        defaultDealerProduct.dealerId = dealerId;
        productName = UUID.randomUUID().toString().replaceAll("-", "");
        defaultDealerProduct.productName = productName;
        categoryId = new ObjectId();
        defaultDealerProduct.insuranceCategoryIds = Lists.newArrayList(categoryId);
        collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("dealer_product", DealerProduct.class);
        collection.insertOne(defaultDealerProduct);
        id = defaultDealerProduct.id;
    }

    @After
    public void tearDown() throws Exception {
        collection.deleteOne(new Document("_id", id));
    }

    @Test
    public void search() throws Exception {
        SearchDealerProductRequest request = new SearchDealerProductRequest();
        request.categoryIds = Lists.newArrayList(categoryId);
        request.limit = 1;
        request.page = 1;
        ServerResponse response = site.post("/api/dealer/" + dealerId + "/search").body(request).execute();

        Assert.assertEquals(200, response.statusCode());
        FindView<String> productIds = response.body(FindView.class);
        Assert.assertEquals(1, productIds.items.size());
        String actual = productIds.items.get(0);
        Assert.assertEquals(productName, actual);
    }

}