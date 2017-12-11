package com.caej.client;

import java.util.Optional;

import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.product.BatchGetProductRequest;
import com.caej.product.api.product.ProductSearchCategoryTreeResponse;
import com.caej.product.api.product.ProductSearchFirstLevelCategoryResponse;
import com.caej.product.api.product.SearchProductRequest;
import com.caej.product.api.product.SearchProductResponse;

import io.sited.cache.Cache;
import io.sited.db.FindView;

/**
 * @author miller
 */
public class ProductSearchWebServiceClient implements ProductSearchWebService {
    private final ProductSearchWebService productSearchWebService;
    private final Cache<ProductSearchCategoryTreeResponse> productSearchCategoryTreeResponseCache;
    private final Cache<ProductSearchFirstLevelCategoryResponse> productSearchFirstLevelCategoryResponseCache;

    public ProductSearchWebServiceClient(ProductSearchWebService productSearchWebService, Cache<ProductSearchCategoryTreeResponse> productSearchCategoryTreeResponseCache, Cache<ProductSearchFirstLevelCategoryResponse> productSearchFirstLevelCategoryResponseCache) {
        this.productSearchWebService = productSearchWebService;
        this.productSearchCategoryTreeResponseCache = productSearchCategoryTreeResponseCache;
        this.productSearchFirstLevelCategoryResponseCache = productSearchFirstLevelCategoryResponseCache;
    }

    @Override
    public FindView<SearchProductResponse> search(SearchProductRequest request) {
        return productSearchWebService.search(request);
    }

    @Override
    public FindView<String> searchV2(SearchProductRequest request) {
        return productSearchWebService.searchV2(request);
    }

    @Override
    public FindView<SearchProductResponse> batchGet(BatchGetProductRequest request) {
        return productSearchWebService.batchGet(request);
    }

    @Override
    public ProductSearchCategoryTreeResponse categoryTree() {
        final String prefix = "categoryTree";
        Optional<ProductSearchCategoryTreeResponse> optional = productSearchCategoryTreeResponseCache.get(prefix);
        if (optional.isPresent()) {
            return optional.get();
        }
        ProductSearchCategoryTreeResponse response = productSearchWebService.categoryTree();
        productSearchCategoryTreeResponseCache.put(prefix, response);
        return response;
    }

    @Override
    public ProductSearchFirstLevelCategoryResponse firstLevelCategory() {
        final String prefix = "firstLevelCategory";
        Optional<ProductSearchFirstLevelCategoryResponse> optional = productSearchFirstLevelCategoryResponseCache.get(prefix);
        if (optional.isPresent()) {
            return optional.get();
        }
        ProductSearchFirstLevelCategoryResponse response = productSearchWebService.firstLevelCategory();
        productSearchFirstLevelCategoryResponseCache.put(prefix, response);
        return response;
    }
}
