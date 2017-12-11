package app.dealer.api;

import app.dealer.api.policyholder.CreatePolicyHolderRequest;
import app.dealer.api.policyholder.PolicyHolderResponse;
import app.dealer.api.policyholder.UpdatePolicyHolderRequest;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;
import io.sited.db.FindView;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * @author miller
 */
public interface PolicyHolderWebService {
    @POST
    @Path("/api/policy-holder")
    PolicyHolderResponse create(CreatePolicyHolderRequest request);

    @GET
    @Path("/api/policy-holder/:policyHolderId")
    Optional<PolicyHolderResponse> get(@PathParam("policyHolderId") ObjectId policyHolderId);

    @PUT
    @Path("/api/policy-holder/:policyHolderId")
    PolicyHolderResponse update(@PathParam("policyHolderId") ObjectId policyHolderId, UpdatePolicyHolderRequest request);

    @DELETE
    @Path("/api/policy-holder/:policyHolderId")
    void delete(@PathParam("policyHolderId") ObjectId policyHolderId);

    @GET
    @Path("/api/policy-holder/dealer/:dealerId")
    FindView<PolicyHolderResponse> list(@PathParam("dealerId") String dealerId);
}
