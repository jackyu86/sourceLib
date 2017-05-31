package app.dealer.api;

import app.dealer.api.dealer.CreateDealerRequest;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerQuery;
import app.dealer.api.dealer.SearchDealerRequest;
import app.dealer.api.dealer.UpdateDealerRequest;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

import java.util.Optional;

/**
 * @author Jonathan.Guo
 */
public interface DealerWebService {
    @POST
    @Path("/api/dealer")
    DealerResponse create(CreateDealerRequest request);

    @PUT
    @Path("/api/dealer")
    FindView<DealerResponse> find(DealerQuery query);

    @GET
    @Path("/api/dealer/:dealerId")
    Optional<DealerResponse> get(@PathParam("dealerId") String dealerId);

    @PUT
    @Path("/api/dealer/:dealerId")
    DealerResponse update(@PathParam("dealerId") String dealerId, UpdateDealerRequest request);

    @DELETE
    @Path("/api/dealer/:dealerId")
    void delete(@PathParam("dealerId") String dealerId);

    @PUT
    @Path("/api/dealer/admin/children")
    FindView<DealerResponse> adminChildren(SearchDealerRequest request);

    @PUT
    @Path("/api/dealer/children")
    FindView<DealerResponse> children(SearchDealerRequest request);

    @GET
    @Path("/api/dealer/:dealerId/root")
    DealerResponse root(@PathParam("dealerId") String dealerId);

    @PUT
    @Path("/api/dealer/:dealerId/lock")
    void lock(@PathParam("dealerId") String id);

    @PUT
    @Path("/api/dealer/:dealerId/unlock")
    void unlock(@PathParam("dealerId") String id);

    @PUT
    @Path("/api/dealer/:dealerId/auto-allocate")
    void switchAutoAllocate(@PathParam("dealerId") String id);
}
