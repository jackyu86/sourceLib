package io.sited.customer.service.mongo;

import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.CustomerQuery;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.customer.domain.Customer;
import io.sited.customer.domain.Gender;
import io.sited.customer.service.CustomerIdentificationService;
import io.sited.customer.service.CustomerService;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import org.bson.Document;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author miller
 */
public class MongoCustomerServiceImpl implements CustomerService {
    @Inject
    MongoRepository<Customer> repository;
    @Inject
    CustomerIdentificationService customerIdentificationService;

    @Override
    public Customer get(String id) {
        return repository.get(id);
    }

    @Override
    public Optional<Customer> findByIdentification(IdentificationView identification) {
        return repository.query(new Document("identification", identification.number).append("identification_type", identification.type)).findOne();
    }

    @Override
    public Customer create(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.id = request.id;
        if (request.identification != null) {
            customer.identification = request.identification.number;
            customer.identificationType = request.identification.type;
            customerIdentificationService.update(customer.id, request.identification.type, request.identification.number);
        }
        customer.state = request.state;
        customer.city = request.city;
        customer.ward = request.ward;
        customer.birthday = request.birthday;
        customer.gender = request.gender == null ? Gender.UNDEFINED : Gender.valueOf(request.gender.name());
        customer.source = request.source;
        customer.channelId = request.channelId;
        customer.campaignId = request.campaignId;
        customer.countryCode = request.countryCode;
        customer.currencyCode = request.currencyCode;
        customer.updatedBy = request.requestBy;
        customer.createdBy = request.requestBy;
        customer.createdTime = LocalDateTime.now();
        customer.updatedTime = LocalDateTime.now();
        repository.insert(customer);
        return customer;
    }

    @Override
    public Customer update(String id, UpdateCustomerRequest request) {
        Customer customer = get(id);
        if (request.identification != null) {
            customer.identificationType = request.identification.type;
            customer.identification = request.identification.number;
            customerIdentificationService.update(customer.id, request.identification.type, request.identification.number);
        }
        customer.state = request.state;
        customer.city = request.city;
        customer.ward = request.ward;
        customer.birthday = request.birthday;
        if (request.gender != null) {
            customer.gender = Gender.valueOf(request.gender.name());
        }
        customer.countryCode = request.countryCode;
        customer.currencyCode = request.currencyCode;
        customer.updatedBy = request.requestBy;
        customer.updatedTime = LocalDateTime.now();
        repository.update(customer.id, customer);
        return customer;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public FindView<Customer> find(CustomerQuery criteria) {
        Document document = new Document();
        if (criteria.channelId != null) {
            document.put("channel_id", criteria.channelId);
        }
        if (criteria.source != null) {
            document.put("source", criteria.source);
        }
        return repository.query(document).page(criteria.page).limit(criteria.limit).find();
    }
}
