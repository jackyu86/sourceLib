package com.caej.site.dealer.web.ajax;

import app.dealer.api.credit.CreditTrackingResponse;

import java.time.LocalDateTime;

/**
 * @author Hubery.Chen
 */
public class CreditTrackingAJAXResponse {
    public Double quota;
    public CreditTrackingResponse.UpdateCreditType type;
    public Double totalCredits;
    public String operator;
    public LocalDateTime createdTime;
}
