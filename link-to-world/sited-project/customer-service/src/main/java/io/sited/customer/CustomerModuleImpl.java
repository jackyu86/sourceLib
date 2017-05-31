package io.sited.customer;

import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.customer.api.AddressWebService;
import io.sited.customer.api.CountryWebService;
import io.sited.customer.api.CustomerIdentificationWebService;
import io.sited.customer.api.CustomerModule;
import io.sited.customer.api.CustomerWebService;
import io.sited.customer.domain.Address;
import io.sited.customer.domain.Country;
import io.sited.customer.domain.Customer;
import io.sited.customer.domain.CustomerIdentification;
import io.sited.customer.service.AddressService;
import io.sited.customer.service.CountryService;
import io.sited.customer.service.CustomerIdentificationService;
import io.sited.customer.service.CustomerService;
import io.sited.customer.service.mongo.MongoAddressServiceImpl;
import io.sited.customer.service.mongo.MongoCountryServiceImpl;
import io.sited.customer.service.mongo.MongoCustomerIdentificationServiceImpl;
import io.sited.customer.service.mongo.MongoCustomerServiceImpl;
import io.sited.customer.web.AddressWebServiceImpl;
import io.sited.customer.web.CountryWebServiceImpl;
import io.sited.customer.web.CustomerWebServiceImpl;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;

/**
 * @author miller
 */
@ModuleInfo(name = "customer.api", require = {APIModule.class, DBModule.class})
public class CustomerModuleImpl extends CustomerModule {
    @Override
    protected void configure() throws Exception {
        require(MongoConfig.class)
            .entity(CustomerIdentification.class)
            .entity(Country.class)
            .entity(Customer.class)
            .entity(Address.class);

        bind(CustomerIdentificationService.class, bind(MongoCustomerIdentificationServiceImpl.class));
        bind(CountryService.class, bind(MongoCountryServiceImpl.class));
        bind(CustomerService.class, bind(MongoCustomerServiceImpl.class));
        bind(AddressService.class, bind(MongoAddressServiceImpl.class));

        require(APIConfig.class)
            .service(CustomerWebService.class, CustomerWebServiceImpl.class)
            .service(CustomerIdentificationWebService.class, CustomerIdentificationWebServiceImpl.class)
            .service(AddressWebService.class, AddressWebServiceImpl.class)
            .service(CountryWebService.class, CountryWebServiceImpl.class);
    }
}