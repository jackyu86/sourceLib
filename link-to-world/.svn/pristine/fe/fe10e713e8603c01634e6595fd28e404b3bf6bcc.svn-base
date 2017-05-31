package com.caej.site.dealer.web.ajax;

import com.caej.site.validator.Phone;

import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerUserStatusView;
import io.sited.user.web.validator.Password;
import io.sited.user.web.validator.Username;
import io.sited.validator.constraints.NotEmpty;

/**
 * @author chi
 */
public class RegisterDealerRequest {
    @Username
    public String username;
    public String email;
    public String phone;
    public String pinCode;
    @Password
    public String password;
    @NotEmpty
    public String name;
    public String contactName;
    @NotEmpty
    public String contactIdNumber;
    @Phone
    public String cellphone;
    @NotEmpty
    public String state;
    @NotEmpty
    public String city;
    public String ward;
    public DealerLevelView level;
    public String parentDealerId;
    public String businessLicence;
    public DealerUserStatusView status;
}
