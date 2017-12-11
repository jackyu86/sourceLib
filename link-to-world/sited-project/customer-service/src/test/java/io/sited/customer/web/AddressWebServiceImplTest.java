package io.sited.customer.web;

import io.sited.customer.CustomerModuleImpl;
import io.sited.customer.api.CustomerModule;
import io.sited.customer.api.address.AddressResponse;
import io.sited.customer.api.address.CreateAddressRequest;
import io.sited.customer.service.AddressService;
import io.sited.http.ServerResponse;
import io.sited.test.SiteRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * @author chi
 */
public class AddressWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new CustomerModuleImpl());

    @Before
    public void setup() {
        AddressService addressService = site.module(CustomerModule.class).require(AddressService.class);

        CreateAddressRequest address = new CreateAddressRequest();
        address.countryCode = "countryCode";
        address.city = "city";
        address.state = "state";
        address.ward = "ward";
        address.address1 = "address1";
        address.address2 = "address2";
        address.zipCode = "zipCode";
        address.requestBy = "init";

        addressService.create("57b6dd870879224ef8cdafe7", address);
    }

    @Test
    public void find() throws Exception {
        List<AddressResponse> addresses = site.get("/api/customer/57b6dd870879224ef8cdafe7/address").execute().body(List.class);
        Assert.assertFalse(addresses.isEmpty());
    }

    @Test
    public void create() throws Exception {
        CreateAddressRequest address = new CreateAddressRequest();
        address.countryCode = "countryCode";
        address.city = "city";
        address.state = "state";
        address.ward = "ward";
        address.address1 = "address1";
        address.address2 = "address2";
        address.zipCode = "zipCode";
        address.requestBy = "init";

        ServerResponse response = site.post("/api/customer/57b6dd870879224ef8cdafe7/address").body(address).execute();
        AddressResponse addressResponse = response.body(AddressResponse.class);
        Assert.assertNotNull(addressResponse.id);
    }
}