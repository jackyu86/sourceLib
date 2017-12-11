package com.caej.batch.model;


/**
 * buf_account_confirm
 * @author E-mail: yanhang0610@qq.com
 * Create Time：2017-12-04 15:37:15
 */

public class BufAccountConfirm {

    private String id;		  // id
    private String appSheetSerialNo;		  // 申请单编号
    private String transactionCfmDate;		  // 交易确认日期
    public String transactionAccountID;		  // 投资人基金交易帐号
    private String returnCode;		  // 交易处理返回代码
    private String distributorCode;		  // 销售人代码
    private String businessCode;		  // 业务代码
    private String tAAccountID;		  // 投资人基金帐号
    private String multiAcctFlag;		  // 多渠道开户标志
    private String tASerialNO;		  // TA确认交易流水号
    private String transactionDate;		  // 交易发生日期
    private String transactionTime;		  // 交易发生时间
    private String branchCode;		  // 网点号码
    private String fromTAFlag;		  // 是否注册登记人发起业务标志
    private String certificateType;		  // 个人证件类型及机构证件型
    private String certificateNo;		  // 投资人证件号码
    private String investorName;		  // 投资人户名
    private String individualOrInstitution;		  // 个人/机构标志
    private String accountAbbr;		  // 投资人户名简称
    private String accountCardID;		  // 基金账户卡的凭证号
    private String regionCode;		  // 交易所在地区编号
    private String targetTransactionAccountID;		  // 对方销售人处投资人基金交易帐号
    private String netNo;		  // 操作（清算）网点编号
    private String specification;		  // 摘要/说明
    private String customerNo;		  // TA客户编号
    private String frozenCause;		  // 冻结原因
    private String freezingDeadline;		  // 冻结截止日期
    private String errorDetail;		  // 出错详细信息

    public BufAccountConfirm() {}

    public String toString() {
        return
                "id: " + id + ", " +
                        "appSheetSerialNo: " + appSheetSerialNo + ", " +
                        "transactionCfmDate: " + transactionCfmDate + ", " +
                        "returnCode: " + returnCode + ", " +
                        "transactionAccountID: " + transactionAccountID + ", " +
                        "distributorCode: " + distributorCode + ", " +
                        "businessCode: " + businessCode + ", " +
                        "tAAccountID: " + tAAccountID + ", " +
                        "multiAcctFlag: " + multiAcctFlag + ", " +
                        "tASerialNO: " + tASerialNO + ", " +
                        "transactionDate: " + transactionDate + ", " +
                        "transactionTime: " + transactionTime + ", " +
                        "branchCode: " + branchCode + ", " +
                        "fromTAFlag: " + fromTAFlag + ", " +
                        "certificateType: " + certificateType + ", " +
                        "certificateNo: " + certificateNo + ", " +
                        "investorName: " + investorName + ", " +
                        "individualOrInstitution: " + individualOrInstitution + ", " +
                        "accountAbbr: " + accountAbbr + ", " +
                        "accountCardID: " + accountCardID + ", " +
                        "regionCode: " + regionCode + ", " +
                        "targetTransactionAccountID: " + targetTransactionAccountID + ", " +
                        "netNo: " + netNo + ", " +
                        "specification: " + specification + ", " +
                        "customerNo: " + customerNo + ", " +
                        "frozenCause: " + frozenCause + ", " +
                        "freezingDeadline: " + freezingDeadline + ", " +
                        "errorDetail: " + errorDetail ;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAppSheetSerialNo() {
        return appSheetSerialNo;
    }

    public void setAppSheetSerialNo(String appSheetSerialNo) {
        this.appSheetSerialNo = appSheetSerialNo;
    }


    public String getTransactionCfmDate() {
        return transactionCfmDate;
    }

    public void setTransactionCfmDate(String transactionCfmDate) {
        this.transactionCfmDate = transactionCfmDate;
    }


    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }


    public String getTransactionAccountID() {
        return transactionAccountID;
    }

    public void setTransactionAccountID(String transactionAccountID) {
        this.transactionAccountID = transactionAccountID;
    }


    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }


    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }


    public String getTAAccountID() {
        return tAAccountID;
    }

    public void setTAAccountID(String tAAccountID) {
        this.tAAccountID = tAAccountID;
    }


    public String getMultiAcctFlag() {
        return multiAcctFlag;
    }

    public void setMultiAcctFlag(String multiAcctFlag) {
        this.multiAcctFlag = multiAcctFlag;
    }


    public String getTASerialNO() {
        return tASerialNO;
    }

    public void setTASerialNO(String tASerialNO) {
        this.tASerialNO = tASerialNO;
    }


    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }


    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }


    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }


    public String getFromTAFlag() {
        return fromTAFlag;
    }

    public void setFromTAFlag(String fromTAFlag) {
        this.fromTAFlag = fromTAFlag;
    }


    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }


    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }


    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }


    public String getIndividualOrInstitution() {
        return individualOrInstitution;
    }

    public void setIndividualOrInstitution(String individualOrInstitution) {
        this.individualOrInstitution = individualOrInstitution;
    }


    public String getAccountAbbr() {
        return accountAbbr;
    }

    public void setAccountAbbr(String accountAbbr) {
        this.accountAbbr = accountAbbr;
    }


    public String getAccountCardID() {
        return accountCardID;
    }

    public void setAccountCardID(String accountCardID) {
        this.accountCardID = accountCardID;
    }


    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }


    public String getTargetTransactionAccountID() {
        return targetTransactionAccountID;
    }

    public void setTargetTransactionAccountID(String targetTransactionAccountID) {
        this.targetTransactionAccountID = targetTransactionAccountID;
    }


    public String getNetNo() {
        return netNo;
    }

    public void setNetNo(String netNo) {
        this.netNo = netNo;
    }


    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }


    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }


    public String getFrozenCause() {
        return frozenCause;
    }

    public void setFrozenCause(String frozenCause) {
        this.frozenCause = frozenCause;
    }


    public String getFreezingDeadline() {
        return freezingDeadline;
    }

    public void setFreezingDeadline(String freezingDeadline) {
        this.freezingDeadline = freezingDeadline;
    }


    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }


}


