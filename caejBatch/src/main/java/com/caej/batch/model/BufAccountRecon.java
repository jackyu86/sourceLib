package com.caej.batch.model;


import java.math.BigDecimal;

/**
 * buf_account_recon
 * @author E-mail: yanhang0610@qq.com
 * Create Time：2017-12-06 20:33:55
 */
public class BufAccountRecon {
    private BigDecimal availableVol;		  // 持有人可用基金份数
    private BigDecimal totalVolOfDistributorInTA;		  // 基金总份数（含冻结）
    private String transactionCfmDate;		  // 交易确认日期 格式为：YYYYMMDD
    private String fundCode;		  // 基金代码
    private String transactionAccountID;		  // 投资人基金交易账号 投资人在销售机构内开设的用于交易的账号
    private String distributorCode;		  // 销售人代码
    private String tAAccountID;		  // 投资人基金账号
    private BigDecimal totalFrozenVol;		  // 基金冻结总份数 仅包括账户类和交易类冻结业务及派生继续冻结的份额
    private String branchCode;		  // 网点号码
    private String tASerialNO;		  // TA确认交易流水号
    private BigDecimal totalBackendLoad;		  // 交易后端收费总额
    private String shareClass;		  // 收费方式 0-前收费，1-后收费
    private String detailFlag;		  // 明细标志 0-非明细，1-明细非明细指针对基金账户的对账，明细指针对基金账户具体过户日或TA确认流水号的对账
    private String accountStatus;		  // 账户状态 0-正常，1-冻结，2-挂失
    private String shareRegisterDate;		  // 份额注册日期 明细标志为1时必填
    private BigDecimal undistributeMonetaryIncome;		  // 货币基金未付收益金额 对货币基金，明细标志为0时必填
    private String undistributeMonetaryIncomeFlag;		  // 货币基金未付收益金额正负 0-正  1-负对货币基金，明细标志为0时必填
    private BigDecimal guaranteedAmount;		  // 剩余保本金额
    private String sourceType;		  // 份额原始来源 0-认购1-申购 2-定期定额申购3-分红明细标志为1时必填
    private String defDividendMethod;		  // 默认分红方式 0-红利转投，1-现金分红

    public BigDecimal getAvailableVol() {
        return availableVol;
    }

    public void setAvailableVol(BigDecimal availableVol) {
        this.availableVol = availableVol;
    }

    public BigDecimal getTotalVolOfDistributorInTA() {
        return totalVolOfDistributorInTA;
    }

    public void setTotalVolOfDistributorInTA(BigDecimal totalVolOfDistributorInTA) {
        this.totalVolOfDistributorInTA = totalVolOfDistributorInTA;
    }

    public String getTransactionCfmDate() {
        return transactionCfmDate;
    }

    public void setTransactionCfmDate(String transactionCfmDate) {
        this.transactionCfmDate = transactionCfmDate;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
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

    public String gettAAccountID() {
        return tAAccountID;
    }

    public void settAAccountID(String tAAccountID) {
        this.tAAccountID = tAAccountID;
    }

    public BigDecimal getTotalFrozenVol() {
        return totalFrozenVol;
    }

    public void setTotalFrozenVol(BigDecimal totalFrozenVol) {
        this.totalFrozenVol = totalFrozenVol;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String gettASerialNO() {
        return tASerialNO;
    }

    public void settASerialNO(String tASerialNO) {
        this.tASerialNO = tASerialNO;
    }

    public BigDecimal getTotalBackendLoad() {
        return totalBackendLoad;
    }

    public void setTotalBackendLoad(BigDecimal totalBackendLoad) {
        this.totalBackendLoad = totalBackendLoad;
    }

    public String getShareClass() {
        return shareClass;
    }

    public void setShareClass(String shareClass) {
        this.shareClass = shareClass;
    }

    public String getDetailFlag() {
        return detailFlag;
    }

    public void setDetailFlag(String detailFlag) {
        this.detailFlag = detailFlag;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getShareRegisterDate() {
        return shareRegisterDate;
    }

    public void setShareRegisterDate(String shareRegisterDate) {
        this.shareRegisterDate = shareRegisterDate;
    }

    public BigDecimal getUndistributeMonetaryIncome() {
        return undistributeMonetaryIncome;
    }

    public void setUndistributeMonetaryIncome(BigDecimal undistributeMonetaryIncome) {
        this.undistributeMonetaryIncome = undistributeMonetaryIncome;
    }

    public String getUndistributeMonetaryIncomeFlag() {
        return undistributeMonetaryIncomeFlag;
    }

    public void setUndistributeMonetaryIncomeFlag(String undistributeMonetaryIncomeFlag) {
        this.undistributeMonetaryIncomeFlag = undistributeMonetaryIncomeFlag;
    }

    public BigDecimal getGuaranteedAmount() {
        return guaranteedAmount;
    }

    public void setGuaranteedAmount(BigDecimal guaranteedAmount) {
        this.guaranteedAmount = guaranteedAmount;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDefDividendMethod() {
        return defDividendMethod;
    }

    public void setDefDividendMethod(String defDividendMethod) {
        this.defDividendMethod = defDividendMethod;
    }

    public String toString() {
        return
                "availableVol: " + availableVol + ", " +
                        "totalVolOfDistributorInTA: " + totalVolOfDistributorInTA + ", " +
                        "transactionCfmDate: " + transactionCfmDate + ", " +
                        "fundCode: " + fundCode + ", " +
                        "transactionAccountID: " + transactionAccountID + ", " +
                        "distributorCode: " + distributorCode + ", " +
                        "tAAccountID: " + tAAccountID + ", " +
                        "totalFrozenVol: " + totalFrozenVol + ", " +
                        "branchCode: " + branchCode + ", " +
                        "tASerialNO: " + tASerialNO + ", " +
                        "totalBackendLoad: " + totalBackendLoad + ", " +
                        "shareClass: " + shareClass + ", " +
                        "detailFlag: " + detailFlag + ", " +
                        "accountStatus: " + accountStatus + ", " +
                        "shareRegisterDate: " + shareRegisterDate + ", " +
                        "undistributeMonetaryIncome: " + undistributeMonetaryIncome + ", " +
                        "undistributeMonetaryIncomeFlag: " + undistributeMonetaryIncomeFlag + ", " +
                        "guaranteedAmount: " + guaranteedAmount + ", " +
                        "sourceType: " + sourceType + ", " +
                        "defDividendMethod: " + defDividendMethod ;
    }

}


