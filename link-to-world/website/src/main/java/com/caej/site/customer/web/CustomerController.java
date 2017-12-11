package com.caej.site.customer.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.cart.api.CartWebService;
import com.caej.client.ProductWebServiceClient;
import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.order.OrderWebService;
import com.caej.order.order.OrderStatusView;
import com.caej.order.order.OrderView;
import com.caej.order.order.SearchOrderRequest;
import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.ProductPriceWebService;
import com.caej.product.api.price.ProductPriceRequest;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import app.dealer.api.CreditWebService;
import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.PolicyHolderWebService;
import app.dealer.api.credit.CreditResponse;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.policyholder.PolicyHolderResponse;
import app.dealer.api.product.DealerProductView;
import io.sited.StandardException;
import io.sited.customer.api.AddressWebService;
import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.address.AddressResponse;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.user.api.UserWebService;
import io.sited.user.web.User;
import io.sited.util.JSON;
import io.sited.web.WebConfig;

/**
 * @author chi
 */
public class CustomerController {
    @Inject
    UserWebService userWebService;
    @Inject
    CustomerWebService customerWebService;
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    PolicyHolderWebService policyHolderWebService;
    @Inject
    OrderWebService orderWebService;
    @Inject
    ProductWebServiceClient productWebServiceClient;
    @Inject
    CartWebService cartWebService;
    @Inject
    ProductPriceWebService productPriceWebService;
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;
    @Inject
    ProductFormWebService productFormWebService;
    @Inject
    CreditWebService creditWebService;
    @Inject
    WebConfig webConfig;
    @Inject
    AddressWebService addressWebService;
    @Inject
    DealerProductWebService dealerProductWebService;

    @Path("/account/complete")
    @GET
    public Response completeInfo(Request request) {
        User user = request.require(User.class);
        updateSessionUser(user, request);
        HashMap<String, Object> data = Maps.newHashMap();
        if (isDealerUser(user)) {
            return Response.template("/user/dealer/complete-info.html", data);
        } else {
            CustomerResponse customerResponse = customerWebService.get(user.id);
            data.put("customer", customerResponse);
            return Response.template("/user/customer/complete-info.html", data);
        }
    }

    @Path("/account/verifying")
    @GET
    public Response verifying(Request request) {
        return Response.template("/user/dealer/verifying.html", Maps.newHashMap());
    }

    @Path("/account/info")
    @GET
    public Response customerInfo(Request request) {
        User user = request.require(User.class);
        Map<String, Object> data = Maps.newHashMap();
        data.put("user", userWebService.get(user.id));
        data.put("customer", customerModel(customerWebService.get(user.id)));
        data.put("states", insuranceCountryWebService.provinceByCountryCode("CHN"));
        if (isDealerUser(user)) {
            DealerUserResponse dealerUserResponse = dealerUserWebService.get(user.id).orElseThrow(() -> new StandardException("not found dealer, userId:" + user.id));
            DealerResponse dealerResponse = dealerWebService.get(dealerUserResponse.dealerId).orElseThrow(() -> new StandardException("not found dealer, userId:" + user.id));
            data.put("dealer", dealerResponse);
            data.put("parentName", "无");
            if (dealerResponse.parentDealerId != null) {
                Optional<DealerResponse> parentDealer = dealerWebService.get(dealerResponse.parentDealerId);
                parentDealer.ifPresent(parent -> data.put("parentName", parent.name));
            }
            Optional<CreditResponse> credit = creditWebService.get(dealerResponse.id);
            Double totalCredits = credit.isPresent() ? credit.get().totalCredits : 0d;
            data.put("totalCredits", totalCredits);
            data.put("userRoles", userRoles(user.roles));
            return Response.template("/user/dealer/info.html", data);
        } else {
            // 匿名购买者手机号是UUID随机生成的，显示在页面放空
            if (Strings.isNullOrEmpty(user.phone) || !Pattern.compile("^1[3578]{1}\\d{9}$").matcher(user.phone).matches())
                user.phone = "";
            List<AddressResponse> addressList = addressWebService.findByCustomerId(user.id);
            data.put("address", addressList.isEmpty() ? new AddressResponse() : addressList.get(0));
            data.put("user", user);
            return Response.template("/user/customer/info.html", data);
        }
    }

