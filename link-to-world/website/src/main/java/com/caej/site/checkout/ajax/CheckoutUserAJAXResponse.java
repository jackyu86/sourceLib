package com.caej.site.checkout.ajax;

import io.sited.customer.api.customer.IdentificationView;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
public class CheckoutUserAJAXResponse {
    public String name;
    public IdentificationView id;
    public LocalDate birthDate;
    public String gender;
    public String phone;
    public String email;
    public Map<String, String> address;
}
