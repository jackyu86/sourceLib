package com.caej.insurance.api.clause;

import com.caej.insurance.api.insurance.InsuranceClauseType;

/**
 * @author miller
 */
public class CreateInsuranceClauseRequest {
    public String name;
    public InsuranceClauseType type;
    public String serialNumber;
    public String contentURL;
    public String exclusionsUrl;
    public String wordURL;
    public String pdfURL;
    public String requestBy;
}
