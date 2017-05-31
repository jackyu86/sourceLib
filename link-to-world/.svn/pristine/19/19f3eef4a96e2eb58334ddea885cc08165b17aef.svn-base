package io.sited.customer.service.mongo;

import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.api.address.UpdateAddressRequest;
import io.sited.customer.domain.Address;
import io.sited.customer.service.AddressService;
import io.sited.db.MongoRepository;
import io.sited.http.exception.NotFoundException;
import org.bson.Document;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class MongoAddressServiceImpl implements AddressService {
    @Inject
    MongoRepository<Address> repository;

    @Override
    public Address get(String addressId) {
        Optional<Address> address = repository.query("_id", addressId).findOne();
        if (!address.isPresent()) {
            throw new NotFoundException("missing address, id={}", addressId);
        }
        return address.get();
    }

    @Override
    public List<Address> findByCustomerId(String customerId) {
        return repository.query(new Document("customer_id", customerId)).findMany();
    }

    @Override
    public Address create(String customerId, CreateAddressRequest request) {
        Address address = new Address();
        address.id = UUID.randomUUID().toString();
        address.customerId = customerId;
        address.fullName = request.fullName;
        address.phone = request.phone;
        address.countryCode = request.countryCode;
        address.state = request.state;
        address.city = request.city;
        address.ward = request.ward;
        address.address1 = request.address1;
        address.address2 = request.address2;
        address.zipCode = request.zipCode;
        address.createdTime = LocalDateTime.now();
        address.createdBy = request.requestBy;
        address.updatedTime = LocalDateTime.now();
        address.updatedBy = request.requestBy;
        repository.insert(address);
        return address;
    }

    @Override
    public Address update(String addressId, UpdateAddressRequest request) {
        Address address = get(addressId);
        address.fullName = request.fullName;
        address.phone = request.phone;
        address.countryCode = request.countryCode;
        address.state = request.state;
        address.city = request.city;
        address.ward = request.ward;
        address.address1 = request.address1;
        address.address2 = request.address2;
        address.zipCode = request.zipCode;
        address.updatedTime = LocalDateTime.now();
        address.updatedBy = request.requestBy;

        repository.update(addressId, address);
        return address;
    }

    @Override
    public boolean delete(String addressId) {
        return repository.delete(addressId);
    }
}
