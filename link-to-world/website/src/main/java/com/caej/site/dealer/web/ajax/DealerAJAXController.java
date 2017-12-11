package com.caej.site.dealer.web.ajax;

import static app.dealer.api.dealer.DealerLevelView.LEVEL1;
import static app.dealer.api.dealer.DealerLevelView.LEVEL2;
import static app.dealer.api.dealer.DealerLevelView.LEVEL3;
import static app.dealer.api.dealer.DealerLevelView.LEVEL4;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.order.OrderStatisticsWebService;
import com.caej.order.statistics.SearchSettlementRequest;
import com.caej.order.statistics.StatisticsCountRequest;
import com.caej.order.statistics.StatisticsCountResponse;
import com.caej.site.user.service.UserService;
import com.google.common.collect.Lists;

import app.dealer.api.CreditTrackingWebService;
import app.dealer.api.CreditWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.credit.CreditResponse;
import app.dealer.api.credit.CreditTrackingQuery;
import app.dealer.api.credit.CreditTrackingResponse;
import app.dealer.api.credit.ResetRequest;
import app.dealer.api.credit.UpdateRequest;
import app.dealer.api.dealer.CreateDealerRequest;
import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerStatusView;
import app.dealer.api.dealer.DealerUserQuery;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.dealer.DealerUserStatusView;
import app.dealer.api.dealer.SearchDealerRequest;
import app.dealer.api.dealer.UpdateDealerRequest;
import app.dealer.api.dealer.UpdatePayPasswordRequest;
import io.sited.StandardException;
import io.sited.customer.api.CustomerWebService;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.user.api.user.AuthenticationRequest;
import io.sited.user.api.user.UserResponse;
import io.sited.user.api.user.UserStatusView;
import io.sited.user.web.User;
import io.sited.user.web.controller.user.ChangePasswordRequest;
import io.sited.user.web.service.PinCodeService;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class DealerAJAXController {
    private final Logger logger = LoggerFactory.getLogger(DealerAJAXController.class);

    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    UserService userService;
    @Inject
    CreditWebService creditWebService;
    @Inject
    CreditTrackingWebService creditTrackingWebService;
    @Inject
    OrderStatisticsWebService orderStatisticsWebService;
    @Inject
    CustomerWebService customerWebService;
    @Inject
    PinCodeService pinCodeService;

    @Path("/ajax/dealer/:id/check")
    @GET
    public CheckDealerAJAXResponse check(Request request) throws IOException {
        String id = request.pathParam("id");
        Optional<DealerResponse> dealer = Optional.empty();
        CheckDealerAJAXResponse response = new CheckDealerAJAXResponse();
        try {
            dealer = dealerWebService.get(id);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
        }
        if (!dealer.isPresent()) {
            response.errorMsg = "企业名称不存在";
        } else if (dealer.get().level.equals(LEVEL4)) {
            response.errorMsg = "企业不符合推荐规则";
        }
        return response;
    }

    @Path("/ajax/dealer/settlement")
    @PUT("GET")
    public Response settlement(Request request) {
        User user = request.require(User.class);
        if (!isDealerAdmin(user)) throw new UnauthorizedException(request.path());

        SettlementAJAXRequest settlementAJAXRequest = request.body(SettlementAJAXRequest.class);
        DealerUserQuery dealerUserQuery = new DealerUserQuery();
        DealerUserResponse currentDealerUser = dealerUserWebService.get(user.id).get();
        SearchDealerRequest searchDealerRequest = new SearchDealerRequest();
        searchDealerRequest.name = settlementAJAXRequest.name;
        searchDealerRequest.currentParentId = currentDealerUser.dealerId;
        dealerUserQuery.dealerIds = dealerWebService.children(searchDealerRequest).items.stream().map(dealerResponse -> dealerResponse.id).collect(Collectors.toList());
        dealerUserQuery.dealerIds.add(currentDealerUser.dealerId);
        SearchSettlementRequest searchSettlementRequest = new SearchSettlementRequest();
        searchSettlementRequest.customerIds = dealerUserWebService.find(dealerUserQuery).items.stream().map(dealerUserResponse -> dealerUserResponse.userId).collect(Collectors.toList());
        searchSettlementRequest.productName = settlementAJAXRequest.productName;
        searchSettlementRequest.page = settlementAJAXRequest.page;
        searchSettlementRequest.limit = settlementAJAXRequest.limit;
        return Response.body(orderStatisticsWebService.searchSettlement(searchSettlementRequest));
    }

    @Path("/ajax/dealer/statistics")
    @PUT("GET")
    public Response statistics(Request request) {
        User user = request.require(User.class);
        if (!isDealerAdmin(user)) throw new UnauthorizedException(request.path());

        StatisticsCountAJAXRequest statisticsCountAJAXRequest = request.body(StatisticsCountAJAXRequest.class);
        DealerUserResponse currentDealerUser = dealerUserWebService.get(user.id).get();
        DealerUserQuery dealerUserQuery = new DealerUserQuery();
        if (statisticsCountAJAXRequest.role.equals("dealerAdmin")) {
            SearchDealerRequest searchDealerRequest = new SearchDealerRequest();
            searchDealerRequest.name = statisticsCountAJAXRequest.name;
            searchDealerRequest.parentId = currentDealerUser.dealerId;
            dealerUserQuery.dealerIds = dealerWebService.children(searchDealerRequest).items.stream().map(dealerResponse -> dealerResponse.id).collect(Collectors.toList());
            dealerUserQuery.roles = Lists.newArrayList("dealerAdmin");
        } else {
            dealerUserQuery.dealerId = currentDealerUser.dealerId;
            dealerUserQuery.roles = Lists.newArrayList("dealerUser");
        }
        List<String> userIds = dealerUserWebService.find(dealerUserQuery).items.stream().map(dealerUserResponse -> dealerUserResponse.userId).collect(Collectors.toList());
        Map<String, UserResponse> userMap = userService.find(userIds).stream().collect(Collectors.toMap(userResponse -> userResponse.id, userResponse -> userResponse));

        StatisticsCountRequest statisticsCountRequest = new StatisticsCountRequest();
        statisticsCountRequest.customerIds = userIds;
        statisticsCountRequest.startTime = statisticsCountAJAXRequest.startTime;
        statisticsCountRequest.endTime = statisticsCountAJAXRequest.endTime;
        FindView<StatisticsCountResponse> statisticsCountResponses = orderStatisticsWebService.statistics(statisticsCountRequest);
        FindView<StatisticsCountAJAXResponse> results = new FindView<>();
        results.total = statisticsCountResponses.total;
        logger.info("statisticsCountResponses: {}", JSON.toJSON(statisticsCountResponses.items));
        results.items.addAll(statisticsCountResponses.items.stream().map(statisticsCountResponse -> response(statisticsCountResponse, userMap)).collect(Collectors.toList()));
        return Response.body(results);
    }

    @Path("/ajax/dealer")
    @PUT("GET")
    public Response list(Request request) {
        User user = request.require(User.class);
        DealerResponse currentDealer = request.require(DealerResponse.class);
        if (!isDealerAdmin(user)) throw new UnauthorizedException(request.path());

        DealerAJAXRequest dealerAJAXRequest = request.body(DealerAJAXRequest.class);
        SearchDealerRequest searchDealerRequest = new SearchDealerRequest();
        searchDealerRequest.city = dealerAJAXRequest.city;
        searchDealerRequest.contactName = dealerAJAXRequest.contactName;
        searchDealerRequest.name = dealerAJAXRequest.name;
        searchDealerRequest.page = dealerAJAXRequest.page;
        searchDealerRequest.level = dealerAJAXRequest.level;
        searchDealerRequest.limit = dealerAJAXRequest.limit;
        searchDealerRequest.parentId = currentDealer.id;
        FindView<DealerResponse> dealerResponses = dealerWebService.children(searchDealerRequest);
        return Response.body(FindView.map(dealerResponses, dealerResponse -> dealerAJAXResponse(dealerResponse, currentDealer)));
    }

    @Path("/ajax/dealer/:id/credit")
    @GET
    public Response credit(Request request) {
        Optional<CreditResponse> credit = creditWebService.get(request.pathParam("id"));
        return Response.body(credit.orElse(null));
    }

    @Path("/ajax/dealer/:id/credit/tracking")
    @GET
    public Response creditTrackingList(Request request) {
        User user = request.require(User.class);
        if (!isDealerAdmin(user)) throw new UnauthorizedException(request.path());

        FindView<CreditTrackingResponse> creditTrackingResponses = creditTrackingWebService.find(request.pathParam("id"), new CreditTrackingQuery());
        return Response.body(FindView.map(creditTrackingResponses, this::creditTrackingAJAXResponse));
    }

    @Path("/ajax/dealer/:id/auto-allocate")
    @PUT
    public void switchAutoAllocate(Request request) {
        dealerWebService.switchAutoAllocate(request.pathParam("id"));
    }

    @Path("/ajax/dealer/register")
    @POST
    public void register(Request request) {
        RegisterDealerRequest registerDealerRequest = request.body(RegisterDealerRequest.class);

        pinCodeService.validate(request, registerDealerRequest.username, registerDealerRequest.pinCode);

        DealerResponse parentDealer = null;
        if (registerDealerRequest.parentDealerId != null) {
            Optional<DealerResponse> dealerResponse = Optional.empty();
            try {
                dealerResponse = dealerWebService.get(registerDealerRequest.parentDealerId);
            } catch (StandardException e) {
                logger.error(e.getMessage(), e);
            }
            if (!dealerResponse.isPresent()) {
                throw new BadRequestException("parentDealerId", "dealer.error.parentDealerNoneExists");
            }
            parentDealer = dealerResponse.get();
            if (parentDealer.level == LEVEL4) {
                throw new BadRequestException("parentDealerId", "dealer.error.parentDealerIsLevelNoneEnough");
            }
        }

        UserResponse userResponse = userService.createUser(registerDealerRequest, request);
        request.session().remove("pinCode");

        CreateDealerRequest createDealerRequest = new CreateDealerRequest();
        createDealerRequest.name = registerDealerRequest.name;
        createDealerRequest.email = registerDealerRequest.email;
        createDealerRequest.contactName = registerDealerRequest.contactName;
        createDealerRequest.contactIdNumber = registerDealerRequest.contactIdNumber;
        createDealerRequest.cellphone = registerDealerRequest.cellphone;
        createDealerRequest.state = registerDealerRequest.state;
        createDealerRequest.city = registerDealerRequest.city;
        createDealerRequest.ward = registerDealerRequest.ward;
        createDealerRequest.businessLicence = registerDealerRequest.businessLicence;
        createDealerRequest.status = DealerStatusView.AUDITING;

        if (parentDealer != null) {
            createDealerRequest.parentDealerId = registerDealerRequest.parentDealerId;
            createDealerRequest.level = nextLevel(parentDealer.level);
        } else {
            createDealerRequest.level = LEVEL1;
        }

        createDealerRequest.requestBy = "kdlins-website";
        DealerResponse dealerResponse = dealerWebService.create(createDealerRequest);

        CreateDealerUserRequest createDealerUserRequest = new CreateDealerUserRequest();
        createDealerUserRequest.dealerId = dealerResponse.id;
        createDealerUserRequest.userId = userResponse.id;
        createDealerUserRequest.requestBy = "kdlins-website";
        createDealerUserRequest.roles = Lists.newArrayList("dealerAdmin");
        createDealerUserRequest.channelId = request.cookie("channelId").orElse(null);
        createDealerUserRequest.source = request.cookie("source").orElse(null);
        createDealerUserRequest.roles = Lists.newArrayList("dealerAdmin");
        createDealerUserRequest.status = DealerUserStatusView.AUDITING;
        dealerUserWebService.create(createDealerUserRequest);
    }

    @Path("/ajax/dealer/self/pay-password")
    @PUT
    public void changePayPassword(Request request) {
        User user = request.require(User.class);
        ChangePasswordRequest resetPasswordRequest = request.body(ChangePasswordRequest.class);

        try {
            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.username = user.username;
            authenticationRequest.password = resetPasswordRequest.oldPassword;
            userService.authenticate(authenticationRequest);
        } catch (Exception e) {
            throw new BadRequestException("oldPassword", "user.error.invalidPassword", null, e);
        }

        UpdatePayPasswordRequest updatePayPasswordRequest = new UpdatePayPasswordRequest();
        updatePayPasswordRequest.payPassword = resetPasswordRequest.newPassword;
        updatePayPasswordRequest.requestBy = resetPasswordRequest.requestBy;
        dealerUserWebService.updatePayPassword(user.id, updatePayPasswordRequest);
    }

    @Path("/ajax/dealer/update")
    @PUT
    public void update(Request request) {
        User user = request.require(User.class);
        if (!isDealerAdmin(user) && !isDealerUser(user)) throw new UnauthorizedException(request.path());
        Optional<DealerUserResponse> dealerUserResponse = dealerUserWebService.get(user.id);
        if (!dealerUserResponse.isPresent()) {
            return;
        }
        DealerInfoUpdateAJAXRequest dealerInfoUpdateAJAXRequest = request.body(DealerInfoUpdateAJAXRequest.class);
        if (isDealerAdmin(user)) {
            DealerResponse dealerResponse = dealerWebService.get(dealerUserResponse.get().dealerId).orElseThrow(() -> new UnauthorizedException("no dealer"));
            UpdateDealerRequest updateDealerRequest = updateDealerRequest(dealerResponse);
            updateDealerRequest.email = dealerInfoUpdateAJAXRequest.email;
            updateDealerRequest.contactName = dealerInfoUpdateAJAXRequest.fullName;
            updateDealerRequest.contactIdNumber = dealerInfoUpdateAJAXRequest.contactIdNumber;
            dealerWebService.update(dealerResponse.id, updateDealerRequest);
        }
        userService.updateUser(user.id, dealerInfoUpdateAJAXRequest);
    }

    @Path("/ajax/dealer")
    @POST
    public Response create(Request request) {
        User user = request.require(User.class);
        if (!isDealerAdmin(user)) {
            throw new UnauthorizedException("no permission to update child");
        }
        DealerUserResponse parentDealerUser = dealerUserWebService.get(user.id).get();
        CreateDealerAJAXRequest createDealerAJAXRequest = request.body(CreateDealerAJAXRequest.class);
        DealerResponse parentDealer = dealerWebService.get(parentDealerUser.dealerId).get();
        UserResponse userResponse = userService.createUser(createDealerAJAXRequest, request);

        CreateDealerRequest createDealerRequest = new CreateDealerRequest();
        createDealerRequest.name = createDealerAJAXRequest.name;
        createDealerRequest.email = createDealerAJAXRequest.email;
        createDealerRequest.businessLicence = createDealerAJAXRequest.businessLicence;
        createDealerRequest.contactName = createDealerAJAXRequest.contactName;
        createDealerRequest.contactIdNumber = createDealerAJAXRequest.identification;
        createDealerRequest.cellphone = createDealerAJAXRequest.phone;
        createDealerRequest.state = createDealerAJAXRequest.state;
        createDealerRequest.city = createDealerAJAXRequest.city;
        createDealerRequest.ward = createDealerAJAXRequest.ward;
        createDealerRequest.status = DealerStatusView.ACTIVE;
        createDealerRequest.parentDealerId = parentDealer.id;
        switch (parentDealer.level) {
            case LEVEL1:
                createDealerRequest.level = LEVEL2;
                break;
            case LEVEL2:
                createDealerRequest.level = LEVEL3;
                break;
            case LEVEL3:
                createDealerRequest.level = LEVEL4;
                break;
            default:
                throw new UnauthorizedException("no permission to create child dealer");
        }
        DealerResponse dealerResponse = dealerWebService.create(createDealerRequest);

        CreateDealerUserRequest createDealerUserRequest = new CreateDealerUserRequest();
        createDealerUserRequest.dealerId = dealerResponse.id;
        createDealerUserRequest.userId = userResponse.id;
        createDealerUserRequest.requestBy = "kdlins-website";
        createDealerUserRequest.roles = Lists.newArrayList("dealerAdmin");
        createDealerUserRequest.status = DealerUserStatusView.ACTIVE;
        dealerUserWebService.create(createDealerUserRequest);
        return Response.empty();
    }

    @Path("/ajax/dealer/:id")
    @PUT
    public Response updateChild(Request request) {
        User user = request.require(User.class);
        if (!isDealerAdmin(user)) {
            throw new UnauthorizedException("no permission to update child");
        }

        String dealerId = request.pathParam("id");
        DealerResponse dealer = dealerWebService.get(dealerId).get();
        UpdateDealerAJAXRequest updateDealerAJAXRequest = request.body(UpdateDealerAJAXRequest.class);
        userService.updateUser(dealerUserWebService.getByDealerId(dealerId).get().userId, updateDealerAJAXRequest.contactIdNumber);
        updateDealer(updateDealerAJAXRequest, dealer);
        return Response.empty();
    }

    @Path("/ajax/dealer/:id/update/quota")
    @PUT
    public Response updateQuota(Request request) {
        DealerResponse dealer = dealerWebService.get(request.pathParam("id")).get();
        User user = request.require(User.class);

        if (!isParentDealerAdmin(user, dealer)) {
            throw new UnauthorizedException("failed to reset dealer quota, userId={}, dealerId={}", user.id, dealer.id);
        }

        DealerUserResponse parentDealerUser = dealerUserWebService.get(user.id).get();
        UpdateQuotaAJAXRequest updateQuotaAJAXRequest = request.body(UpdateQuotaAJAXRequest.class);
        UpdateRequest updateCreditRequest = new UpdateRequest();
        updateCreditRequest.quota = updateQuotaAJAXRequest.quota;
        updateCreditRequest.parentDealerId = parentDealerUser.dealerId;
        updateCreditRequest.operator = user.username;
        updateCreditRequest.requestBy = "kdlins-website";
        creditWebService.update(dealer.id, updateCreditRequest);
        return Response.empty();
    }

    @Path("/ajax/dealer/:id/reset/quota")
    @PUT
    public Response resetQuota(Request request) {
        DealerResponse dealer = dealerWebService.get(request.pathParam("id")).get();
        User user = request.require(User.class);

        if (!isParentDealerAdmin(user, dealer)) {
            throw new UnauthorizedException("failed to reset dealer quota, userId={}, dealerId={}", user.id, dealer.id);
        }

        DealerUserResponse parentDealerUser = dealerUserWebService.get(user.id).get();
        ResetRequest resetRequest = new ResetRequest();
        resetRequest.parentDealerId = parentDealerUser.dealerId;
        resetRequest.operator = user.username;
        resetRequest.requestBy = "kdlins-website";
        creditWebService.reset(dealer.id, resetRequest);
        return Response.empty();
    }

    @Path("/ajax/dealer/:id/quota/status")
    @PUT
    public Response updateQuotaStatus(Request request) {
        DealerResponse dealer = dealerWebService.get(request.pathParam("id")).get();
        User user = request.require(User.class);

        if (!isParentDealerAdmin(user, dealer)) {
            throw new UnauthorizedException("failed to reset dealer quota, userId={}, dealerId={}", user.id, dealer.id);
        }

        creditWebService.updateStatus(dealer.id);
        return Response.empty();
    }

    @Path("/ajax/dealer/:id/block")
    @PUT
    public void block(Request request) {
        User user = request.require(User.class);
        String id = request.pathParam("id");
        DealerResponse dealer = dealerWebService.get(id).get();
        if (!isParentDealerAdmin(user, dealer)) {
            throw new UnauthorizedException("failed to block dealer, userId={}, dealerId={}", user.id, dealer.id);
        }
        dealerWebService.lock(id);
    }

    @Path("/ajax/dealer/:id/unblock")
    @PUT
    public void unblock(Request request) {
        User user = request.require(User.class);
        String id = request.pathParam("id");
        DealerResponse dealer = dealerWebService.get(id).get();
        if (!isParentDealerAdmin(user, dealer)) {
            throw new UnauthorizedException("failed to block dealer, userId={}, dealerId={}", user.id, dealer.id);
        }
        String userId = dealerUserWebService.getByDealerId(id).get().userId;
        dealerWebService.unlock(id);
        userService.updateStatus(userId, UserStatusView.ACTIVE);
    }

    @Path("/ajax/dealer/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        DealerResponse dealer = dealerWebService.get(id).get();
        User user = request.require(User.class);
        if (!isParentDealerAdmin(user, dealer)) {
            throw new UnauthorizedException("failed to delete dealer, userId={}, dealerId={}", user.id, dealer.id);
        }

        dealerDelete(id, dealerUserWebService.getByDealerId(id).get().userId);

        SearchDealerRequest searchDealerRequest = new SearchDealerRequest();
        searchDealerRequest.currentParentId = id;
        dealerWebService.children(searchDealerRequest).forEach(dealerResponse -> {
            String dealerId = dealerResponse.id;
            dealerDelete(dealerId, dealerUserWebService.getByDealerId(dealerId).get().userId);
        });
    }

    private CreditTrackingAJAXResponse creditTrackingAJAXResponse(CreditTrackingResponse creditTrackingResponse) {
        CreditTrackingAJAXResponse creditTrackingAJAXResponse = new CreditTrackingAJAXResponse();
        creditTrackingAJAXResponse.quota = creditTrackingResponse.quota;
        creditTrackingAJAXResponse.type = creditTrackingResponse.type;
        creditTrackingAJAXResponse.totalCredits = creditTrackingResponse.totalCredits;
        creditTrackingAJAXResponse.operator = creditTrackingResponse.operator;
        creditTrackingAJAXResponse.createdTime = creditTrackingResponse.createdTime;
        return creditTrackingAJAXResponse;
    }

    private StatisticsCountAJAXResponse response(StatisticsCountResponse statisticsCountResponse, Map<String, UserResponse> userMap) {
        StatisticsCountAJAXResponse statisticsCountAJAXResponse = new StatisticsCountAJAXResponse();
        statisticsCountAJAXResponse.username = userMap.get(statisticsCountResponse.customerId).username;
        statisticsCountAJAXResponse.insuredFee = statisticsCountResponse.insuredFee;
        statisticsCountAJAXResponse.insuredNum = statisticsCountResponse.insuredNum;
        statisticsCountAJAXResponse.insuredInsuredNum = statisticsCountResponse.insuredInsuredNum;
        statisticsCountAJAXResponse.surrenderFee = statisticsCountResponse.surrenderFee;
        statisticsCountAJAXResponse.surrenderNum = statisticsCountResponse.surrenderNum;
        statisticsCountAJAXResponse.surrenderInsuredNum = statisticsCountResponse.surrenderInsuredNum;
        statisticsCountAJAXResponse.rejectFee = statisticsCountResponse.rejectFee;
        statisticsCountAJAXResponse.rejectNum = statisticsCountResponse.rejectNum;
        statisticsCountAJAXResponse.rejectInsuredNum = statisticsCountResponse.rejectInsuredNum;
        return statisticsCountAJAXResponse;
    }

    private void dealerDelete(String id, String userId) {
        dealerWebService.delete(id);
        dealerUserWebService.delete(userId);
        userService.delete(userId);
        customerWebService.delete(userId);

        DealerUserQuery query = new DealerUserQuery();
        query.dealerId = id;
        dealerUserWebService.find(query).items.forEach(dealerUserResponse -> {
            dealerUserWebService.delete(dealerUserResponse.userId);
            userService.delete(dealerUserResponse.userId);
            customerWebService.delete(dealerUserResponse.userId);
        });
    }

    private boolean isDealerUser(User user) {
        List<String> roles = user.roles;
        return roles != null && roles.contains("dealerUser");
    }

    private boolean isDealerAdmin(User user) {
        List<String> roles = user.roles;
        return roles != null && roles.contains("dealerAdmin");
    }

    private boolean isParentDealerAdmin(User user, DealerResponse dealerResponse) {
        if (isDealerAdmin(user)) {
            Optional<DealerUserResponse> dealer = dealerUserWebService.get(user.id);
            return dealer.isPresent() && dealer.get().dealerId.equals(dealerResponse.parentDealerId);
        }
        return false;
    }

    private DealerAJAXResponse dealerAJAXResponse(DealerResponse dealer, DealerResponse currentDealer) {
        DealerAJAXResponse response = new DealerAJAXResponse();
        DealerUserResponse dealerUser = dealerUserWebService.getByDealerId(dealer.id).get();
        UserResponse user = userService.get(dealerUser.userId);
        response.id = dealer.id;
        response.username = user.username;
        response.name = dealer.name;
        response.contactName = dealer.contactName;
        response.phone = dealer.cellphone;
        response.email = dealer.email;
        response.city = dealer.city;
        response.level = dealer.level;
        response.createDate = dealer.createdTime;
        response.status = dealerUser.status;
        response.canEdit = dealer.parentDealerId.equals(currentDealer.id) && currentDealer.status.equals(DealerStatusView.ACTIVE);
        return response;
    }

    private DealerLevelView nextLevel(DealerLevelView current) {
        DealerLevelView[] values = DealerLevelView.values();
        for (int i = 0; i < values.length; i++) {
            if (current == values[i]) {
                return values[i + 1];
            }
        }
        throw new BadRequestException("parentDealerId", "dealer.error.parentDealerIsLevelNoneEnough");
    }

    private UpdateDealerRequest updateDealerRequest(DealerResponse dealerResponse) {
        UpdateDealerRequest updateDealerRequest = new UpdateDealerRequest();
        updateDealerRequest.name = dealerResponse.name;
        updateDealerRequest.email = dealerResponse.email;
        updateDealerRequest.contactName = dealerResponse.contactName;
        updateDealerRequest.contactIdNumber = dealerResponse.contactIdNumber;
        updateDealerRequest.cellphone = dealerResponse.cellphone;
        updateDealerRequest.state = dealerResponse.state;
        updateDealerRequest.city = dealerResponse.city;
        updateDealerRequest.requestBy = "kdlins-website";
        updateDealerRequest.level = dealerResponse.level;
        updateDealerRequest.businessLicence = dealerResponse.businessLicence;
        return updateDealerRequest;
    }

    private void updateDealer(UpdateDealerAJAXRequest updateDealerAJAXRequest, DealerResponse dealer) {
        UpdateDealerRequest updateDealerRequest = new UpdateDealerRequest();
        updateDealerRequest.name = updateDealerAJAXRequest.name;
        updateDealerRequest.email = updateDealerAJAXRequest.email;
        updateDealerRequest.contactName = updateDealerAJAXRequest.contactName;
        updateDealerRequest.contactIdNumber = updateDealerAJAXRequest.contactIdNumber;
        updateDealerRequest.cellphone = updateDealerAJAXRequest.cellphone;
        updateDealerRequest.state = updateDealerAJAXRequest.state;
        updateDealerRequest.city = updateDealerAJAXRequest.city;
        updateDealerRequest.level = dealer.level;

        dealerWebService.update(dealer.id, updateDealerRequest);
    }

}
