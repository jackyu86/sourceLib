package io.sited.customer.web;

import io.sited.customer.api.AddressWebService;
import io.sited.customer.api.address.AddressResponse;
import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.api.address.UpdateAddressRequest;
import io.sited.customer.domain.Address;
import io.sited.customer.service.AddressService;
import io.sited.http.exception.UnauthorizedException;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class AddressWebServiceImpl implements AddressWebService {
    @Inject
    AddressService addressService;

    @Override
    public List<AddressResponse> findByCustomerId(String customerId) {
        return addressService.findByCustomerId(customerId).stream()
            .map(this::response).collect(Collectors.toList());
    }

    @Override
    public AddressResponse get(String customerId, String addressId) {
        Address address = addressService.get(addressId);
        if (!address.customerId.equals(customerId)) {
            throw new UnauthorizedException("failed to get address, customerId={}, addressId={}", customerId, addressId);
        }
        return response(address);
    }

    @Override
    public AddressResponse create(String customerId, CreateAddressRequest request) {
        Address address = addressService.create(customerId, request);
        return response(address);
    }

    @Override
    public AddressResponse update(String customerId, String addressId, UpdateAddressRequest request) {
        Address address = addressService.get(addressId);
        if (!address.customerId.equals(customerId)) {
            throw new UnauthorizedException("failed to update address, customerId={}, addressId={}", customerId, addressId);
        }
        address = addressService.update(addressId, request);
        return response(address);
    }

    @Override
    public void delete(String customerId, String addressId) {
        Address address = addressService.get(addressId);
        if (!address.customerId.equals(customerId)) {
            throw new UnauthorizedException("failed to delete address, customerId={}, addressId={}", customerId, addressId);
        }
        addressService.delete(addressId);
    }

    private AddressResponse response(Address address) {
        AddressResponse response = new AddressResponse();
        response.id = address.id;
        response.customerId = address.customerId;
        response.fullName = address.fullName;
        response.phone = address.phone;
        response.countryCode = address.countryCode;
        response.state = address.state;
        response.city = address.city;
        response.ward = address.ward;
        response.address1 = address.address1;
        response.address2 = address.address2;
        response.zipCode = address.zipCode;
        response.createdTime = address.createdTime;
        response.createdBy = address.createdBy;
        response.updatedTime = address.updatedTime;
        response.updatedBy = address.updatedBy;
        return response;
    }
}
