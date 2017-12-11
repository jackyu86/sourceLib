package app.dealer.api.credit;

import java.time.LocalDateTime;

/**
 * @author Hubery.Chen
 */
public class CreditTrackingResponse {
    public String id;
    public String creditId;
    public String paymentId;
    public Double quota;
    public UpdateCreditType type;
    public Double totalCredits;
    public String operator;
    public LocalDateTime createdTime;
    public String createdBy;

    public enum UpdateCreditType {
        CHECKOUT, INIT, UPDATE, RESET
    }
}
