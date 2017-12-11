package com.caej.admin.customer.admin;

import java.io.IOException;

import javax.inject.Inject;

import com.caej.order.OrderWebService;
import com.caej.order.order.OrderSumRequest;

import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CustomerQuery;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.db.FindView;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.UserQuery;
import io.sited.user.api.user.UserResponse;

/**
 * @author chi
 */
public class CustomerAdminController {
    @Inject
    UserWebService userWebService;
    @Inject
    CustomerWebService customerWebService;
    @Inject
    OrderWebService orderWebService;

    @Path("/admin/ajax/customer/query")
    @PUT
    public Response put(Request request) throws IOException {
        CustomerDetailQuery query = request.body(CustomerDetailQuery.class);

        if (query.identification != null) {
            CustomerQuery customerQuery = new CustomerQuery();
            customerQuery.page = query.page;
            customerQuery.limit = query.limit;
            customerQuery.identification = query.identification;
            FindView<CustomerResponse> customers = customerWebService.find(customerQuery);
            return Response.body(FindView.map(customers, this::response));
        } else {
            UserQuery userQuery = new UserQuery();
            userQuery.page = query.page;
            userQuery.limit = query.limit;
            userQuery.username = query.username;
            userQuery.fullName = query.fullName;
            userQuery.email = query.email;
            userQuery.phone = query.phone;
            FindView<UserResponse> users = userWebService.find(userQuery);
            return Response.body(FindView.map(users, this::response));
        }
    }

    private CustomerDetailResponse response(CustomerResponse customerResponse) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        response.customer = customerResponse;
        response.user = userWebService.get(customerResponse.id);
        OrderSumRequest orderSumRequest = new OrderSumRequest();
        orderSumRequest.customerId = customerResponse.id;
        response.totalOrder = orderWebService.sum(orderSumRequest).total;
        return response;
    }

    private CustomerDetailResponse response(UserResponse userResponse) {
        CustomerDetailResponse response = new CustomerDetailResponse();
        response.user = userResponse;
        OrderSumRequest orderSumRequest = new OrderSumRequest();
        orderSumRequest.customerId = userResponse.id;
        response.totalOrder = orderWebService.sum(orderSumRequest).total;
        try {
            response.customer = customerWebService.get(userResponse.id);
        } catch (Exception e) {
        }
        return response;
    }
}
