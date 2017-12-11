package com.caej.admin.dealer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.inject.Inject;

import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.ProductResponse;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;

import app.dealer.api.CreditWebService;
import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.credit.UpdateRequest;
import app.dealer.api.dealer.CreateDealerRequest;
import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerQuery;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerStatusView;
import app.dealer.api.dealer.DealerUserStatusView;
import app.dealer.api.product.DealerProductView;
import app.dealer.api.product.UpdateDealerProductRequest;
import io.sited.StandardException;
import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.GenderView;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.db.FindView;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UserResponse;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class InitDealerService {
    @Inject
    UserWebService userWebService;
    @Inject
    CustomerWebService customerWebService;
    @Inject
    DealerWebService dealerWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    CreditWebService creditWebService;
    @Inject
    ProductWebService productWebService;

    public void init(String path) {
        FindView<DealerResponse> responses = dealerWebService.find(new DealerQuery());
        if (responses.items.isEmpty()) {
            InitDealerRequest initDealerRequest = JSON.fromJSON(resource(path), InitDealerRequest.class);
            DealerRequest parent = initDealerRequest.parent;
            UserResponse parentUser = createUser(parent);
            createCustomer(parent, parentUser);
            DealerResponse parentDealer = createDealer(parent, null);
            createDealerUser(parentDealer, parentUser);
            updateDealerProduct(parentDealer, parent);
            createCredit(parentDealer, parent);
            DealerRequest child = initDealerRequest.child;
            UserResponse childUser = createUser(child);
            createCustomer(child, childUser);
            DealerResponse childDealer = createDealer(child, parentDealer);
            createDealerUser(childDealer, childUser);
            updateDealerProduct(childDealer, child);
            createCredit(childDealer, child);
        }
    }

    private String resource(String path) {
        try (Reader reader = new InputStreamReader(Resources.getResource(path).openStream(), Charsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    private UserResponse createUser(DealerRequest dealerRequest) {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.username = dealerRequest.username;
        createUserRequest.password = dealerRequest.password;
        createUserRequest.email = dealerRequest.email;
        createUserRequest.phone = dealerRequest.cellphone;
        createUserRequest.requestBy = "kdlins-back-office";
        createUserRequest.roles = Lists.newArrayList();
        createUserRequest.roles.add("dealerAdmin");
        return userWebService.create(createUserRequest);
    }

    private void createCustomer(DealerRequest dealerRequest, UserResponse userResponse) {
        CreateCustomerRequest customerRequest = new CreateCustomerRequest();
        customerRequest.id = userResponse.id;
        customerRequest.identification = new IdentificationView();
        customerRequest.identification.type = "Id";
        customerRequest.identification.number = dealerRequest.contactIdNumber;
        customerRequest.gender = GenderView.UNDEFINED;
        customerRequest.state = dealerRequest.state;
        customerRequest.city = dealerRequest.city;
        customerRequest.ward = dealerRequest.ward;
        customerRequest.requestBy = "kdlins-back-office";
        customerWebService.create(customerRequest);
    }

    private DealerResponse createDealer(DealerRequest dealerRequest, DealerResponse parent) {
        CreateDealerRequest createDealerRequest = new CreateDealerRequest();
        createDealerRequest.name = dealerRequest.name;
        createDealerRequest.email = dealerRequest.email;
        createDealerRequest.contactName = dealerRequest.contactName;
        createDealerRequest.contactIdNumber = dealerRequest.contactIdNumber;
        createDealerRequest.cellphone = dealerRequest.cellphone;
        createDealerRequest.state = dealerRequest.state;
        createDealerRequest.city = dealerRequest.city;
        createDealerRequest.ward = dealerRequest.ward;
        createDealerRequest.status = DealerStatusView.ACTIVE;
        createDealerRequest.level = dealerRequest.level;
        if (parent != null) createDealerRequest.parentDealerId = parent.id;
        return dealerWebService.create(createDealerRequest);
    }

    private void createDealerUser(DealerResponse dealerResponse, UserResponse userResponse) {
        CreateDealerUserRequest createDealerUserRequest = new CreateDealerUserRequest();
        createDealerUserRequest.dealerId = dealerResponse.id;
        createDealerUserRequest.userId = userResponse.id;
        createDealerUserRequest.requestBy = "kdlins-back-office";
        createDealerUserRequest.roles = Lists.newArrayList("dealerAdmin");
        createDealerUserRequest.status = DealerUserStatusView.ACTIVE;
        dealerUserWebService.create(createDealerUserRequest);
    }

    private void updateDealerProduct(DealerResponse dealerResponse, DealerRequest dealerRequest) {
        UpdateDealerProductRequest updateDealerProductRequest = new UpdateDealerProductRequest();
        List<DealerProductView> products = Lists.newArrayList();
        dealerRequest.products.forEach(productName -> {
            ProductResponse productResponse = productWebService.getByName(productName);
            DealerProductView dealerProductView = new DealerProductView();
            dealerProductView.productName = productName;
            dealerProductView.insuranceCategoryIds = productResponse.insuranceCategoryIds;
            products.add(dealerProductView);
        });
        updateDealerProductRequest.products = products;
        updateDealerProductRequest.dealerId = dealerResponse.id;
        dealerProductWebService.update(dealerResponse.id, updateDealerProductRequest);
    }

    private void createCredit(DealerResponse dealerResponse, DealerRequest dealerRequest) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.quota = dealerRequest.credit.quota;
        updateRequest.operator = "admin";
        updateRequest.requestBy = "kdlins-back-office";
        creditWebService.init(dealerResponse.id, updateRequest);
    }
}
