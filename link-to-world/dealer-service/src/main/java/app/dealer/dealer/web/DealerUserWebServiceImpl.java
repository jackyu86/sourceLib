package app.dealer.dealer.web;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.AuthenticationRequest;
import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerUserQuery;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.dealer.DealerUserStatusView;
import app.dealer.api.dealer.UpdateDealerUserRequest;
import app.dealer.api.dealer.UpdatePayPasswordRequest;
import app.dealer.dealer.domain.DealerUser;
import app.dealer.dealer.domain.DealerUserStatus;
import app.dealer.dealer.service.DealerUserService;
import app.dealer.dealer.service.PasswordHasher;
import com.google.common.base.Strings;
import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.exception.BadRequestException;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author miller
 */
public class DealerUserWebServiceImpl implements DealerUserWebService {
    @Inject
    DealerUserService dealerUserService;

    @Override
    public Optional<DealerUserResponse> get(String userId) {
        Optional<DealerUser> dealerUser = dealerUserService.getOptionalByUserId(userId);
        if (!dealerUser.isPresent()) return Optional.empty();
        return Optional.ofNullable(response(dealerUser.get()));
    }

    @Override
    public DealerUserResponse create(CreateDealerUserRequest request) {
        DealerUser dealerUser = dealerUserService.create(request);
        return response(dealerUser);
    }

    @Override
    public void delete(String userId) {
        dealerUserService.delete(userId);
    }

    @Override
    public DealerUserResponse update(String userId, UpdateDealerUserRequest request) {
        return response(dealerUserService.update(userId, request));
    }

    @Override
    public FindView<DealerUserResponse> find(DealerUserQuery query) {
        return FindView.map(dealerUserService.find(query), this::response);
    }

    @Override
    public void updatePayPassword(String userId, UpdatePayPasswordRequest request) {
        dealerUserService.updatePayPassword(userId, request);
    }

    @Override
    public DealerUserResponse payPasswordAuthenticate(String userId, AuthenticationRequest request) {
        DealerUser dealerUser = dealerUserService.getByUserId(userId);
        if (Strings.isNullOrEmpty(dealerUser.hashedPayPassword)) {
            throw new BadRequestException("payPassword", "dealer.error.unsetPayPassword");
        }
        if (!dealerUser.hashedPayPassword.equals(new PasswordHasher(dealerUser.salt, request.payPassword).hash(dealerUser.iteration))) {
            throw new BadRequestException("payPassword", "dealer.error.invalidPayPassword");
        }
        return response(dealerUser);
    }

    @Override
    public void lock(@PathParam("id") String userId) {
        dealerUserService.updateStatus(userId, DealerUserStatus.INACTIVE);
    }

    @Override
    public void unlock(@PathParam("id") String userId) {
        dealerUserService.updateStatus(userId, DealerUserStatus.ACTIVE);
    }

    @Override
    public void lockByDealer(@PathParam("dealerId") String dealerId) {
        dealerUserService.updateStatusByDealer(dealerId, DealerUserStatus.INACTIVE);
    }

    @Override
    public void unlockByDealer(@PathParam("dealerId") String dealerId) {
        dealerUserService.updateStatusByDealer(dealerId, DealerUserStatus.ACTIVE);
    }

    @Override
    public Optional<DealerUserResponse> getByDealerId(@PathParam("dealerId") String dealerId) {
        Optional<DealerUser> dealerUser = dealerUserService.getOptionalByDealerId(dealerId);
        return dealerUser.isPresent() ? Optional.of(response(dealerUser.get())) : Optional.empty();
    }

    public DealerUserResponse response(DealerUser dealerUser) {
        DealerUserResponse response = new DealerUserResponse();
        response.id = dealerUser.id;
        response.userId = dealerUser.userId;
        response.dealerId = dealerUser.dealerId;
        response.roles = dealerUser.roles;
        response.status = DealerUserStatusView.valueOf(dealerUser.status.name());
        response.createdBy = dealerUser.createdBy;
        response.createdTime = dealerUser.createdTime;
        response.updatedBy = dealerUser.updatedBy;
        response.updatedTime = dealerUser.updatedTime;
        return response;
    }
}
