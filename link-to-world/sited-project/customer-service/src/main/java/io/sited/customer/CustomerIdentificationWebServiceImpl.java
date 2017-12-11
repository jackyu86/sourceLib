package io.sited.customer;

import io.sited.customer.api.CustomerIdentificationWebService;
import io.sited.customer.api.identification.CustomerIdentificationResponse;
import io.sited.customer.domain.CustomerIdentification;
import io.sited.customer.service.CustomerIdentificationService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class CustomerIdentificationWebServiceImpl implements CustomerIdentificationWebService {
    @Inject
    CustomerIdentificationService customerIdentificationService;

    @Override
    public List<CustomerIdentificationResponse> findByCustomerId(String customerId) {
        List<CustomerIdentification> identifications = customerIdentificationService.findByCustomerId(customerId);
        return identifications.stream().map(this::response).collect(Collectors.toList());
    }

    private CustomerIdentificationResponse response(CustomerIdentification customerIdentification) {
        CustomerIdentificationResponse response = new CustomerIdentificationResponse();
        response.id = customerIdentification.id.toHexString();
        response.customerId = customerIdentification.customerId;
        response.type = customerIdentification.type;
        response.number = customerIdentification.number;
        response.createdTime = customerIdentification.createdTime;
        response.createdBy = customerIdentification.createdBy;
        return response;
    }
}
