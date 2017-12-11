package app.dealer.api;

import app.dealer.api.dealer.AuthenticationRequest;
import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerUserQuery;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.dealer.UpdateDealerUserRequest;
import app.dealer.api.dealer.UpdatePayPasswordRequest;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

import java.util.Optional;

/**
 * @author chi
 */
public interface DealerUserWebService {
    @Path("/api/dealer/user/:id")
    @GET
    Optional<DealerUserResponse> get(@PathParam("id") String userId);

    @Path("/api/dealer/user")
    @POST
    DealerUserResponse create(CreateDealerUserRequest request);

    @Path("/api/dealer/user/:id")
    @DELETE
    void delete(@PathParam("id") String userId);

    @Path("/api/dealer/user/:id")
    @PUT
    DealerUserResponse update(@PathParam("id") String userId, UpdateDealerUserRequest request);

    @Path("/api/dealer/user/find")
    @PUT
    FindView<DealerUserResponse> find(DealerUserQuery query);

    @Path("/api/dealer/user/:id/pay-password")
    @PUT
    void updatePayPassword(@PathParam("id") String userId, UpdatePayPasswordRequest request);

    @Path("/api/dealer/user/:id/pay-password/authenticate")
    @POST
    DealerUserResponse payPasswordAuthenticate(@PathParam("id") String userId, AuthenticationRequest request);

    @PUT
    @Path("/api/dealer/user/:id/lock")
    void lock(@PathParam("id") String userId);

    @PUT
    @Path("/api/dealer/user/:id/unlock")
    void unlock(@PathParam("id") String userId);

    @PUT
    @Path("/api/dealer/:dealerId/user/lock")
    void lockByDealer(@PathParam("dealerId") String dealerId);

    @PUT
    @Path("/api/dealer/:dealerId/user/unlock")
    void unlockByDealer(@PathParam("dealerId") String dealerId);

    @GET
    @Path("/api/dealer/:dealerId/user")
    Optional<DealerUserResponse> getByDealerId(@PathParam("dealerId") String dealerId);
}
