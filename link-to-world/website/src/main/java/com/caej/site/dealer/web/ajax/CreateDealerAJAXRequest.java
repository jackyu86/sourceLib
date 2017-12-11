package com.caej.site.dealer.web.ajax;

import io.sited.user.web.validator.Password;
import io.sited.user.web.validator.Username;
import io.sited.validator.constraints.NotEmpty;

/**
 * @author Jonathan.Guo
 */
public class CreateDealerAJAXRequest {
    @Username
    public String username;
    @Password
    public String password;
    public String name;
    @NotEmpty
    public String businessLicence;
    public String state;
    public String city;
    public String ward;
    @NotEmpty
    public String email;
    @NotEmpty
    public String contactName;
    public String phone;
    public String identification;
}
