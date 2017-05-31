package com.caej.client;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.product.DealerProductCategoryResponse;
import app.dealer.api.product.DealerProductResponse;
import app.dealer.api.product.DealerProductView;
import app.dealer.api.product.SearchDealerProductRequest;
import app.dealer.api.product.UpdateDealerProductRequest;
import io.sited.cache.Cache;
import io.sited.db.FindView;
import io.sited.http.PathParam;

import java.util.Optional;

/**
 * @author miller
 */
public class DealerProductWebServiceClient implements DealerProductWebService {
    private final Cache<DealerProductCategoryResponse> categoryResponseCache;
    private final DealerProductWebService dealerProductWebService;

    public DealerProductWebServiceClient(DealerProductWebService dealerProductWebService, Cache<DealerProductCategoryResponse> categoryResponseCache) {
        this.categoryResponseCache = categoryResponseCache;
        this.dealerProductWebService = dealerProductWebService;
    }

    @Override
    public DealerProductResponse update(@PathParam("dealerId") String dealerId, UpdateDealerProductRequest request) {
        return dealerProductWebService.update(dealerId, request);
    }

    @Override
    public Optional<DealerProductResponse> get(@PathParam("dealerId") String dealerId) {
        return dealerProductWebService.get(dealerId);
    }

    @Override
    public FindView<String> search(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request) {
        return dealerProductWebService.search(dealerId, request);
    }

    @Override
    public FindView<String> searchV2(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request) {
        return dealerProductWebService.search(dealerId, request);
    }

    @Override
    public FindView<DealerProductView> list(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request) {
        return dealerProductWebService.list(dealerId, request);
    }

    @Override
    public DealerProductCategoryResponse category(@PathParam("dealerId") String dealerId) {
        final String prefix = "dealerId$";
        Optional<DealerProductCategoryResponse> optional = categoryResponseCache.get(prefix + dealerId);
        if (optional.isPresent()) {
            return optional.get();
        }
        DealerProductCategoryResponse response = dealerProductWebService.category(dealerId);
        categoryResponseCache.put(prefix + dealerId, response);
        return response;
    }

    @Override
    public Optional<DealerProductView> getByDealerIdAndProductName(@PathParam("dealerId") String dealerId, @PathParam("productName") String productName) {
        return dealerProductWebService.getByDealerIdAndProductName(dealerId, productName);
    }
}
