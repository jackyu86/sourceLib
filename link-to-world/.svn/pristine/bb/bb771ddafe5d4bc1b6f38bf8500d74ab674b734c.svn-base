package com.caej.site.customer.web.ajax;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.site.customer.web.GenderModel;
import com.caej.site.dealer.web.ajax.UpdatePolicyHolderAJAXRequest;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.PolicyHolderWebService;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.policyholder.CreatePolicyHolderRequest;
import app.dealer.api.policyholder.PolicyHolderResponse;
import app.dealer.api.policyholder.UpdatePolicyHolderRequest;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.BadRequestException;
import io.sited.user.web.User;

/**
 * @author Jonathan.Guo
 */
public class PolicyHolderAJAXController {
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    PolicyHolderWebService policyHolderWebService;

    @Path("/ajax/policy-holder")
    @GET
    public Response list(Request request) {
        User user = request.require(User.class);
        Optional<DealerUserResponse> dealerUserResponse = dealerUserWebService.get(user.id);
        if (!dealerUserResponse.isPresent()) {
            throw new BadRequestException("user", null, "Dealer user don't exists, userId={}", user.id);
        }
        FindView<PolicyHolderResponse> list = policyHolderWebService.list(dealerUserResponse.get().dealerId);
        list.items.forEach(policyHolderResponse -> policyHolderResponse.gender = GenderModel.display(policyHolderResponse.gender).value);
        return Response.body(list);
    }

    @Path("/ajax/policy-holder")
    @POST
    public Response create(Request request) throws IOException {
        CreatePolicyHolderRequest createPolicyHolderRequest = request.body(CreatePolicyHolderRequest.class);
        User user = request.require(User.class);
        Optional<DealerUserResponse> dealerUserResponse = dealerUserWebService.get(user.id);
        if (!dealerUserResponse.isPresent()) {
            throw new BadRequestException("user", null, "Dealer user don't exists, userId={}", user.id);
        }
        createPolicyHolderRequest.dealerId = dealerUserResponse.get().dealerId;
        createPolicyHolderRequest.createdBy = user.id;
        policyHolderWebService.create(createPolicyHolderRequest);
        return Response.empty();
    }

    @Path("/ajax/policy-holder")
    @PUT
    public Response update(Request request) throws IOException {
        UpdatePolicyHolderAJAXRequest updatePolicyHolderAJAXRequest = request.body(UpdatePolicyHolderAJAXRequest.class);
        User user = request.require(User.class);
        UpdatePolicyHolderRequest updatePolicyHolderRequest = new UpdatePolicyHolderRequest();
        updatePolicyHolderRequest.name = updatePolicyHolderAJAXRequest.name;
        updatePolicyHolderRequest.gender = updatePolicyHolderAJAXRequest.gender;
        updatePolicyHolderRequest.birthday = updatePolicyHolderAJAXRequest.birthDate;
        updatePolicyHolderRequest.idNumber = updatePolicyHolderAJAXRequest.idNumber;
        updatePolicyHolderRequest.phone = updatePolicyHolderAJAXRequest.phone;
        updatePolicyHolderRequest.email = updatePolicyHolderAJAXRequest.email;
        updatePolicyHolderRequest.updatedBy = user.id;
        policyHolderWebService.update(new ObjectId(updatePolicyHolderAJAXRequest.id), updatePolicyHolderRequest);
        return Response.empty();
    }

    @Path("/ajax/policy-holder/:id")
    @DELETE
    public Response delete(Request request) throws IOException {
        String id = request.pathParam("id");
        policyHolderWebService.delete(new ObjectId(id));
        return Response.empty();
    }
}
