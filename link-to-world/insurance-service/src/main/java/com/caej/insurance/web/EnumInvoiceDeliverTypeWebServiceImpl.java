package com.caej.insurance.web;


import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.insurance.api.EnumInvoiceDeliverTypeWebService;
import com.caej.insurance.api.enumtype.AllEnumInvoiceDeliverTypeResponse;
import com.caej.insurance.api.enumtype.EnumInvoiceDeliverTypeResponse;
import com.caej.insurance.domain.EnumInvoiceDeliverType;
import com.caej.insurance.service.EnumInvoiceDeliverTypeService;

/**
 * @author miller
 */
public class EnumInvoiceDeliverTypeWebServiceImpl implements EnumInvoiceDeliverTypeWebService {
    @Inject
    EnumInvoiceDeliverTypeService enumInvoiceDeliverTypeService;

    private EnumInvoiceDeliverTypeResponse response(EnumInvoiceDeliverType enumInvoiceDeliverType) {
        EnumInvoiceDeliverTypeResponse response = new EnumInvoiceDeliverTypeResponse();
        response.name = enumInvoiceDeliverType.name;
        response.value = enumInvoiceDeliverType.value;
        return response;
    }

    @Override
    public AllEnumInvoiceDeliverTypeResponse findAll() {
        AllEnumInvoiceDeliverTypeResponse response = new AllEnumInvoiceDeliverTypeResponse();
        response.list = enumInvoiceDeliverTypeService.findAll().stream().map(this::response).collect(Collectors.toList());
        return response;
    }

    @Override
    public Double price(String value) {
        return enumInvoiceDeliverTypeService.price(value);
    }
}
