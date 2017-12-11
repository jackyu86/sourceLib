package com.caej.batch.model;


import java.math.BigDecimal;

public class BufAbonus {

    private BigDecimal basisforCalculatingDividend;		  // 红利/红利再投资基数 登记日基金持有人的基金份数
    private String transactionCfmDate;		  // 交易确认日期 格式为：YYYYMMDD
    private String currencyType;		  // 结算币种 具体编码依GB/T 12406-2008
    private BigDecimal volOfDividendforReinvestment;		  // 基金账户红利再投资基金份数 投资人实得红股，含被续冻的红股
    private String dividentDate;		  // 分红日/发放日
    private BigDecimal dividendAmount;		  // 基金账户红利资金 红利总金额,含冻结红利及再投资的红利
    private String xRDate;		  // 除权日
    private BigDecimal confirmedAmount;		  // 每笔交易确认金额 实发红利资金，不含冻结红利及再投资的红利
    private String fundCode;		  // 基金代码
    private String registrationDate;		  // 权益登记日期 格式为：YYYYMMDD
    private String returnCode;		  // 交易处理返回代码 取值见附录B
    private String transactionAccountID;		  // 投资人基金交易账号 投资人在销售机构内开设的用于交易的账号
    private String distributorCode;		  // 销售人代码
    private String businessCode;		  // 业务代码 编码见表4
    private String tAAccountID;		  // 投资人基金账号
    private BigDecimal dividendPerUnit;		  // 单位基金分红金额（含税） 举例：每千份分两元，则此处填2。
    private String defDividendMethod;		  // 默认分红方式 0-红利转投，1-现金分红
    private String depositAcct;		  // 投资人在销售人处用于交易的资金账号 投资人本次分红的方式
    private String regionCode;		  // 交易所在地区编号
    private String downLoaddate;		  // 交易数据下传日期
    private BigDecimal charge;		  // 手续费 指发送日期
    private BigDecimal agencyFee;		  // 代理费
    private BigDecimal totalFrozenVol;		  // 基金冻结总份数
    private BigDecimal nAV;		  // 基金单位净值
    private String branchCode;		  // 网点号码
    private BigDecimal otherFee1;		  // 其他费用1 托管网点号码。对大集中方式的销售人，此字段与销售人代码相同。
    private BigDecimal otherFee2;		  // 其他费用2
    private String individualOrInstitution;		  // 个人/机构标志
    private BigDecimal dividendRatio;		  // 红利比例 0-机构，1-个人
    private String tASerialNO;		  // TA确认交易流水号
    private BigDecimal stampDuty;		  // 印花税 TA对每笔确认的唯一标识，同一日不能重复，
    private BigDecimal frozenBalance;		  // 冻结金额 与交易确认日期TransactionCfmDate一起组成TA中一笔确认的唯一键
    private BigDecimal transferFee;		  // 过户费
    private String shareClass;		  // 收费方式
    private String feeCalculator;		  // 计费人
    private BigDecimal drawBonusUnit;		  // 分红单位 0-前收费，1-后收费
    private BigDecimal frozenSharesforReinvest;		  // 冻结再投资份额 0-TA计费  1-基金计费
    private String dividendType;		  // 分红类型 举例：每千份分多少，则分红单位就为一千
    private String originalAppSheetNo;		  // 原申请单编号
    private BigDecimal achievementPay;		  // 业绩报酬 0-普通分红，1-质押基金分红，2-货币基金收益结转，3-保本基金赔付，4-专户到期处理
    private BigDecimal achievementCompen;		  // 业绩补偿 对质押基金分红为Y项，表示原质押业务的申请单编号


    public BigDecimal getBasisforCalculatingDividend() {
        return basisforCalculatingDividend;
    }

    public void setBasisforCalculatingDividend(BigDecimal basisforCalculatingDividend) {
        this.basisforCalculatingDividend = basisforCalculatingDividend;
    }

    public String getTransactionCfmDate() {
        return transactionCfmDate;
    }

