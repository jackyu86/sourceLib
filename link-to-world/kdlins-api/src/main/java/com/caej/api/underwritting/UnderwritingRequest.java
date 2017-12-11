package com.caej.api.underwritting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author miller
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class UnderwritingRequest {
    @XmlElement(name = "main")
    public UnderwritingRequestMain main;
    @XmlElement(name = "application")
    public UnderwritingRequestApplication application;
    @XmlElement(name = "cancel")
    public UnderwritingRequestCancel cancel;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "main")
    public static class UnderwritingRequestMain {
        @XmlElement(name = "transNo")
        public String transNo = "";
        @XmlElement(name = "transType")
        public String transType = "";
        @XmlElement(name = "transDate")
        public String transDate = "";
        @XmlElement(name = "transTime")
        public String transTime = "";
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "application")
    public static class UnderwritingRequestApplication {
        @XmlElement(name = "applyCode")
        public String applyCode = "";
        @XmlElement(name = "applyDate")
        public String applyDate = "";
        @XmlElement(name = "agencyCode")
        public String agencyCode = "";
        @XmlElement(name = "premium")
        public Double premium;
        @XmlElement(name = "deliverType")
        public String deliverType = "";
        @XmlElement(name = "travelDestCode")
        public String travelDestCode = "";
        @XmlElement(name = "travelDestName")
        public String travelDestName = "";
//        @XmlElement(name = "order")
//        public String order = "";
        @XmlElementWrapper(name = "autoMobiles")
        @XmlElement(name = "autoMobile")
        public List<UnderwritingRequestAutoMobile> autoMobiles;
        @XmlElement(name = "flight")
        public UnderwritingRequestFlight flight;
        @XmlElement(name = "policyHolder")
        public UnderwritingRequestPolicyHolder policyHolder;
        @XmlElementWrapper(name = "insureds")
        @XmlElement(name = "insured")
        public List<UnderwritingRequestInsured> insureds;
        @XmlElementWrapper(name = "beneficiarys")
        @XmlElement(name = "beneficiary")
        public List<UnderwritingRequestBeneficiary> beneficiarys;
        @XmlElement(name = "plan")
        public UnderwritingRequestPlan plan;
        @XmlElementWrapper(name = "products")
        @XmlElement(name = "product")
        public List<UnderwritingRequestProduct> products;
        @XmlElement(name = "paymentMethod")
        public UnderwritingRequestPaymentMethod paymentMethod;
        @XmlElement(name = "insInterval")
        public UnderwritingRequestInsInterval insInterval;
        @XmlElement(name = "others")
        public UnderwritingRequestOthers others;
        @XmlElementWrapper(name = "declarations")
        @XmlElement(name = "declaration")
        public List<com.caej.api.underwritting.UnderwritingRequest.UnderwritingRequestDeclaration> declarations;
        @XmlElementWrapper(name = "images")
        @XmlElement(name = "image")
        public List<UnderwritingRequestImage> images;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "autoMobile")
    public static class UnderwritingRequestAutoMobile {
        @XmlElement(name = "licensePlate")
        public String licensePlate = "";
        @XmlElement(name = "standardNumber")
        public Integer standardNumber;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "flight")
    public static class UnderwritingRequestFlight {
        @XmlElement(name = "flightNo")
        public String flightNo = "";
        @XmlElement(name = "flightTime")
        public String flightTime = "";
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "policyHolder")
    public static class UnderwritingRequestPolicyHolder {
        @XmlElement(name = "firstName")
        public String firstName = "";
        @XmlElement(name = "gender")
        public String gender = "";
        @XmlElement(name = "birthday")
        public String birthday = "";
        @XmlElement(name = "certiType")
        public String certiType = "";
        @XmlElement(name = "certiCode")
        public String certiCode = "";
        @XmlElement(name = "certiExpireDate")
        public String certiExpireDate = "";
        @XmlElement(name = "nationality")
        public String nationality;
        @XmlElement(name = "marriageStatus")
        public String marriageStatus;
        @XmlElement(name = "workCompany")
        public String workCompany;
        @XmlElement(name = "title")
        public String title;
        @XmlElement(name = "jobCode")
        public String jobCode;
        @XmlElement(name = "jobName")
        public String jobName;
        @XmlElement(name = "mobile")
        public String mobile;
        @XmlElement(name = "telephone")
        public String telephone;
        @XmlElement(name = "email")
        public String email;
        @XmlElement(name = "state")
        public String state;
        @XmlElement(name = "city")
        public String city;
        @XmlElement(name = "ward")
        public String ward;
        @XmlElement(name = "address")
        public String address;
        @XmlElement(name = "postalCode")
        public String postalCode;
        @XmlElement(name = "income")
        public Long income;
        @XmlElement(name = "incomeSource")
        public String incomeSource;
        @XmlElement(name = "height")
        public Double height;
        @XmlElement(name = "weight")
        public Double weight;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "insured")
    public static class UnderwritingRequestInsured {
        @XmlElement(name = "insuredNum")
        public Integer insuredNum;
        @XmlElement(name = "policyCode")
        public String policyCode;
        @XmlElement(name = "relation2Ph")
        public Integer relation2Ph;
        @XmlElement(name = "firstName")
        public String firstName;
        @XmlElement(name = "gender")
        public String gender;
        @XmlElement(name = "birthday")
        public String birthday;
        @XmlElement(name = "certiType")
        public String certiType;
        @XmlElement(name = "certiCode")
        public String certiCode;
        @XmlElement(name = "certiExpireDate")
        public String certiExpireDate;
        @XmlElement(name = "nationality")
        public String nationality;
        @XmlElement(name = "marriageStatus")
        public String marriageStatus;
        @XmlElement(name = "workCompany")
        public String workCompany;
        @XmlElement(name = "title")
        public String title;
        @XmlElement(name = "jobCode")
        public String jobCode;
        @XmlElement(name = "jobName")
        public String jobName;
        @XmlElement(name = "mobile")
        public String mobile;
        @XmlElement(name = "telephone")
        public String telephone;
        @XmlElement(name = "email")
        public String email;
        @XmlElement(name = "state")
        public String state;
        @XmlElement(name = "city")
        public String city;
        @XmlElement(name = "ward")
        public String ward;
        @XmlElement(name = "address")
        public String address;
        @XmlElement(name = "postalCode")
        public String postalCode;
        @XmlElement(name = "income")
        public Long income;
        @XmlElement(name = "incomeSource")
        public String incomeSource;
        @XmlElement(name = "height")
        public Double height;
        @XmlElement(name = "weight")
        public Double weight;
        @XmlElement(name = "socialSecurity")
        public String socialSecurity;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "beneficiary")
    public static class UnderwritingRequestBeneficiary {
        @XmlElement(name = "beneType")
        public String beneType;
        @XmlElement(name = "isLegal")
        public String isLegal;
        @XmlElement(name = "firstName")
        public String firstName;
        @XmlElement(name = "gender")
        public String gender;
        @XmlElement(name = "birthday")
        public String birthday;
        @XmlElement(name = "certiType")
        public String certiType;
        @XmlElement(name = "certiCode")
        public String certiCode;
        @XmlElement(name = "certiExpireDate")
        public String certiExpireDate;
        @XmlElement(name = "nationality")
        public String nationality;
        @XmlElement(name = "relation2Ins")
        public String relation2Ins;
        @XmlElement(name = "beneOrder")
        public String beneOrder;
        @XmlElement(name = "beneRate")
        public Double beneRate;
    }

    @XmlRootElement(name = "plan")
    public static class UnderwritingRequestPlan {
        @XmlElement(name = "planCode")
        public String planCode;
        @XmlElement(name = "planName")
        public String planName;
    }

    @XmlRootElement(name = "product")
    public static class UnderwritingRequestProduct {
        @XmlElement(name = "productNum")
        public String productNum;
        @XmlElement(name = "productName")
        public String productName;
        @XmlElement(name = "internalId")
        public String internalId;
        @XmlElement(name = "coveragePeriod")
        public String coveragePeriod;
        @XmlElement(name = "coverageYear")
        public Integer number;
        @XmlElement(name = "amount")
        public Double amount;
        @XmlElement(name = "unit")
        public Double unit;
        @XmlElement(name = "chargeType")
        public String chargeType;
        @XmlElement(name = "chargePeriod")
        public String chargePeriod;
        @XmlElement(name = "chargeYear")
        public Integer chargeYear;
        @XmlElement(name = "premium")
        public Double premium;
        @XmlElement(name = "exemption")
        public Double exemption;
        @XmlElement(name = "payRate")
        public Double payRate;
        @XmlElement(name = "countWay")
        public String countWay;
        @XmlElement(name = "renewDecision")
        public String renewDecision;
        @XmlElementWrapper(name = "liabs")
        @XmlElement(name = "liab")
        public List<UnderwritingRequestLiab> liabs;
    }

    @XmlRootElement(name = "liab")
    public static class UnderwritingRequestLiab {
        @XmlElement(name = "liabId")
        public Integer liabId;
        @XmlElement(name = "amount")
        public Double amount;
        @XmlElement(name = "unit")
        public Double unit;
        @XmlElement(name = "premium")
        public Double premium;
    }

    @XmlRootElement(name = "paymentMethod")
    public static class UnderwritingRequestPaymentMethod {
        @XmlElement(name = "payMode")
        public Integer payMode;
        @XmlElement(name = "payNext")
        public Integer payNext;
        @XmlElement(name = "bankCode")
        public String bankCode;
        @XmlElement(name = "accountType")
        public String accountType;
        @XmlElement(name = "bankAccount")
        public String bankAccount;
        @XmlElement(name = "accountName")
        public String accountName;
        @XmlElement(name = "bankBelong")
        public String bankBelong;
        @XmlElement(name = "overManage")
        public String overManage;
        @XmlElement(name = "overdueManage")
        public String overdueManage;
        @XmlElement(name = "paymentNo")
        public String paymentNo;
        @XmlElement(name = "premium")
        public Double premium;
    }

    @XmlRootElement(name = "insInterval")
    public static class UnderwritingRequestInsInterval {
        @XmlElement(name = "effectDate")
        public String effectDate;
        @XmlElement(name = "endDate")
        public String endDate;
    }

    @XmlRootElement(name = "others")
    public static class UnderwritingRequestOthers {
        @XmlElement(name = "invoiceName")
        public String invoiceName;
        @XmlElement(name = "invoiceDate")
        public String invoiceDate;
        @XmlElement(name = "invoicedeliverType")
        public String invoicedeliverType;
        @XmlElement(name = "firstName")
        public String firstName;
        @XmlElement(name = "mobile")
        public String mobile;
        @XmlElement(name = "state")
        public String state;
        @XmlElement(name = "city")
        public String city;
        @XmlElement(name = "ward")
        public String ward;
        @XmlElement(name = "address")
        public String address;
        @XmlElement(name = "postalCode")
        public String postalCode;
        @XmlElement(name = "premium")
        public Double premium;
    }

    @XmlRootElement(name = "declaration")
    public static class UnderwritingRequestDeclaration {
        @XmlElement(name = "customer")
        public String customer;
        @XmlElement(name = "orderNum")
        public String orderNum;
        @XmlElement(name = "yesNo")
        public String yesNo;
        @XmlElement(name = "fill1")
        public String fill1;
        @XmlElement(name = "fill2")
        public String fill2;
        @XmlElement(name = "fill3")
        public String fill3;
        @XmlElement(name = "fill4")
        public String fill4;
        @XmlElement(name = "fill5")
        public String fill5;
        @XmlElement(name = "fill6")
        public String fill6;
        @XmlElement(name = "fill7")
        public String fill7;
    }

    @XmlRootElement(name = "image")
    public static class UnderwritingRequestImage {
        @XmlElement(name = "imageType")
        public String imageType;
        @XmlElement(name = "fileName")
        public String fileName;
        @XmlElement(name = "pageNo")
        public Integer pageNo;
    }

    @XmlRootElement(name = "cancel")
    public static class UnderwritingRequestCancel {
        @XmlElement(name = "policyCode")
        public String policyCode;
        @XmlElement(name = "payMode")
        public Integer payMode;
        @XmlElement(name = "bankCode")
        public String bankCode;
        @XmlElement(name = "bankOutlet")
        public String bankOutlet;
        @XmlElement(name = "bankAccount")
        public String bankAccount;
        @XmlElement(name = "accoName")
        public String accoName;
        @XmlElement(name = "certiType")
        public String certiType;
        @XmlElement(name = "certiCode")
        public String certiCode;
    }
}