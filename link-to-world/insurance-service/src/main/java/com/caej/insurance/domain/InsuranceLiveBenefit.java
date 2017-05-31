package com.caej.insurance.domain;

import java.time.LocalDateTime;

import com.caej.insurance.api.insurance.InsuranceReceiveFrequencyType;
import com.caej.insurance.api.insurance.InsuranceReceiveTimeType;
import com.caej.insurance.api.insurance.InsuranceReceiveType;

import io.sited.db.Field;

/**
 * 生存领取
 *
 * @author chi
 */
public class InsuranceLiveBenefit {
    @Field(name = "receive_type")
    public InsuranceReceiveType receiveType;
    @Field(name = "receive_frequency_type")
    public InsuranceReceiveFrequencyType receiveFrequencyType;
    @Field(name = "receive_times")
    public Integer receiveTimes;
    @Field(name = "receive_time_type")
    public InsuranceReceiveTimeType receiveTimeType;
    //是否变额
    @Field(name = "proportion_enabled")
    public Boolean proportionEnabled;
    //变额比例
    @Field(name = "proportion")
    public Double proportion;
    @Field(name = "start_time")
    public LocalDateTime startTime;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
