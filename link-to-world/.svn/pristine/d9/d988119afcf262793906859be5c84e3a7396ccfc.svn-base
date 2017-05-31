package com.caej.site.product.web;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.client.DealerProductWebServiceClient;
import com.caej.client.InsuranceVendorWebServiceClient;
import com.caej.client.ProductDetailWebServiceClient;
import com.caej.client.ProductSearchWebServiceClient;
import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.BatchInsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.product.api.product.ProductDetailResponse;
import com.caej.product.api.product.SearchProductRequest;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.product.SearchDealerProductRequest;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.BadRequestException;
import io.sited.user.web.User;

/**
 * @author chi
 */
public class ProductSearchControllerV2 {
    private final Logger logger = LoggerFactory.getLogger(ProductSearchControllerV2.class);

    @Inject
    InsuranceCategoryWebService insuranceCategoryWebService;
    @Inject
    ProductSearchWebServiceClient productSearchWebServiceClient;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    DealerProductWebServiceClient dealerProductWebServiceClient;
    @Inject
    ProductDetailWebServiceClient productDetailWebServiceClient;
    @Inject
    InsuranceVendorWebServiceClient insuranceVendorWebServiceClient;

    @Path("/search")
    @GET
    public Response search(Request request) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        HashMap<String, Object> context = Maps.newHashMap();
        List<InsuranceCategoryNodeResponse> tree;
        Optional<DealerUserResponse> dealerUserResponse = dealerUser(request);
        if (dealerUserResponse.isPresent()) {
            List<ObjectId> categoryIds = dealerProductWebServiceClient.category(dealerUserResponse.get().dealerId).categoryIds;
            BatchInsuranceCategoryRequest batchInsuranceCategoryRequest = new BatchInsuranceCategoryRequest();
            batchInsuranceCategoryRequest.categoryIds = categoryIds;
            tree = insuranceCategoryWebService.childrenTree(batchInsuranceCategoryRequest);
        } else {
            tree = productSearchWebServiceClient.categoryTree().list;
        }
        context.put("categoryNodes", tree);

        ObjectId categoryId = request.queryParam("category", ObjectId.class).orElse(null);
        FindView<ProductDetailResponse> products = isDealerUser(request)
            ? searchForDealerUser(categoryId, request.require(User.class), 1, Integer.MAX_VALUE)
            : search(categoryId, 1, Integer.MAX_VALUE);

        context.put("products", productItemViews(products));

        logger.info("plp loaded, time={}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return Response.template("/product/list.html", context);
    }


    private FindView<ProductItemView> productItemViews(FindView<ProductDetailResponse> responses) {
        List<String> vendorIds = responses.items.stream().map(product -> product.product.insuranceVendorId.toHexString()).collect(Collectors.toList());
        List<InsuranceVendorResponse> vendors = insuranceVendorWebServiceClient.batchGet(vendorIds);
        FindView<ProductItemView> views = new FindView<>();
        views.page = responses.page;
        views.limit = responses.limit;
        views.total = responses.total;
        views.items = Lists.newArrayList();
        for (int i = 0; i < responses.total; i++) {
            ProductItemView view = new ProductItemView();
            ProductDetailResponse response = responses.items.get(i);
            view.id = response.product.id;
            view.name = response.product.name;
            view.displayName = response.product.displayName;
            view.form = response.plpForm;
            view.highlightContent = response.product.highlightContent;
            view.price = response.price;
            view.specialFunction = response.product.specialFunction;
            view.specialRule = response.product.specialRule;
            view.vendor = vendors.get(i);
            views.items.add(view);
        }
        return views;
    }

    private FindView<ProductDetailResponse> search(ObjectId categoryId, Integer page, Integer limit) {
        SearchProductRequest searchProductRequest = new SearchProductRequest();
        searchProductRequest.categoryIds = categoryId == null ? null : Lists.newArrayList(categoryId);
        searchProductRequest.page = 1;
        searchProductRequest.limit = limit;
        FindView<String> productNames = productSearchWebServiceClient.searchV2(searchProductRequest);
        return productDetailResponses(productNames);
    }

    private FindView<ProductDetailResponse> searchForDealerUser(ObjectId categoryId, User user, Integer page, Integer limit) {
        DealerUserResponse dealerUser = dealerUserWebService.get(user.id).orElseThrow(() -> new BadRequestException("missing dealer user, id={}", user.id));
        SearchDealerProductRequest searchDealerProductRequest = new SearchDealerProductRequest();
        searchDealerProductRequest.categoryIds = categoryId == null ? null : Lists.newArrayList(categoryId);
        searchDealerProductRequest.page = page;
        searchDealerProductRequest.limit = limit;
        FindView<String> productNames = dealerProductWebServiceClient.search(dealerUser.dealerId, searchDealerProductRequest);
        return productDetailResponses(productNames);
    }

    private FindView<ProductDetailResponse> productDetailResponses(FindView<String> names) {
        FindView<ProductDetailResponse> views = new FindView<>();
        views.page = names.page;
        views.limit = names.limit;
        views.total = names.total;
        views.items = productDetailWebServiceClient.batchGet(names.items);
        return views;
    }

    private boolean isDealerUser(Request request) {
        User current = request.require(User.class, null);
        return current != null && (current.hasRole("dealerAdmin") || current.hasRole("dealerSeller"));
    }

    private Optional<DealerUserResponse> dealerUser(Request request) {
        User current = request.require(User.class, null);
        if (current == null) return Optional.empty();
        return dealerUserWebService.get(current.id);
    }
}
