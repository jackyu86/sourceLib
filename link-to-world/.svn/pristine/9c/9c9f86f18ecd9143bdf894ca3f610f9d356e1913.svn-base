package com.caej.admin.dealer;

import static app.dealer.api.dealer.DealerLevelView.LEVEL1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.country.FindProvincesRequest;
import com.caej.insurance.api.country.InsuranceProvinceResponse;
import com.caej.order.OrderStatisticsWebService;
import com.caej.order.statistics.AdminStatisticsCountRequest;
import com.caej.order.statistics.AdminStatisticsCountResponse;
import com.caej.order.statistics.SearchSettlementRequest;
import com.google.common.collect.Lists;

import app.dealer.api.CreditTrackingWebService;
import app.dealer.api.CreditWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.credit.CreditStatusView;
import app.dealer.api.credit.CreditTrackingQuery;
import app.dealer.api.credit.CreditTrackingResponse;
import app.dealer.api.credit.ResetRequest;
import app.dealer.api.credit.UpdateRequest;
import app.dealer.api.dealer.CreateDealerRequest;
import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerQuery;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerStatusView;
import app.dealer.api.dealer.DealerUserQuery;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.dealer.DealerUserStatusView;
import app.dealer.api.dealer.SearchDealerRequest;
import app.dealer.api.dealer.UpdateDealerRequest;
import io.sited.admin.AdminUser;
import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.GenderView;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UpdateUserStatusRequest;
import io.sited.user.api.user.UserResponse;
import io.sited.user.api.user.UserStatusView;

/**
 * Created by Hubery.Chen
 */
public class DealerAdminController {
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    UserWebService userWebService;
    @Inject
    CustomerWebService customerWebService;
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;
    @Inject
    CreditWebService creditWebService;
    @Inject
    CreditTrackingWebService creditTrackingWebService;
    @Inject
    OrderStatisticsWebService orderStatisticsWebService;

    @Path("/admin/ajax/dealer/:id/credit/status")
    @GET
    public CreditStatusView changeCreditStatus(Request request) {
        String dealerId = request.pathParam("id");
        SearchDealerRequest searchDealerRequest = new SearchDealerRequest();
        searchDealerRequest.parentId = dealerId;
        dealerWebService.children(searchDealerRequest).items.forEach(dealerResponse -> {
            creditWebService.updateStatus(dealerResponse.id);
        });
        return creditWebService.updateStatus(dealerId);
    }

    @Path("/admin/ajax/dealer/:id/credit/tracking")
    @GET
    public FindView<CreditTrackingResponse> creditTrackingList(Request request) {
        return creditTrackingWebService.find(request.pathParam("id"), new CreditTrackingQuery());
    }

    @Path("/admin/ajax/dealer/statistics")
    @PUT
    public FindView<StatisticsCountAJAXResponse> statistics(Request request) {
        StatisticsCountAJAXRequest statisticsCountAJAXRequest = request.body(StatisticsCountAJAXRequest.class);
        AdminUser adminUser = request.require(AdminUser.class);

        FindView<DealerResponse> dealerResponses = findDealers(adminUser, statisticsCountAJAXRequest.name, statisticsCountAJAXRequest.level);
        Map<String, DealerResponse> dealerMap = dealerResponses.items.stream().collect(Collectors.toMap(result -> result.id, result -> result));

        AdminStatisticsCountRequest adminStatisticsCountRequest = new AdminStatisticsCountRequest();
        if (adminUser.hasRole("VendorAdmin")) {
            adminStatisticsCountRequest.vendorId = adminUser.roles.stream().filter(role -> role.contains("VendorAdmin")).collect(Collectors.toList()).get(0).replace("VendorAdmin", "");
        }
        adminStatisticsCountRequest.dealerIds = dealerResponses.items.stream().map(dealerResponse -> dealerResponse.id).collect(Collectors.toList());
        adminStatisticsCountRequest.startTime = statisticsCountAJAXRequest.startTime;
        adminStatisticsCountRequest.endTime = statisticsCountAJAXRequest.endTime;
        return FindView.map(orderStatisticsWebService.adminStatistics(adminStatisticsCountRequest), adminStatisticsCountResponse -> response(adminStatisticsCountResponse, dealerMap));
    }

