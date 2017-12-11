package io.sited.customer.web;

import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.CustomerQuery;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.GenderView;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.customer.domain.Customer;
import io.sited.customer.service.CustomerService;
import io.sited.db.FindView;

import javax.inject.Inject;

/**
 * @author miller
 */
public class CustomerWebServiceImpl implements CustomerWebService {
    @Inject
    CustomerService customerService;

    @Override
    public CustomerResponse create(CreateCustomerRequest request) {
        Customer customer = customerService.create(request);
        return response(customer);
    }

    @Override
    public FindView<CustomerResponse> find(CustomerQuery query) {
        FindView<Customer> customers = customerService.find(query);
        return FindView.map(customers, this::response);
    }

    @Override
    public CustomerResponse update(String id, UpdateCustomerRequest request) {
        Customer customer = customerService.update(id, request);
        return response(customer);
    }

    @Override
    public CustomerResponse get(String id) {
        return response(customerService.get(id));
    }

    @Override
    public void delete(String id) {
        customerService.delete(id);
    }

    private CustomerResponse response(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.id = customer.id;
        if (customer.identification != null) {
            response.identification = new IdentificationView();
            response.identification.number = customer.identification;
            response.identification.type = customer.identificationType;
        }
        response.countryCode = customer.countryCode;
        response.currencyCode = customer.currencyCode;
        response.state = customer.state;
        response.city = customer.city;
        response.ward = customer.ward;
        response.birthday = customer.birthday;
        response.gender = null == customer.gender ? GenderView.UNDEFINED : GenderView.valueOf(customer.gender.name());
        response.source = customer.source;
        response.channelId = customer.channelId;
        response.campaignId = customer.campaignId;
        response.createdTime = customer.createdTime;
        response.createdBy = customer.createdBy;
        response.updatedTime = customer.updatedTime;
        response.updatedBy = customer.updatedBy;
        return response;
    }
}
