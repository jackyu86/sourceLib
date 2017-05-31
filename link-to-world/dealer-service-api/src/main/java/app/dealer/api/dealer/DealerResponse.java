package app.dealer.api.dealer;

import java.time.LocalDateTime;

/**
 * @author Jonathan.Guo
 */
public class DealerResponse {
    public String id;
    public String name;
    public String email;
    public String contactName;
    public String contactIdNumber;
    public String cellphone;
    public String state;
    public String city;
    public String ward;
    public String parentDealerId;
    public LocalDateTime createdTime;
    public DealerLevelView level;
    public DealerStatusView status;
    public String businessLicence;
    public Boolean autoAllocate;
}
