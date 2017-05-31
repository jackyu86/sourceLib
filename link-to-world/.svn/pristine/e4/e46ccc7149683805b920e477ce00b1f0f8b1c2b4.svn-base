package io.sited.customer.service;

import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.api.address.UpdateAddressRequest;
import io.sited.customer.domain.Address;

import java.util.List;

/**
 * @author chi
 */
public interface AddressService {
    Address get(String addressId);

    List<Address> findByCustomerId(String customerId);

    Address create(String customerId, CreateAddressRequest request);

    Address update(String addressId, UpdateAddressRequest request);

    boolean delete(String addressId);
}
