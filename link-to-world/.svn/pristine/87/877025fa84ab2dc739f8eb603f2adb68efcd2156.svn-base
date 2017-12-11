package app.dealer.product.web;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.product.DealerProductCategoryResponse;
import app.dealer.api.product.DealerProductResponse;
import app.dealer.api.product.DealerProductView;
import app.dealer.api.product.SearchDealerProductRequest;
import app.dealer.api.product.UpdateDealerProductRequest;
import app.dealer.dealer.domain.Dealer;
import app.dealer.dealer.service.DealerService;
import app.dealer.product.domain.DealerProduct;
import app.dealer.product.service.DealerProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.exception.BadRequestException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author miller
 */
public class DealerProductWebServiceImpl implements DealerProductWebService {
    @Inject
    DealerProductService dealerProductService;
    @Inject
    DealerService dealerService;

    @Override
    public DealerProductResponse update(@PathParam("dealerId") String dealerId, UpdateDealerProductRequest request) {
        Dealer currentDealer = dealerService.get(dealerId).orElseThrow(() -> new BadRequestException("dealerId", "dealer.error.dealerNoneExists"));
        dealerProductService.deleteByDealerId(currentDealer.id.toHexString());
        List<Dealer> dealers = dealerService.children(dealerId);
        dealers.forEach(dealer -> dealerProductService.deleteByDealerId(dealer.id.toHexString()));

        insert(dealerId, request.products);
        List<String> dealerIds = Lists.newArrayList();
        if (currentDealer.autoAllocate) {
            dealers.forEach(dealer -> insert(dealer.id.toHexString(), request.products));
        } else {
            dealers.forEach(dealer -> {
                if (dealer.autoAllocate) {
                    insert(dealer.id.toHexString(), request.products);
                } else {
                    dealerIds.add(dealer.id.toHexString());
                }
            });
        }

        insert(dealerId, request.products);
        Map<String, DealerProductView> productViewMap = request.products.stream().collect(Collectors.toMap(dealerProduct -> dealerProduct.productName, dealerProduct -> dealerProduct));

        Map<String, List<DealerProduct>> dealerProductsMap = Maps.newHashMap();
        dealerProductService.search(dealerIds).items.forEach(dealerProduct -> {
            String subDealerId = dealerProduct.dealerId;
            if (!dealerProductsMap.containsKey(subDealerId)) {
                dealerProductsMap.put(subDealerId, Lists.newArrayList());
            }
            dealerProductsMap.get(subDealerId).add(dealerProduct);
        });
        dealerProductsMap.entrySet().forEach(entry -> {
            List<DealerProductView> subDealerProducts = Lists.newArrayList();
            entry.getValue().forEach(dealerProduct -> {
                if (productViewMap.containsKey(dealerProduct.productName)) {
                    subDealerProducts.add(productViewMap.get(dealerProduct.productName));
                }
            });
            insert(entry.getKey(), subDealerProducts);
        });
        DealerProductResponse response = new DealerProductResponse();
        response.products = request.products;
        return response;
    }

    @Override
    public Optional<DealerProductResponse> get(@PathParam("dealerId") String dealerId) {
        List<DealerProductView> viewList = new ArrayList<>();
        List<DealerProduct> list = dealerProductService.get(dealerId);
        list.forEach(dealerProduct -> {
            viewList.add(view(dealerProduct));
        });
        DealerProductResponse response = new DealerProductResponse();
        response.products = viewList;
        return Optional.of(response);
    }

    @Override
    public FindView<String> search(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request) {
        return FindView.map(dealerProductService.search(dealerId, request), dealerProduct -> dealerProduct.productName);
    }

    @Override
    public FindView<String> searchV2(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request) {
        return FindView.map(dealerProductService.search(dealerId, request), dealerProduct -> dealerProduct.id.toHexString());
    }

    @Override
    public FindView<DealerProductView> list(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request) {
        return FindView.map(dealerProductService.search(dealerId, request), this::view);
    }

    @Override
    public DealerProductCategoryResponse category(@PathParam("dealerId") String dealerId) {
        DealerProductCategoryResponse response = new DealerProductCategoryResponse();
        response.categoryIds = Lists.newArrayList(dealerProductService.category(dealerId));
        return response;
    }

    @Override
    public Optional<DealerProductView> getByDealerIdAndProductName(@PathParam("dealerId") String dealerId, @PathParam("productName") String productName) {
        Optional<DealerProduct> dealerProductOptional = dealerProductService.get(dealerId, productName);
        if (dealerProductOptional.isPresent()) {
            return Optional.of(view(dealerProductOptional.get()));
        }
        return Optional.empty();
    }

    private DealerProductView view(DealerProduct dealerProduct) {
        DealerProductView view = new DealerProductView();
        view.productName = dealerProduct.productName;
        view.insuranceCategoryIds = dealerProduct.insuranceCategoryIds;
        view.surrenderMark = dealerProduct.surrenderMark;
        view.bankAccountMark = dealerProduct.bankAccountMark;
        view.rate = dealerProduct.commissionRate;
        return view;
    }

    private void insert(String dealerId, List<DealerProductView> products) {
        products.forEach(dealerProductView -> {
            DealerProduct dealerProduct = new DealerProduct();
            dealerProduct.dealerId = dealerId;
            dealerProduct.productName = dealerProductView.productName;
            dealerProduct.insuranceCategoryIds = dealerProductView.insuranceCategoryIds;
            dealerProduct.surrenderMark = dealerProductView.surrenderMark;
            dealerProduct.bankAccountMark = dealerProductView.bankAccountMark;
            dealerProduct.commissionRate = dealerProductView.rate;
            dealerProductService.create(dealerProduct);
        });
    }
}
