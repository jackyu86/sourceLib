package com.caej.site.customer.web;

import java.util.Map;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.product.BatchGetProductRequest;
import com.caej.product.api.product.SearchProductResponse;
import com.google.common.collect.Maps;

import app.dealer.api.CreditWebService;
import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerStatusView;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.product.SearchDealerProductRequest;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UserResponse;
import io.sited.user.web.User;
import io.sited.web.WebConfig;

/**
 * @author Jonathan.Guo
 */
public class DealerController {
    @Inject
    UserWebService userWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    ProductSearchWebService productSearchWebService;
    @Inject
    WebConfig webConfig;
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;
    @Inject
    CreditWebService creditWebService;

    @Path("/account/dealer")
    @GET
    public Response list(Request request) {
        Map<String, Object> context = Maps.newHashMap();
        User user = request.require(User.class);

        DealerUserResponse dealerUser = dealerUserWebService.get(user.id).get();
        DealerResponse dealer = dealerWebService.get(dealerUser.dealerId).get();
        context.put("currentLevel", dealer.level);
        context.put("canCreate", dealer.level != null && dealer.level != DealerLevelView.LEVEL4);

        return Response.template("/user/dealer/dealer-list.html", context);
    }

    @Path("/account/dealer/create")
    @GET
    public Response create(Request request) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("states", insuranceCountryWebService.provinceByCountryCode("CHN"));
        return Response.template("/user/dealer/dealer-create.html", data);
    }

    @Path("/account/dealer/:id")
    @GET
    public Response update(Request request) {
        ObjectId id = request.pathParam("id", ObjectId.class);
        DealerResponse dealerResponse = dealerWebService.get(id.toHexString()).get();
        DealerModel dealerModel = new DealerModel();
        dealerModel.id = dealerResponse.id;
        dealerModel.name = dealerResponse.name;
        dealerModel.email = dealerResponse.email;
        dealerModel.contactName = dealerResponse.contactName;
        dealerModel.contactIdNumber = dealerResponse.contactIdNumber;
        dealerModel.cellphone = dealerResponse.cellphone;
        dealerModel.level = dealerResponse.level;
        dealerModel.state = dealerResponse.state;
        dealerModel.city = dealerResponse.city;
        dealerModel.ward = dealerResponse.ward;
        UserResponse userResponse = userWebService.get(dealerUserWebService.getByDealerId(dealerResponse.id).get().userId);
        dealerModel.username = userResponse.username;
        Map<String, Object> data = Maps.newHashMap();
        data.put("dealer", dealerModel);
        data.put("states", insuranceCountryWebService.provinceByCountryCode("CHN"));
        data.put("user", request.require(User.class));
        if (dealerResponse.status.equals(DealerStatusView.ACTIVE)) {
            return Response.template("/user/dealer/dealer-detail.html", data);
        }
        return Response.template("/user/dealer/dealer-update.html", data);
    }

    @Path("/account/dealer/invite")
    @GET
    public Response invite(Request request) {
        User user = request.require(User.class, null);
        if (user == null) {
            throw new UnauthenticatedException("user not login");
        }
        Map<String, Object> context = Maps.newHashMap();
        context.put("inviteUrl", webConfig.baseURL() + "/account/register?channelId=dealer&source=" + dealerUserWebService.get(user.id).get().dealerId);
        return Response.template("/user/dealer/dealer-invite.html", context);
    }

    @Path("/account/dealer/statistic")
    @GET
    public Response statistic(Request request) {
        Map<String, Object> context = Maps.newHashMap();

        return Response.template("/user/dealer/dealer-statistic.html", context);
    }

    @Path("/account/dealer/settlement")
    @GET
    public Response settlement(Request request) {
        Map<String, Object> context = Maps.newHashMap();
        return Response.template("/user/dealer/dealer-settlement.html", context);
    }

    @Path("/account/dealer/:id/product")
    @GET
    public Response dealerProduct(Request request) {
        ObjectId id = request.pathParam("id", ObjectId.class);
        DealerResponse dealerResponse = dealerWebService.get(id.toHexString()).orElseThrow(() -> new NotFoundException("no dealer"));
        User user = request.require(User.class);
        DealerUserResponse dealerUserResponse = dealerUserWebService.get(user.id).orElseThrow(() -> new UnauthorizedException("not dealer"));
        SearchDealerProductRequest searchRequest = new SearchDealerProductRequest();
        searchRequest.page = 1;
        searchRequest.limit = Integer.MAX_VALUE;
        FindView<String> productNames = dealerProductWebService.search(dealerUserResponse.dealerId, searchRequest);
        BatchGetProductRequest batchGetProductRequest = new BatchGetProductRequest();
        batchGetProductRequest.productNames = productNames;
        FindView<SearchProductResponse> productResponses = productSearchWebService.batchGet(batchGetProductRequest);
        FindView<String> dealerProductNames = dealerProductWebService.search(dealerResponse.id, searchRequest);

        FindView<DealerProductModel> products = new FindView<>();
        products.page = searchRequest.page;
        products.page = searchRequest.limit;
        products.total = (long) productNames.items.size();

        productResponses.forEach(productResponse -> {
            DealerProductModel dealerProductModel = new DealerProductModel();
            dealerProductModel.product = productResponse;
            dealerProductModel.checked = dealerProductNames.items.contains(productResponse.name);
            products.items.add(dealerProductModel);
        });
        Map<String, Object> data = Maps.newHashMap();
        data.put("products", products);
        data.put("dealer", dealerResponse);
        return Response.template("/user/dealer/dealer-product.html", data);
    }
}
