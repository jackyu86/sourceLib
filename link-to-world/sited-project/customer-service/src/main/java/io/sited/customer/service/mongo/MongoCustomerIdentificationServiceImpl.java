package io.sited.customer.service.mongo;

import io.sited.customer.domain.CustomerIdentification;
import io.sited.customer.service.CustomerIdentificationService;
import io.sited.db.MongoRepository;
import org.bson.Document;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class MongoCustomerIdentificationServiceImpl implements CustomerIdentificationService {
    @Inject
    MongoRepository<CustomerIdentification> repository;

    @Override
    public List<CustomerIdentification> findByCustomerId(String customerId) {
        return repository.query("customer_id", customerId).findMany();
    }

    @Override
    public void update(String customerId, String type, String number) {
        Optional<CustomerIdentification> customerIdentificationOptional = findByCustomerIdAndType(customerId, type);
        if (customerIdentificationOptional.isPresent()) {
            CustomerIdentification customerIdentification = customerIdentificationOptional.get();
            customerIdentification.number = number;
            repository.update(customerIdentification.id, customerIdentification);
        } else {
            CustomerIdentification customerIdentification = new CustomerIdentification();
            customerIdentification.customerId = customerId;
            customerIdentification.type = type;
            customerIdentification.number = number;
            customerIdentification.createdTime = LocalDateTime.now();
            customerIdentification.createdBy = "api";
            repository.insert(customerIdentification);
        }
    }

    private Optional<CustomerIdentification> findByCustomerIdAndType(String customerId, String type) {
        return repository.query(new Document("customer_id", customerId).append("type", type)).findOne();
    }
}
