package com.caej.insurance.api.insurance;

import org.bson.types.ObjectId;

/**
 * @author miller
 */
public class InsuranceLiabilityRequest {
    public ObjectId groupId;
    public String name;
    public String description;
    public Integer priority;
    public InsuranceLiabilityType type;
    public InsuranceLiveBenefitView liveBenefit;
    public InsuranceRiskProtectionView riskProtection;
    public String requestBy;
}
