package io.sited.message;

import io.sited.db.FindView;
import io.sited.http.ServerResponse;
import io.sited.message.api.message.MessageQuery;
import io.sited.message.api.message.MessageRequest;
import io.sited.message.api.message.MessageResponse;
import io.sited.test.SiteRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author chi
 */
public class MessageModuleImplTest {
    @Rule
    public SiteRule rule = new SiteRule(new MessageModuleImpl());

    @Test
    public void flow() throws Exception {
        MessageRequest request = new MessageRequest();
        request.from = "some";
        request.to = "some";
        request.content = "some";
        request.type = "some";

        ServerResponse response = rule.post("/api/message").body(request).execute();
        Assert.assertEquals(200, response.statusCode());

        MessageQuery query = new MessageQuery();
        query.to = "some";
        query.page = 1;
        query.limit = 20;

        FindView<MessageResponse> results = rule.put("/api/message/find").body(query).execute().body(FindView.class);
        Assert.assertEquals(1, (long) results.total);
    }
}