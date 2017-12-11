package io.sited.customer.web;

import io.sited.customer.CustomerModuleImpl;
import io.sited.customer.api.CustomerModule;
import io.sited.customer.api.customer.CreateCustomerRequest;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.GenderView;
import io.sited.customer.api.customer.IdentificationView;
import io.sited.customer.api.customer.UpdateCustomerRequest;
import io.sited.customer.domain.Customer;
import io.sited.customer.service.CustomerService;
import io.sited.http.ServerResponse;
import io.sited.test.SiteRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author miller
 */
public class CustomerWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new CustomerModuleImpl());
    private final Customer customer = customer();

    @Before
    public void setup() throws Exception {
        CustomerService customerService = site.module(CustomerModule.class).require(CustomerService.class);
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.id = customer.id;
        request.identification = new IdentificationView();
        request.identification.number = customer.identification;
        request.identification.type = customer.identificationType;
        customerService.create(request);
    }

    @Test
    public void create() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.id = UUID.randomUUID().toString();
        request.identification = new IdentificationView();
        request.identification.number = "350627111122223333";
        request.identification.type = "ID";
        request.state = "福建";
        request.city = "厦门";
        request.ward = "思明区";
        request.birthday = LocalDate.now();
        request.gender = GenderView.MALE;
        request.requestBy = "auto";
        ServerResponse response = site.post("/api/customer").body(request).execute();

        Assert.assertEquals(200, response.statusCode());
        CustomerResponse customer = response.body(CustomerResponse.class);
        Assert.assertNotNull(customer);
    }

    @Test
    public void findByIdentifier() throws Exception {
        /*IdentificationView identification = new IdentificationView();
        identification.type = "ID";
        identification.number = "350627111122223333";

        ServerResponse response = site.put("/api/customer/identification").body(identification).execute();
        Optional body = response.body(Optional.class);
        Assert.assertTrue(body.isPresent());*/
    }

    @Test
    public void update() throws Exception {
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.gender = GenderView.MALE;
        ServerResponse response = site.put("/api/customer/" + customer.id).body(request).execute();

        Assert.assertEquals(200, response.statusCode());
        CustomerResponse customer = response.body(CustomerResponse.class);
        Assert.assertEquals(GenderView.MALE, customer.gender);
    }

    @Test
    public void get() throws Exception {
        ServerResponse response = site.get("/api/customer/" + customer.id).execute();
        Assert.assertEquals(200, response.statusCode());
        CustomerResponse customer = response.body(CustomerResponse.class);
        Assert.assertNotNull(customer);
        Assert.assertEquals(this.customer.id, customer.id);
    }

    private Customer customer() {
        Customer customer = new Customer();
        customer.id = UUID.randomUUID().toString();
        customer.identification = "350627111122223333";
        customer.identificationType = "ID";
        return customer;
    }
}