    @Path("/admin/ajax/dealer/settlement")
    @PUT
    public Response settlement(Request request) {
        SettlementAJAXRequest settlementAJAXRequest = request.body(SettlementAJAXRequest.class);
        AdminUser adminUser = request.require(AdminUser.class);

        SearchSettlementRequest searchSettlementRequest = new SearchSettlementRequest();
        FindView<DealerResponse> dealerResponses = findDealers(adminUser, settlementAJAXRequest.name, settlementAJAXRequest.level);
        searchSettlementRequest.dealerIds = dealerResponses.items.stream().map(dealerResponse -> dealerResponse.id).collect(Collectors.toList());
        if (adminUser.hasRole("ProvinceAdmin")) {
            searchSettlementRequest.states = states(adminUser);
        }
        if (adminUser.hasRole("VendorAdmin")) {
            searchSettlementRequest.vendorId = adminUser.roles.stream().filter(role -> role.contains("VendorAdmin")).collect(Collectors.toList()).get(0).replace("VendorAdmin", "");
        }
        searchSettlementRequest.productName = settlementAJAXRequest.productName;
        searchSettlementRequest.page = settlementAJAXRequest.page;
        searchSettlementRequest.limit = settlementAJAXRequest.limit;
        return Response.body(orderStatisticsWebService.searchSettlement(searchSettlementRequest));
    }

    @Path("/admin/ajax/dealer/find")
    @PUT
    public FindView<DealerAJAXResponse> find(Request request) {
        DealerAJAXRequest dealerAJAXRequest = request.body(DealerAJAXRequest.class);
        AdminUser adminUser = request.require(AdminUser.class);
        SearchDealerRequest searchDealerRequest = new SearchDealerRequest();
        searchDealerRequest.name = dealerAJAXRequest.name;
        if (adminUser.hasRole("ProvinceAdmin")) {
            searchDealerRequest.states = states(adminUser);
        }
        searchDealerRequest.level = dealerAJAXRequest.level;
        searchDealerRequest.page = dealerAJAXRequest.page;
        searchDealerRequest.limit = dealerAJAXRequest.limit;

        return FindView.map(dealerWebService.adminChildren(searchDealerRequest), this::dealerAJAXResponse);
    }

    @Path("/admin/ajax/dealer/:id")
    @GET
    public DealerUpdateView get(Request request) {
        String id = request.pathParam("id");
        DealerUpdateView dealerUpdateView = new DealerUpdateView();
        dealerUpdateView.dealer = dealerWebService.get(id).get();
        String userId = userId(id);
        dealerUpdateView.user = userWebService.get(userId);
        dealerUpdateView.customer = customerWebService.get(userId);
        AdminUser adminUser = request.require(AdminUser.class);
        if (adminUser.hasRole("ProvinceAdmin")) {
            String state = adminUser.roles.stream().filter(role -> role.contains("ProvinceAdmin")).collect(Collectors.toList()).get(0).replace("ProvinceAdmin", "");
            Optional<InsuranceProvinceResponse> provinceResponseOptional = insuranceCountryWebService.provinceByShortName(state);
            provinceResponseOptional.ifPresent(insuranceProvinceResponse -> dealerUpdateView.states = Lists.newArrayList(insuranceProvinceResponse));
        } else {
            dealerUpdateView.states = insuranceCountryWebService.provinceByCountryCode("CHN");
        }
        dealerUpdateView.credit = creditWebService.get(id).orElse(null);
        return dealerUpdateView;
    }

