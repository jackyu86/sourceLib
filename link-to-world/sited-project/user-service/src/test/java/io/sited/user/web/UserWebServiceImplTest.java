package io.sited.user.web;

import io.sited.http.ServerResponse;
import io.sited.user.SiteRule;
import io.sited.user.UserModuleImpl;
import io.sited.user.api.user.AuthenticationRequest;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UserResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

/**
 * @author chi
 */
public class UserWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new UserModuleImpl());

    @Before
    public void setup() throws Exception {
        Optional<UserResponse> user = user();
        if (!user.isPresent()) {
            CreateUserRequest createUserRequest = new CreateUserRequest();
            createUserRequest.email = "1@2";
            createUserRequest.username = "some";
            createUserRequest.fullName = "some";
            createUserRequest.password = "some";
            site.post("/api/user").body(createUserRequest).execute();
        }
    }

    @Test
    public void authenticate() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.username = "some";
        authenticationRequest.password = "some";

        ServerResponse response = site.post("/api/user/authenticate").body(authenticationRequest).execute();
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void update() throws Exception {
        UserResponse user = user().get();
        ServerResponse response;

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.imageURL = "/1.jpg";

        response = site.put("/api/user/" + user.id).body(updateUserRequest).execute();
        user = response.body(UserResponse.class);
        Assert.assertEquals("/1.jpg", user.imageURL);
        Assert.assertEquals("some", user.fullName);
    }

    private Optional<UserResponse> user() throws Exception {
        ServerResponse response = site.get("/api/user/username/some").execute();
        return response.body(Optional.class);
    }


}