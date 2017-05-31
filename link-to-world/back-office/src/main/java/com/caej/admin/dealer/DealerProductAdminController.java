package com.caej.admin.dealer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.SearchProductRequest;
import com.google.common.collect.Maps;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.DealerResponse;
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

/**
 * Created by hubery.chen on 2017/1/3.
 */
public class DealerProductAdminController {
    @Inject
    DealerWebService dealerWebService;
    @Inject
    ProductWebService productWebService;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    InsuranceCategoryWebService insuranceCategoryWebService;

    @Path("/admin/ajax/dealer/:dealerId/products")
    @GET
    public Response dealerProductList(Request request) {
        String dealerId = request.pathParam("dealerId");
        DealerResponse dealerResponse = dealerWebService.get(dealerId).orElseThrow(() -> new NotFoundException("no dealer"));
        SearchDealerProductRequest searchRequest = new SearchDealerProductRequest();
        searchRequest.page = 1;
        searchRequest.limit = Integer.MAX_VALUE;

        SearchProductRequest searchProductRequest = new SearchProductRequest();
        searchProductRequest.page = 1;
        searchProductRequest.limit = Integer.MAX_VALUE;
        List<ProductResponse> productList = productWebService.list();
        Map<String, ProductResponse> productMap = Maps.newHashMap();
        productList.forEach(productResponse -> productMap.put(productResponse.name, productResponse));
        FindView<DealerProductView> dealerProducts = dealerProductWebService.list(dealerResponse.id, searchRequest);
        Map<String, DealerProductView> dealerProductMap = Maps.newHashMap();
        dealerProducts.forEach(dealerProductView -> dealerProductMap.put(dealerProductView.productName, dealerProductView));
        List<InsuranceCategoryNodeResponse> tree = insuranceCategoryWebService.tree();
        List<DealerCategoryResponse> categoryResponses = toCategoryResponseList(tree);
        Map<String, DealerCategoryResponse> categoryMap = Maps.newHashMap();
        collectCategoryMap(categoryResponses, categoryMap);

        productList.forEach(product -> {
            DealerProductResponse response = new DealerProductResponse();
            DealerProductView dealerProduct = dealerProductMap.get(product.name);
            response.name = product.name;
            response.displayName = productMap.get(response.name).displayName;
            if (dealerProduct != null) {
                response.checked = true;
                response.surrenderMark = dealerProduct.surrenderMark;
                response.bankAccountMark = dealerProduct.bankAccountMark;
                response.rate = dealerProduct.rate;
            } else {
                response.checked = false;
            }
            response.categoryList = product.insuranceCategoryIds;
            response.categoryList.forEach(categoryId -> categoryMap.get(categoryId.toHexString()).productList.add(response));
        });
        return Response.body(categoryResponses);
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

    @Path("/admin/ajax/dealer/:dealerId/products")
    @PUT
    public void updateDealerProduct(Request request) {
        String dealerId = request.pathParam("dealerId");
        UpdateDealerProductAJAXRequest updateDealerProductAJAXRequest = request.body(UpdateDealerProductAJAXRequest.class);
        if (updateDealerProductAJAXRequest.productList == null)
            return;
        UpdateDealerProductRequest updateDealerProductRequest = new UpdateDealerProductRequest();
        updateDealerProductAJAXRequest.productList.forEach(product -> {
            product.insuranceCategoryIds = productWebService.getByName(product.productName).insuranceCategoryIds;
        });
        updateDealerProductRequest.products = updateDealerProductAJAXRequest.productList;
        updateDealerProductRequest.dealerId = dealerId;
        dealerProductWebService.update(dealerId, updateDealerProductRequest);
    }

}
