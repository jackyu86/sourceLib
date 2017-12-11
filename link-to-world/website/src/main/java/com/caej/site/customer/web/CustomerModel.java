package com.caej.site.customer.web;

import io.sited.customer.api.customer.IdentificationView;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Hubery.Chen
 */
public class CustomerModel {
    public String id;
    public IdentificationView identification;
    public LocalDate birthday;
    public GenderModel gender;
    public String state;
    public String city;
    public String ward;
    public String currencyCode;
    public String countryCode;
    public String channelId;
    public String campaignId;
    public String source;
    public LocalDateTime createdTime;
    public String createdBy;
    public LocalDateTime updatedTime;
    public String updatedBy;

    public String identificationDescription() {
        String result = "";
        if ("Id".equals(identification.type)) {
            result += "身份证";
        } else if ("mid".equals(identification.type)) {
            result += "军人证";
        } else if ("passport".equals(identification.type)) {
            result += "护照";
        } else if ("bid".equals(identification.type)) {
            result += "出生证明";
        } else if ("other".equals(identification.type)) {
            result += "其他";
        } else if ("rid".equals(identification.type)) {
            result += "户口簿";
        } else {
            result += "身份证";
        }
        return result + " " + identification.number;
    }

}
