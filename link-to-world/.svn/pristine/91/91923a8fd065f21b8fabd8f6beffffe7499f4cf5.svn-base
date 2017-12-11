package app.dealer.policyholder.web;

import app.dealer.api.PolicyHolderWebService;
import app.dealer.api.policyholder.CreatePolicyHolderRequest;
import app.dealer.api.policyholder.PolicyHolderResponse;
import app.dealer.api.policyholder.UpdatePolicyHolderRequest;
import app.dealer.policyholder.domain.PolicyHolder;
import app.dealer.policyholder.service.PolicyHolderService;
import io.sited.http.PathParam;
import io.sited.http.exception.NotFoundException;
import io.sited.db.FindView;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author miller
 */
public class PolicyHolderWebServiceImpl implements PolicyHolderWebService {
    @Inject
    PolicyHolderService policyHolderService;

    @Override
    public PolicyHolderResponse create(CreatePolicyHolderRequest request) {
        return response(policyHolderService.create(request));
    }

    @Override
    public Optional<PolicyHolderResponse> get(@PathParam("policyHolderId") ObjectId policyHolderId) {
        Optional<PolicyHolder> policyHolder = policyHolderService.findById(policyHolderId);
        if (!policyHolder.isPresent()) {
            throw new NotFoundException("missing policyHolder,id={}", policyHolderId);
        }
        return Optional.ofNullable(response(policyHolder.get()));
    }

    @Override
    public PolicyHolderResponse update(@PathParam("policyHolderId") ObjectId policyHolderId, UpdatePolicyHolderRequest request) {
        return response(policyHolderService.update(policyHolderId, request));
    }

    @Override
    public void delete(@PathParam("policyHolderId") ObjectId policyHolderId) {
        policyHolderService.delete(policyHolderId);
    }

    @Override
    public FindView<PolicyHolderResponse> list(@PathParam("dealerId") String dealerId) {
        return FindView.map(policyHolderService.list(dealerId), this::response);
    }

    private PolicyHolderResponse response(PolicyHolder policyHolder) {
        PolicyHolderResponse response = new PolicyHolderResponse();
        response.id = policyHolder.id;
        response.name = policyHolder.name;
        response.gender = policyHolder.gender;
        response.birthDate = policyHolder.birthDate;
        response.idNumber = policyHolder.idNumber;
        response.phone = policyHolder.phone;
        response.email = policyHolder.email;
        return response;
    }
}
