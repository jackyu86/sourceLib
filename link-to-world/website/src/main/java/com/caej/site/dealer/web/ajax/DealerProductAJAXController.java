package com.caej.site.dealer.web.ajax;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.client.ProductWebServiceClient;
import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.product.BatchGetProductRequest;
import com.caej.product.api.product.SearchProductResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.product.DealerProductView;
import app.dealer.api.product.SearchDealerProductRequest;
import app.dealer.api.product.UpdateDealerProductRequest;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.user.web.User;

/**
 * @author Jonathan.Guo
 */
public class DealerProductAJAXController {
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    ProductWebServiceClient productWebServiceClient;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    ProductSearchWebService productSearchWebService;
    @Inject
    InsuranceCategoryWebService insuranceCategoryWebService;

    @Path("/account/dealer/:id/products")
    @GET
    public Response dealerProductList(Request request) {
        ObjectId id = request.pathParam("id", ObjectId.class);
        DealerResponse dealerResponse = dealerWebService.get(id.toHexString()).orElseThrow(() -> new NotFoundException("no dealer"));
        User user = request.require(User.class);
        DealerUserResponse dealerUserResponse = dealerUserWebService.get(user.id).orElseThrow(() -> new UnauthorizedException("not dealer"));
        SearchDealerProductRequest searchRequest = new SearchDealerProductRequest();
        searchRequest.page = 1;
        searchRequest.limit = Integer.MAX_VALUE;
        FindView<DealerProductView> products = dealerProductWebService.list(dealerUserResponse.dealerId, searchRequest);
        BatchGetProductRequest batchGetProductRequest = new BatchGetProductRequest();
        batchGetProductRequest.productNames = FindView.map(products, product -> product.productName);
        FindView<SearchProductResponse> searchProductResponses = productSearchWebService.batchGet(batchGetProductRequest);
        Map<String, SearchProductResponse> productMap = Maps.newHashMap();
        searchProductResponses.forEach(searchProductResponse -> productMap.put(searchProductResponse.name, searchProductResponse));
        FindView<String> dealerProductNames = dealerProductWebService.search(dealerResponse.id, searchRequest);

        List<InsuranceCategoryNodeResponse> tree = insuranceCategoryWebService.tree();
        List<DealerCategoryResponse> categoryResponses = toCategoryResponseList(tree);
        Map<String, DealerCategoryResponse> categoryMap = Maps.newHashMap();
        collectCategoryMap(categoryResponses, categoryMap);

        products.forEach(product -> {
            DealerProductResponse response = new DealerProductResponse();
            response.name = product.productName;
            response.displayName = productMap.get(response.name).displayName;
            response.checked = dealerProductNames.items.contains(product.productName);
            response.categoryList = product.insuranceCategoryIds;
            response.categoryList.forEach(categoryId -> categoryMap.get(categoryId.toHexString()).productList.add(response));
        });
        return Response.body(categoryResponses);
    }

    @Path("/ajax/dealer/:id/products")
    @PUT
    public void updateDealerProduct(Request request) {
        String dealerId = request.pathParam("id");
        String parentDealerId = dealerWebService.get(dealerId).get().parentDealerId;
        UpdateDealerProductAJAXRequest updateDealerProductAJAXRequest = request.body(UpdateDealerProductAJAXRequest.class);
        if (updateDealerProductAJAXRequest.productNameList == null)
            return;
        UpdateDealerProductRequest updateDealerProductRequest = new UpdateDealerProductRequest();
        List<DealerProductView> products = Lists.newArrayList();
        updateDealerProductAJAXRequest.productNameList.forEach(productName -> {
            DealerProductView parentDealerProductView = dealerProductWebService.getByDealerIdAndProductName(parentDealerId, productName).get();
            DealerProductView dealerProductView = new DealerProductView();
            dealerProductView.productName = productName;
            dealerProductView.insuranceCategoryIds = parentDealerProductView.insuranceCategoryIds;
            dealerProductView.bankAccountMark = parentDealerProductView.bankAccountMark;
            dealerProductView.surrenderMark = parentDealerProductView.surrenderMark;
            dealerProductView.rate = parentDealerProductView.rate;
            products.add(dealerProductView);
        });
        updateDealerProductRequest.products = products;
        updateDealerProductRequest.dealerId = dealerId;
        dealerProductWebService.update(dealerId, updateDealerProductRequest);
    }

    private void collectCategoryMap(List<DealerCategoryResponse> list, Map<String, DealerCategoryResponse> categoryMap) {
        list.forEach(response -> {
            categoryMap.put(response.category.id.toHexString(), response);
            if (response.children != null && !response.children.isEmpty()) {
                collectCategoryMap(response.children, categoryMap);
            }
        });
    }

    private List<DealerCategoryResponse> toCategoryResponseList(List<InsuranceCategoryNodeResponse> tree) {
        return tree.stream().map(this::toCategoryResponse).collect(Collectors.toList());
    }

    private DealerCategoryResponse toCategoryResponse(InsuranceCategoryNodeResponse node) {
        DealerCategoryResponse response = new DealerCategoryResponse();
        response.category = node.category;
        if (!node.children.isEmpty()) {
            response.children = toCategoryResponseList(node.children);
        }
        return response;
    }

}
