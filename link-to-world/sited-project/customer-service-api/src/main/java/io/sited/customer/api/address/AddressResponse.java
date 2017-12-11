package io.sited.customer.api.address;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class AddressResponse {
    public String id;
    public String customerId;
    public String fullName;
    public String phone;
    public String countryCode;
    public String state;
    public String city;
    public String ward;
    public String address1;
    public String address2;
    public String zipCode;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