    @Path("/admin/ajax/dealer")
    @PUT
    public DealerUpdateView update(Request request) {
        DealerUpdateView dealerUpdateView = request.body(DealerUpdateView.class);
        String dealerId = dealerUpdateView.dealer.id;
        String userId = userId(dealerId);

        userWebService.update(userId, updateUserRequest(dealerUpdateView.user));
        customerWebService.update(userId, updateCustomerRequest(dealerUpdateView.customer));
        dealerWebService.update(dealerId, updateDealerRequest(dealerUpdateView.dealer));
        creditWebService.update(dealerId, updateRequest(request.require(AdminUser.class), dealerUpdateView.credit.quota));
        return dealerUpdateView;
    }

    @Path("/admin/ajax/dealer")
    @POST
    public DealerUpdateView create(Request request) {
        DealerUpdateView dealerUpdateView = request.body(DealerUpdateView.class);

        DealerResponse dealer = dealerUpdateView.dealer;

        dealerUpdateView.user = userWebService.create(createUserRequest(dealerUpdateView.user.username, dealerUpdateView.password, dealer));
        String userId = dealerUpdateView.user.id;
        dealerUpdateView.customer = customerWebService.create(createCustomerRequest(userId, dealer));
        dealerUpdateView.dealer = dealerWebService.create(createDealerRequest(dealer));
        String dealerId = dealerUpdateView.dealer.id;

        CreateDealerUserRequest createDealerUserRequest = new CreateDealerUserRequest();
        createDealerUserRequest.dealerId = dealerId;
        createDealerUserRequest.userId = userId;
        createDealerUserRequest.requestBy = "kdlins-back-office";
        createDealerUserRequest.roles = Lists.newArrayList("dealerAdmin");
        createDealerUserRequest.status = DealerUserStatusView.AUDITING;
        dealerUserWebService.create(createDealerUserRequest);

        dealerUpdateView.credit = creditWebService.init(dealerId, updateRequest(request.require(AdminUser.class), dealerUpdateView.credit.quota));
        return dealerUpdateView;
    }

    @Path("/admin/ajax/dealer/:id/reset")
    @POST
    public void reset(Request request) {
        String id = request.pathParam("id");
        ResetRequest resetRequest = new ResetRequest();
        resetRequest.operator = "admin";
        resetRequest.requestBy = "kdlins-back-office";
        creditWebService.reset(id, resetRequest);
    }

    @Path("/admin/ajax/dealer/:id/block")
    @GET
    public void block(Request request) {
        String id = request.pathParam("id");
        dealerWebService.lock(id);
    }

    @Path("/admin/ajax/dealer/:id/unblock")
    @GET
    public void unblock(Request request) {
        String id = request.pathParam("id");
        dealerWebService.unlock(id);
        updateStatus(userId(id), UserStatusView.ACTIVE);
    }