    @Path("/account/password")
    @GET
    public Response managePassword(Request request) {
        return Response.template("/user/manage-password.html", Maps.newHashMap());
    }

    @Path("/account/policy-holder")
    @GET
    public Response policyHolder(Request request) {
        User user = request.require(User.class);
        Optional<DealerUserResponse> dealerUserResponse = dealerUserWebService.get(user.id);
        if (!dealerUserResponse.isPresent()) {
            throw new BadRequestException("user", null, "Dealer not exists, userId={}", user.id);
        }
        FindView<PolicyHolderResponse> findView = policyHolderWebService.list(dealerUserResponse.get().dealerId);
        HashMap<String, Object> data = Maps.newHashMap();
        data.put("policyHolders", findView);
        return Response.template("/user/policy-holder.html", data);
    }

    @Path("/account/policy-holder/create")
    @GET
    public Response createPolicyHolder(Request request) {
        return Response.template("/user/policy-holder-create.html", Maps.newHashMap());
    }

    @Path("/account/policy-holder/:id/update")
    @GET
    public Response updatePolicyHolder(Request request) {
        String policyHolderId = request.pathParam("id");
        PolicyHolderResponse policyHolderResponse = policyHolderWebService.get(new ObjectId(policyHolderId)).orElseThrow(() -> new BadRequestException("policy holder", null, "NotNull", "no policy holder"));
        Map<String, Object> data = Maps.newHashMap();
        data.put("policyHolder", policyHolderResponse);
        return Response.template("/user/policy-holder-update.html", data);
    }

