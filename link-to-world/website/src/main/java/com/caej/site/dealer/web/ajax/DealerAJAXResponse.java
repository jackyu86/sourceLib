package com.caej.site.dealer.web.ajax;

import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerUserStatusView;

import java.time.LocalDateTime;

/**
 * @author Jonathan.Guo
 */
public class DealerAJAXResponse {
    public String id;
    public String username;
    public String name;
    public String contactName;
    public String phone;
    public String email;
    public String city;
    public DealerLevelView level;
    public LocalDateTime createDate;
    public DealerUserStatusView status;
    public boolean canEdit;
}
