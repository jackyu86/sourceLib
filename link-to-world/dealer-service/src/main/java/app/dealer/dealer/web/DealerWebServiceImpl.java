package app.dealer.dealer.web;

import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.CreateDealerRequest;
import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerQuery;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerStatusView;
import app.dealer.api.dealer.SearchDealerRequest;
import app.dealer.api.dealer.UpdateDealerRequest;
import app.dealer.dealer.domain.Dealer;
import app.dealer.dealer.domain.DealerStatus;
import app.dealer.dealer.domain.DealerUser;
import app.dealer.dealer.domain.DealerUserStatus;
import app.dealer.dealer.service.DealerService;
import app.dealer.dealer.service.DealerUserService;
import app.dealer.product.domain.DealerProduct;
import app.dealer.product.service.DealerProductService;
import io.sited.db.FindView;
import io.sited.http.exception.BadRequestException;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static app.dealer.dealer.domain.DealerStatus.ACTIVE;
import static app.dealer.dealer.domain.DealerStatus.INACTIVE;

/**
 * @author miller
 */
public class DealerWebServiceImpl implements DealerWebService {
    @Inject
    DealerService dealerService;
    @Inject
    DealerUserService dealerUserService;
    @Inject
    DealerProductService dealerProductService;

    @Override
    public DealerResponse create(CreateDealerRequest request) {
        return response(dealerService.create(request));
    }

    @Override
    public FindView<DealerResponse> find(DealerQuery query) {
        return FindView.map(dealerService.find(query), this::response);
    }

    @Override
    public Optional<DealerResponse> get(String dealerId) {
        Optional<Dealer> dealer = dealerService.get(dealerId);
        if (dealer.isPresent() && !dealer.get().status.equals(DealerStatus.DELETED)) {
            return Optional.ofNullable(response(dealer.get()));
        }
        return Optional.empty();
    }

    @Override
    public DealerResponse update(String dealerId, UpdateDealerRequest request) {
        return response(dealerService.update(dealerId, request));
    }

    @Override
    public void delete(String dealerId) {
        dealerService.delete(dealerId);
    }

    @Override
    public FindView<DealerResponse> adminChildren(SearchDealerRequest request) {
        return FindView.map(dealerService.adminSearch(request), this::response);
    }

    @Override
    public FindView<DealerResponse> children(SearchDealerRequest request) {
        return FindView.map(dealerService.search(request), this::response);
    }

    @Override
    public DealerResponse root(String dealerId) {
        return response(dealerService.root(dealerId));
    }

    @Override
    public void lock(String id) {
        lockCurrentDealer(id);
        dealerService.children(id).forEach(dealer -> lockCurrentDealer(dealer.id.toHexString()));
    }

    @Override
    public void unlock(String id) {
        unlockCurrentDealer(id);
        dealerService.children(id).forEach(dealer -> unlockCurrentDealer(dealer.id.toHexString()));
    }

    @Override
    public void switchAutoAllocate(String id) {
        Dealer dealer = dealerService.get(id).orElseThrow(() -> new BadRequestException("dealerId", "dealer.error.dealerNoneExists"));
        List<DealerProduct> dealerProducts = dealerProductService.get(dealer.parentDealerId);
        allocate(dealer, dealerProducts);
        dealerService.children(id).forEach(result -> allocate(result, dealerProducts));
    }

    private void allocate(Dealer dealer, List<DealerProduct> products) {
        dealerService.switchAutoAllocate(dealer);
        String dealerId = dealer.id.toHexString();
        dealerProductService.deleteByDealerId(dealerId);
        products.forEach(source -> {
            DealerProduct dealerProduct = new DealerProduct();
            dealerProduct.dealerId = dealerId;
            dealerProduct.productName = source.productName;
            dealerProduct.insuranceCategoryIds = source.insuranceCategoryIds;
            dealerProduct.surrenderMark = source.surrenderMark;
            dealerProduct.bankAccountMark = source.bankAccountMark;
            dealerProduct.commissionRate = source.commissionRate;
            dealerProductService.create(dealerProduct);
        });
    }

    private void lockCurrentDealer(String id) {
        dealerService.updateStatus(id, INACTIVE);
        DealerUser dealerUser = dealerUserService.getByDealerId(id);
        dealerUserService.updateStatus(dealerUser.userId, DealerUserStatus.INACTIVE);
    }

    private void unlockCurrentDealer(String id) {
        dealerService.updateStatus(id, ACTIVE);
        DealerUser dealerUser = dealerUserService.getByDealerId(id);
        dealerUserService.updateStatus(dealerUser.userId, DealerUserStatus.ACTIVE);
    }

    private DealerResponse response(Dealer dealer) {
        DealerResponse response = new DealerResponse();
        response.id = dealer.id.toHexString();
        response.name = dealer.name;
        response.email = dealer.email;
        response.contactName = dealer.contactName;
        response.contactIdNumber = dealer.contactIdNumber;
        response.cellphone = dealer.cellphone;
        response.state = dealer.state;
        response.city = dealer.city;
        response.ward = dealer.ward;
        response.parentDealerId = dealer.parentDealerId;
        response.createdTime = dealer.createdTime;
        response.level = DealerLevelView.valueOf(dealer.level.name());
        response.status = DealerStatusView.valueOf(dealer.status.name());
        response.businessLicence = dealer.businessLicence;
        response.autoAllocate = dealer.autoAllocate;
        return response;
    }
}