    public void setTransactionCfmDate(String transactionCfmDate) {
        this.transactionCfmDate = transactionCfmDate;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getVolOfDividendforReinvestment() {
        return volOfDividendforReinvestment;
    }

    public void setVolOfDividendforReinvestment(BigDecimal volOfDividendforReinvestment) {
        this.volOfDividendforReinvestment = volOfDividendforReinvestment;
    }

    public String getDividentDate() {
        return dividentDate;
    }

    public void setDividentDate(String dividentDate) {
        this.dividentDate = dividentDate;
    }

    public BigDecimal getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(BigDecimal dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    public String getXRDate() {
        return xRDate;
    }

    public void setxRDate(String xRDate) {
        this.xRDate = xRDate;
    }

    public BigDecimal getConfirmedAmount() {
        return confirmedAmount;
    }

    public void setConfirmedAmount(BigDecimal confirmedAmount) {
        this.confirmedAmount = confirmedAmount;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
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

    public String gettAAccountID() {
        return tAAccountID;
    }

    public void settAAccountID(String tAAccountID) {
        this.tAAccountID = tAAccountID;
    }

    public BigDecimal getDividendPerUnit() {
        return dividendPerUnit;
    }

    public void setDividendPerUnit(BigDecimal dividendPerUnit) {
        this.dividendPerUnit = dividendPerUnit;
    }

    public String getDefDividendMethod() {
        return defDividendMethod;
    }

    public void setDefDividendMethod(String defDividendMethod) {
        this.defDividendMethod = defDividendMethod;
    }

    public String getDepositAcct() {
        return depositAcct;
    }

    public void setDepositAcct(String depositAcct) {
        this.depositAcct = depositAcct;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getDownLoaddate() {
        return downLoaddate;
    }

    public void setDownLoaddate(String downLoaddate) {
        this.downLoaddate = downLoaddate;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public BigDecimal getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(BigDecimal agencyFee) {
        this.agencyFee = agencyFee;
    }

    public BigDecimal getTotalFrozenVol() {
        return totalFrozenVol;
    }

    public void setTotalFrozenVol(BigDecimal totalFrozenVol) {
        this.totalFrozenVol = totalFrozenVol;
    }

    public BigDecimal getnAV() {
        return nAV;
    }

    public void setnAV(BigDecimal nAV) {
        this.nAV = nAV;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public BigDecimal getOtherFee1() {
        return otherFee1;
    }

    public void setOtherFee1(BigDecimal otherFee1) {
        this.otherFee1 = otherFee1;
    }

    public BigDecimal getOtherFee2() {
        return otherFee2;
    }

    public void setOtherFee2(BigDecimal otherFee2) {
        this.otherFee2 = otherFee2;
    }

    public String getIndividualOrInstitution() {
        return individualOrInstitution;
    }

    public void setIndividualOrInstitution(String individualOrInstitution) {
        this.individualOrInstitution = individualOrInstitution;
    }

    public BigDecimal getDividendRatio() {
        return dividendRatio;
    }

    public void setDividendRatio(BigDecimal dividendRatio) {
        this.dividendRatio = dividendRatio;
    }

    public String gettASerialNO() {
        return tASerialNO;
    }

    public void settASerialNO(String tASerialNO) {
        this.tASerialNO = tASerialNO;
    }

    public BigDecimal getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(BigDecimal stampDuty) {
        this.stampDuty = stampDuty;
    }

    public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public String getShareClass() {
        return shareClass;
    }

    public void setShareClass(String shareClass) {
        this.shareClass = shareClass;
    }

    public String getFeeCalculator() {
        return feeCalculator;
    }

    public void setFeeCalculator(String feeCalculator) {
        this.feeCalculator = feeCalculator;
    }

    public BigDecimal getDrawBonusUnit() {
        return drawBonusUnit;
    }

    public void setDrawBonusUnit(BigDecimal drawBonusUnit) {
        this.drawBonusUnit = drawBonusUnit;
    }

    public BigDecimal getFrozenSharesforReinvest() {
        return frozenSharesforReinvest;
    }

    public void setFrozenSharesforReinvest(BigDecimal frozenSharesforReinvest) {
        this.frozenSharesforReinvest = frozenSharesforReinvest;
    }

    public String getDividendType() {
        return dividendType;
    }

    public void setDividendType(String dividendType) {
        this.dividendType = dividendType;
    }

    public String getOriginalAppSheetNo() {
        return originalAppSheetNo;
    }

    public void setOriginalAppSheetNo(String originalAppSheetNo) {
        this.originalAppSheetNo = originalAppSheetNo;
    }

    public BigDecimal getAchievementPay() {
        return achievementPay;
    }

    public void setAchievementPay(BigDecimal achievementPay) {
        this.achievementPay = achievementPay;
    }

    public BigDecimal getAchievementCompen() {
        return achievementCompen;
    }

    public void setAchievementCompen(BigDecimal achievementCompen) {
        this.achievementCompen = achievementCompen;
    }
}
