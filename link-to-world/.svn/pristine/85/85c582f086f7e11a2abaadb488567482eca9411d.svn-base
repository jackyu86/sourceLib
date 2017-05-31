package io.sited.customer.service;

import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.CustomerQuery;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.customer.domain.Customer;
import io.sited.db.FindView;

import java.util.Optional;

/**
 * @author miller
 */
public interface CustomerService {
    Customer get(String id);

    Optional<Customer> findByIdentification(IdentificationView identification);

    Customer create(CreateCustomerRequest request);

    Customer update(String id, UpdateCustomerRequest request);

    boolean delete(String id);

    FindView<Customer> find(CustomerQuery criteria);
}
