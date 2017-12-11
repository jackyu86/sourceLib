package com.caej.site.page.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.client.DealerProductWebServiceClient;
import com.caej.client.ProductSearchWebServiceClient;
import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.BatchInsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryResponse;
import com.google.common.collect.Maps;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerUserResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.user.web.User;

/**
 * @author chi
 */
public class IndexController {
    @Inject
    InsuranceCategoryWebService insuranceCategoryWebService;
    @Inject
    DealerProductWebServiceClient dealerProductWebServiceClient;
    @Inject
    ProductSearchWebServiceClient productSearchWebServiceClient;
    @Inject
    DealerUserWebService dealerUserWebService;

    @Path("/")
    @GET
    public Response get(Request request) {
        List<InsuranceCategoryResponse> categories;
        Map<String, Object> context = Maps.newHashMap();
        User user = request.require(User.class, null);
        if (user != null) {
            Optional<DealerUserResponse> optional = dealerUserWebService.get(user.id);
            if (optional.isPresent()) {
                List<ObjectId> categoryIds = dealerProductWebServiceClient.category(optional.get().dealerId).categoryIds;
                BatchInsuranceCategoryRequest batchInsuranceCategoryRequest = new BatchInsuranceCategoryRequest();
                batchInsuranceCategoryRequest.categoryIds = categoryIds;
                categories = insuranceCategoryWebService.firstLevel(batchInsuranceCategoryRequest);
                context.put("isDealer", true);
            } else {
                categories = productSearchWebServiceClient.firstLevelCategory().list;
                context.put("isDealer", false);
            }
        } else {
            categories = productSearchWebServiceClient.firstLevelCategory().list;
            context.put("isDealer", false);
        }
        context.put("user", user);
        context.put("categories", categories);
        return Response.template("/index.html", context);
    }
}