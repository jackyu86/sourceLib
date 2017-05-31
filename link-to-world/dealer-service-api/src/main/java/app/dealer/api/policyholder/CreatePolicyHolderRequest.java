package app.dealer.api.policyholder;

import java.time.LocalDate;

/**
 * @author miller
 */
public class CreatePolicyHolderRequest {
    public String dealerId;
    public String name;
    public String gender;
    public LocalDate birthDate;
    public String idNumber;
    public String phone;
    public String email;
    public String createdBy;

}
