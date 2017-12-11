package com.caej.insurance.api.insurance;

import java.time.LocalDateTime;

/**
 * 生存领取
 *
 * @author chi
 */
public class InsuranceLiveBenefitView {
    public InsuranceReceiveType receiveType;
    public InsuranceReceiveFrequencyType receiveFrequencyType;
    public Integer receiveTimes;
    public InsuranceReceiveTimeType receiveTimeType;
    //是否变额
    public Boolean proportionEnabled;
    //变额比例
    public Double proportion;
    public LocalDateTime startTime;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;
}