    @Path("/admin/ajax/dealer/:id/children/check")
    @GET
    public Boolean checkChildren(Request request) {
        SearchDealerRequest searchDealerRequest = new SearchDealerRequest();
        String dealerId = request.pathParam("id");
        searchDealerRequest.parentId = dealerId;
        if (dealerWebService.children(searchDealerRequest).total > 0) {
            return Boolean.TRUE;
        }
        DealerUserQuery dealerUserQuery = new DealerUserQuery();
        dealerUserQuery.dealerId = dealerId;
        dealerUserQuery.roles = Lists.newArrayList("dealerUser");
        return dealerUserWebService.find(dealerUserQuery).total > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Path("/admin/ajax/dealer/:id")
    @DELETE
    public void delete(Request request) {
        dealerDelete(request.pathParam("id"));
    }

    @Path("/admin/ajax/dealer")
    @DELETE
    public void batchDelete(Request request) {
        List<String> ids = request.body(ArrayList.class);
        ids.forEach(this::dealerDelete);
    }

    private FindView<DealerResponse> findDealers(AdminUser adminUser, String name, DealerLevelView level) {
        DealerQuery dealerQuery = new DealerQuery();
        if (adminUser.hasRole("ProvinceAdmin")) {
            dealerQuery.states = states(adminUser);
        }
        dealerQuery.name = name;
        dealerQuery.level = level;
        return dealerWebService.find(dealerQuery);
    }

    private List<String> states(AdminUser adminUser) {
        List<String> states = adminUser.roles.stream().filter(role -> role.contains("ProvinceAdmin")).map(role -> role.replace("ProvinceAdmin", "")).collect(Collectors.toList());
        FindProvincesRequest request = new FindProvincesRequest();
        request.shortNames = states;
        return insuranceCountryWebService.provinces(request).items.stream().map(insuranceProvinceResponse -> insuranceProvinceResponse.name).collect(Collectors.toList());
    }

    private void updateStatus(String id, UserStatusView status) {
        UpdateUserStatusRequest updateUserStatusRequest = new UpdateUserStatusRequest();
        updateUserStatusRequest.status = status;
        userWebService.updateStatus(id, updateUserStatusRequest);
    }

    private StatisticsCountAJAXResponse response(AdminStatisticsCountResponse adminStatisticsCountResponse, Map<String, DealerResponse> dealerMap) {
        StatisticsCountAJAXResponse statisticsCountAJAXResponse = new StatisticsCountAJAXResponse();
        statisticsCountAJAXResponse.dealerName = dealerMap.get(adminStatisticsCountResponse.dealerId).name;
        statisticsCountAJAXResponse.insuredFee = adminStatisticsCountResponse.insuredFee;
        statisticsCountAJAXResponse.insuredNum = adminStatisticsCountResponse.insuredNum;
        statisticsCountAJAXResponse.insuredInsuredNum = adminStatisticsCountResponse.insuredInsuredNum;
        statisticsCountAJAXResponse.surrenderFee = adminStatisticsCountResponse.surrenderFee;
        statisticsCountAJAXResponse.surrenderNum = adminStatisticsCountResponse.surrenderNum;
        statisticsCountAJAXResponse.surrenderInsuredNum = adminStatisticsCountResponse.surrenderInsuredNum;
        statisticsCountAJAXResponse.rejectFee = adminStatisticsCountResponse.rejectFee;
        statisticsCountAJAXResponse.rejectNum = adminStatisticsCountResponse.rejectNum;
        statisticsCountAJAXResponse.rejectInsuredNum = adminStatisticsCountResponse.rejectInsuredNum;
        return statisticsCountAJAXResponse;
    }

    private DealerAJAXResponse dealerAJAXResponse(DealerResponse dealerResponse) {
        DealerAJAXResponse response = new DealerAJAXResponse();
        response.id = dealerResponse.id;
        response.name = dealerResponse.name;
        response.email = dealerResponse.email;
        response.contactName = dealerResponse.contactName;
        response.contactIdNumber = dealerResponse.contactIdNumber;
        response.cellphone = dealerResponse.cellphone;
        response.state = dealerResponse.state;
        response.city = dealerResponse.city;
        response.parentDealerId = dealerResponse.parentDealerId;
        response.createdTime = dealerResponse.createdTime;
        response.level = dealerResponse.level;
        response.status = dealerResponse.status;
        response.businessLicence = dealerResponse.businessLicence;
        return response;
    }

    private void dealerDelete(String id) {
        String userId = userId(id);
        dealerDelete(id, userId);
    }

    private void dealerDelete(String id, String userId) {
        dealerWebService.delete(id);
        dealerUserWebService.delete(userId);
        userWebService.delete(userId);
        customerWebService.delete(userId);

        DealerUserQuery query = new DealerUserQuery();
        query.dealerId = id;
        dealerUserWebService.find(query).items.forEach(dealerUserResponse -> {
            dealerUserWebService.delete(dealerUserResponse.userId);
            userWebService.delete(dealerUserResponse.userId);
            customerWebService.delete(dealerUserResponse.userId);
        });
    }

    private UpdateRequest updateRequest(AdminUser user, Double quota) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.quota = quota;
        updateRequest.operator = user.username;
        updateRequest.requestBy = "kdlins-back-office";
        return updateRequest;
    }