    @Path("/account/order")
    @GET
    public Response order(Request request) {
        User user = request.require(User.class);
        SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
        searchOrderRequest.customerId = user.id;

        searchOrderRequest.page = request.queryParam("page", Integer.class).orElse(1);
        searchOrderRequest.limit = request.queryParam("limit", Integer.class).orElse(10);
        Optional<LocalDate> orderDateFromOptional = request.queryParam("orderDateFrom", LocalDate.class);
        searchOrderRequest.orderDateFrom = orderDateFromOptional.isPresent() ? orderDateFromOptional.get().atStartOfDay() : null;
        Optional<LocalDate> orderDateToOptional = request.queryParam("orderDateTo", LocalDate.class);
        searchOrderRequest.orderDateTo = orderDateToOptional.isPresent() ? orderDateToOptional.get().atStartOfDay() : null;
        searchOrderRequest.status = request.queryParam("status", OrderStatusView.class).orElse(null);
        searchOrderRequest.name = request.queryParam("name", String.class).orElse(null);
        searchOrderRequest.orderNumber = request.queryParam("orderNumber", String.class).orElse(null);
        searchOrderRequest.insuredName = request.queryParam("insuredName", String.class).orElse(null);

        Map<String, Object> context = Maps.newHashMap();
        FindView<OrderView> orders = orderWebService.search(searchOrderRequest);
        Map<String, DealerProductView> dealerProductMap = Maps.newHashMap();
        if (isDealerUser(user)) {
            DealerUserResponse dealerUser = dealerUserWebService.get(user.id).get();
            List<DealerProductView> dealerProducts = dealerProductWebService.get(dealerUser.dealerId).get().products;
            dealerProducts.forEach(dealerProductView -> dealerProductMap.put(dealerProductView.productName, dealerProductView));
        }

        context.put("orders", FindView.map(orders, (order -> orderDisplayModel(order, dealerProductMap))));
        context.put("status", searchOrderRequest.status);
        context.put("orderDateFrom", searchOrderRequest.orderDateFrom == null ? null : searchOrderRequest.orderDateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        context.put("orderDateTo", searchOrderRequest.orderDateTo == null ? null : searchOrderRequest.orderDateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        context.put("request", searchOrderRequest);
        return Response.template("/user/order.html", context);
    }

    @Path("/account/order/:orderId")
    @GET
    public Response orderDetail(Request request) {
        User user = request.require(User.class, null);
        if (user == null) throw new UnauthenticatedException("user not login");
        String orderId = request.pathParam("orderId");
        OrderView orderView = orderWebService.get(orderId);
        if (!orderView.customerId.equals(user.id)) throw new UnauthenticatedException("operation forbidden");
        OrderModel orderModel = new OrderModel();
        orderModel.orderView = orderView;

        Map<String, Object> context = Maps.newHashMap();
        context.put("order", orderModel);
        context.put("id", orderView.productId);
        context.put("form", productFormWebService.checkoutPreview(orderView.productId, orderView.form));

        return Response.template("/user/order-detail.html", context);
    }

    @Path("/account/cart")
    @GET
    public Response cart(Request request) {
        User user = request.require(User.class, null);
        String id;
        if (user == null) {
            id = request.client().id();
        } else {
            id = user.id;
        }
        List<CartModel> carts = Lists.newArrayList();
        cartWebService.get(id).ifPresent(cartResponse -> cartResponse.items.forEach(item -> {
            CartModel cartModel = new CartModel();
            cartModel.item = item;
            cartModel.product = productWebServiceClient.get(item.productId);
            ProductPriceRequest productPriceRequest = new ProductPriceRequest();
            productPriceRequest.productId = item.productId;
            productPriceRequest.formValue = item.form;
            cartModel.item.price = productPriceWebService.calculate(productPriceRequest);
            carts.add(cartModel);
        }));
        Map<String, Object> context = Maps.newHashMap();
        context.put("user", user);
        if (carts.size() > 0) {
            context.put("carts", carts);
            return Response.template("/user/customer/cart.html", context);
        } else {
            return Response.template("/user/customer/cart-empty.html", context);
        }
    }

    @Path("/account/dealer/user")
    @GET
    public Response dealerUser(Request request) {
        return Response.template("/user/dealer/dealer-user-list.html", Maps.newHashMap());
    }

    @Path("/account/invite")
    @GET
    public Response invite(Request request) {
        User user = request.require(User.class, null);
        if (user == null) {
            throw new UnauthenticatedException("user not login");
        }
        Map<String, Object> context = Maps.newHashMap();
        context.put("inviteUrl", webConfig.baseURL() + "/account/register?channelId=customer&source=" + user.id);
        return Response.template("/user/invite.html", context);
    }

    private CustomerModel customerModel(CustomerResponse response) {
        CustomerModel instance = new CustomerModel();
        instance.id = response.id;
        instance.identification = response.identification;
        instance.birthday = response.birthday;
        instance.gender = GenderModel.valueOf(response.gender.name());
        instance.state = response.state;
        instance.city = response.city;
        instance.ward = response.ward;
        instance.currencyCode = response.currencyCode;
        instance.countryCode = response.countryCode;
        instance.channelId = response.channelId;
        instance.campaignId = response.campaignId;
        instance.source = response.source;
        instance.createdTime = response.createdTime;
        instance.createdBy = response.createdBy;
        instance.updatedTime = response.updatedTime;
        instance.updatedBy = response.updatedBy;
        return instance;
    }

    private OrderDisplayModel orderDisplayModel(OrderView order, Map<String, DealerProductView> dealerProductMap) {
        OrderDisplayModel display = new OrderDisplayModel();
        display.id = order.id;
        display.productDisplayName = order.productDisplayName;
        display.planStartTime = order.planStartTime;
        display.items = order.items;
        display.periodDisplayName = order.periodDisplayName;
        display.total = order.total;
        display.orderStatus = order.orderStatus;
        if (dealerProductMap.containsKey(order.productName)) {
            display.surrenderMark = dealerProductMap.get(order.productName).surrenderMark;
        }
        return display;
    }

    private void updateSessionUser(User user, Request request) {
        User sessionUser = new User();
        sessionUser.id = user.id;
        sessionUser.username = user.username;
        sessionUser.fullName = user.fullName;
        sessionUser.roles = user.roles;
        sessionUser.status = user.status;
        request.session().set("user", JSON.toJSON(sessionUser));
    }

    private boolean isDealerUser(User user) {
        List<String> roles = user.roles;
        return roles != null && (roles.contains("dealerAdmin") || roles.contains("dealerUser"));
    }

    private String userRoles(List<String> roles) {
        StringBuilder builder = new StringBuilder();
        roles.forEach(role -> {
            if ("dealerAdmin".equals(role)) {
                builder.append("管理员，");
            }
            if ("dealerUser".equals(role)) {
                builder.append("出单员，");
            }
        });
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }
}