    private CreateCustomerRequest createCustomerRequest(String userId, DealerResponse dealer) {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.id = userId;
        createCustomerRequest.identification = new IdentificationView();
        createCustomerRequest.identification.type = "Id";
        createCustomerRequest.identification.number = dealer.contactIdNumber;
        createCustomerRequest.gender = GenderView.UNDEFINED;
        createCustomerRequest.state = dealer.state;
        createCustomerRequest.city = dealer.city;
        createCustomerRequest.ward = dealer.ward;
        createCustomerRequest.requestBy = "kdlins-back-office";
        return createCustomerRequest;
    }

    private CreateUserRequest createUserRequest(String username, String password, DealerResponse dealer) {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.username = username;
        createUserRequest.password = password;
        createUserRequest.email = dealer.email;
        createUserRequest.phone = dealer.cellphone;
        createUserRequest.roles = Lists.newArrayList("dealerAdmin");
        createUserRequest.status = UserStatusView.AUDITING;
        createUserRequest.requestBy = "kdlins-back-office";
        return createUserRequest;
    }

    private CreateDealerRequest createDealerRequest(DealerResponse dealer) {
        CreateDealerRequest createDealerRequest = new CreateDealerRequest();
        createDealerRequest.name = dealer.name;
        createDealerRequest.email = dealer.email;
        createDealerRequest.businessLicence = dealer.businessLicence;
        createDealerRequest.contactName = dealer.contactName;
        createDealerRequest.contactIdNumber = dealer.contactIdNumber;
        createDealerRequest.cellphone = dealer.cellphone;
        createDealerRequest.state = dealer.state;
        createDealerRequest.city = dealer.city;
        createDealerRequest.ward = dealer.ward;
        createDealerRequest.status = DealerStatusView.AUDITING;
        createDealerRequest.level = LEVEL1;
        createDealerRequest.requestBy = "kdlins-back-office";
        return createDealerRequest;
    }

    private UpdateDealerRequest updateDealerRequest(DealerResponse dealer) {
        UpdateDealerRequest updateDealerRequest = new UpdateDealerRequest();
        updateDealerRequest.name = dealer.name;
        updateDealerRequest.email = dealer.email;
        updateDealerRequest.contactName = dealer.contactName;
        updateDealerRequest.contactIdNumber = dealer.contactIdNumber;
        updateDealerRequest.cellphone = dealer.cellphone;
        updateDealerRequest.state = dealer.state;
        updateDealerRequest.city = dealer.city;
        updateDealerRequest.ward = dealer.ward;
        updateDealerRequest.level = DealerLevelView.valueOf(dealer.level.name());
        updateDealerRequest.businessLicence = dealer.businessLicence;
        return updateDealerRequest;
    }

    private UpdateCustomerRequest updateCustomerRequest(CustomerResponse customer) {
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.identification = customer.identification;
        updateCustomerRequest.countryCode = customer.countryCode;
        updateCustomerRequest.currencyCode = customer.currencyCode;
        updateCustomerRequest.state = customer.state;
        updateCustomerRequest.city = customer.city;
        updateCustomerRequest.ward = customer.ward;
        updateCustomerRequest.birthday = customer.birthday;
        updateCustomerRequest.gender = null == customer.gender ? GenderView.UNDEFINED : GenderView.valueOf(customer.gender.name());
        return updateCustomerRequest;
    }

    private UpdateUserRequest updateUserRequest(UserResponse user) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.roles = user.roles;
        updateUserRequest.email = user.email;
        updateUserRequest.phone = user.phone;
        updateUserRequest.fullName = user.fullName;
        updateUserRequest.imageURL = user.imageURL;
        updateUserRequest.fields = user.fields;
        updateUserRequest.status = UserStatusView.valueOf(user.status.name());
        return updateUserRequest;
    }

    private String userId(String dealerId) {
        DealerUserQuery query = new DealerUserQuery();
        query.dealerId = dealerId;
        query.roles = Lists.newArrayList("dealerAdmin");
        query.page = 1;
        query.limit = 10;
        FindView<DealerUserResponse> dealerUserResponses = dealerUserWebService.find(query);
        assert dealerUserResponses.total == 1;
        return dealerUserResponses.items.get(0).userId;
    }
}
