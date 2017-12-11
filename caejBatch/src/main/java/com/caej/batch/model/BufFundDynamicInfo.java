package com.caej.batch.model;

import java.math.BigDecimal;

/**
 * buf_fund_dynamic_info
 * @author E-mail: yanhang0610@qq.com
 * Create Time：2017-12-06 20:33:56
 */
public class BufFundDynamicInfo {

    private String id;		  // id
    private String fundName;		  // 基金名称
    private BigDecimal totalFundVol;		  // 基金总份数
    private String fundCode;		  // 基金代码
    private String fundStatus;		  // 基金状态 0-可申购赎回，1-发行4-停止申购赎回5-停止申购，6-停止赎回8-基金终止，9-基金封闭
    private BigDecimal nAV;		  // 基金单位净值
    private String updateDate;		  // 基金净值日期 格式为：YYYYMMDD
    private String netValueType;		  // 净值类型 0-普通净值1-申购净值 2-赎回净值
    private BigDecimal accumulativeNAV;		  // 累计基金单位净值
    private String convertStatus;		  // 基金转换状态 0-可转入，可转出1-只可转入2-只可转出3-不可转换
    private String periodicStatus;		  // 定期定额状态 0-允许定期定额业务1-仅允许定投业务2-仅允许定赎业务3-禁止定期定额业务
    private String transferAgencyStatus;		  // 转托管状态 0-允许所有转托管1-仅允许场外转托管2-仅允许跨市场转托管3-禁止所有转托管
    private BigDecimal fundSize;		  // 基金规模 基金的金额规模
    private String currencyType;		  // 结算币种 具体编码依GB/T 12406-2008
    private String announcFlag;		  // 公告标志 0-公告  1-不公告
    private String defDividendMethod;		  // 默认分红方式
    private BigDecimal instAppSubsAmnt;		  // 法人追加认购金额
    private BigDecimal instAppSubsVol;		  // 法人追加认购份数
    private BigDecimal minAmountByInst;		  // 法人首次认购最低金额
    private BigDecimal minVolByInst;		  // 法人首次认购最低份数
    private String custodianCode;		  // 托管人代码
    private BigDecimal amountOfPeriodicSubs;		  // 定时定额申购的金额 格式为：YYYYMMDD
    private String dateOfPeriodicSubs;		  // 定时定额申购日期
    private BigDecimal maxRedemptionVol;		  // 基金最高赎回份数
    private BigDecimal minAccountBalance;		  // 基金最低持有份数 格式为：YYYYMMDD
    private String iPOStartDate;		  // 基金募集开始日期 格式为：YYYYMMDD
    private String iPOEndDate;		  // 基金募集结束日期
    private String fundManagerCode;		  // 基金管理人
    private BigDecimal indiAppSubsVol;		  // 个人追加认购份数
    private BigDecimal indiAppSubsAmount;		  // 个人追加认购金额
    private BigDecimal minSubsVolByIndi;		  // 个人首次认购最低份数
    private BigDecimal minSubsAmountByIndi;		  // 个人首次认购最低金额
    private String registrarCode;		  // 注册登记人代码
    private String fundSponsor;		  // 基金发起人
    private BigDecimal tradingPrice;		  // 交易价格
    private BigDecimal faceValue;		  // 基金面值
    private String dividentDate;		  // 分红日/发放日
    private String registrationDate;		  // 权益登记日期 格式为：YYYYMMDD
    private String xRDate;		  // 除权日 表示最近一次除权日期
    private BigDecimal maxSubsVolByIndi;		  // 个人最高认购份数
    private BigDecimal maxSubsAmountByIndi;		  // 个人最高认购金额
    private BigDecimal maxSubsVolByInst;		  // 法人最高认购份数
    private BigDecimal maxSubsAmountByInst;		  // 法人最高认购金额
    private BigDecimal unitSubsVolByIndi;		  // 个人认购份数单位 表示级差含义
    private BigDecimal unitSubsAmountByIndi;		  // 个人认购金额单位 表示级差含义
    private BigDecimal unitSubsVolByInst;		  // 法人认购份数单位 表示级差含义
    private BigDecimal unitSubsAmountByInst;		  // 法人认购金额单位 表示级差含义
    private BigDecimal minBidsAmountByIndi;		  // 个人首次申购最低金额
    private BigDecimal minBidsAmountByInst;		  // 法人首次申购最低金额
    private BigDecimal minAppBidsAmountByIndi;		  // 个人追加申购最低金额
    private BigDecimal minAppBidsAmountByInst;		  // 法人追加申购最低金额
    private BigDecimal minRedemptionVol;		  // 基金最少赎回份数
    private BigDecimal minInterconvertVol;		  // 最低基金转换份数
    private String issueTypeByIndi;		  // 个人发行方式 1-比例发行2-摇号3-先来先买
    private String issueTypeByInst;		  // 机构发行方式 1-比例发行2-摇号3-先来先买
    private String subsType;		  // 认购方式 0-金额认购1-份数认购
    private String collectFeeType;		  // 交易费收取方式 0-价内费1-价外费
    private String nextTradeDate;		  // 下一开放日
    private BigDecimal valueLine;		  // 产品价值线数值
    private BigDecimal totalDivident;		  // 累计单位分红
    private BigDecimal fundIncome;		  // 货币基金万份收益 货币基金必填
    private String fundIncomeFlag;		  // 货币基金万份收益正负 0-正  1-负货币基金必填
    private BigDecimal yield;		  // 货币基金七日年化收益率 货币基金必填
    private String yieldFlag;		  // 货币基金七日年化收益率正负 0-正  1-负货币基金必填
    private BigDecimal guaranteedNAV;		  // 保本净值
    private BigDecimal fundYearIncomeRate;		  // 货币基金年收益率 最近一年来的收益率
    private String fundYearIncomeRateFlag;		  // 货币基金年收益率正负
    private BigDecimal indiMaxPurchase;		  // 个人最大申购金额
    private BigDecimal instMaxPurchase;		  // 法人最大申购金额
    private BigDecimal indiDayMaxSumBuy;		  // 个人当日累计购买最大金额
    private BigDecimal instDayMaxSumBuy;		  // 法人当日累计购买最大金额
    private BigDecimal indiDayMaxSumRedeem;		  // 个人当日累计赎回最大份额
    private BigDecimal instDayMaxSumRedeem;		  // 法人当日累计赎回最大份额
    private BigDecimal indiMaxRedeem;		  // 个人最大赎回份额
    private BigDecimal instMaxRedeem;		  // 法人最大赎回份额
    private String fundDayIncomeFlag;		  // 基金当日总收益正负 货币基金必填
    private BigDecimal fundDayIncome;		  // 基金当日总收益 货币基金必填
    private String allowBreachRedempt;		  // 允许违约赎回标志 0-允许，1-不允许
    private String fundType;		  // 基金类型
    private String fundTypeName;		  // 基金类型名称
    private String registrarName;		  // 注册登记人名称
    private String fundManagerName;		  // 基金管理人名称
    private String fundServerTel;		  // 基金公司客服电话
    private String fundInternetAddress;		  // 基金公司网站网址


    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public BigDecimal getTotalFundVol() {
        return totalFundVol;
    }

    public void setTotalFundVol(BigDecimal totalFundVol) {
        this.totalFundVol = totalFundVol;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public BigDecimal getnAV() {
        return nAV;
    }

    public void setnAV(BigDecimal nAV) {
        this.nAV = nAV;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getNetValueType() {
        return netValueType;
    }

    public void setNetValueType(String netValueType) {
        this.netValueType = netValueType;
    }

    public BigDecimal getAccumulativeNAV() {
        return accumulativeNAV;
    }

    public void setAccumulativeNAV(BigDecimal accumulativeNAV) {
        this.accumulativeNAV = accumulativeNAV;
    }

    public String getConvertStatus() {
        return convertStatus;
    }

    public void setConvertStatus(String convertStatus) {
        this.convertStatus = convertStatus;
    }

    public String getPeriodicStatus() {
        return periodicStatus;
    }

    public void setPeriodicStatus(String periodicStatus) {
        this.periodicStatus = periodicStatus;
    }

    public String getTransferAgencyStatus() {
        return transferAgencyStatus;
    }

    public void setTransferAgencyStatus(String transferAgencyStatus) {
        this.transferAgencyStatus = transferAgencyStatus;
    }

    public BigDecimal getFundSize() {
        return fundSize;
    }

    public void setFundSize(BigDecimal fundSize) {
        this.fundSize = fundSize;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getAnnouncFlag() {
        return announcFlag;
    }

    public void setAnnouncFlag(String announcFlag) {
        this.announcFlag = announcFlag;
    }

    public String getDefDividendMethod() {
        return defDividendMethod;
    }

    public void setDefDividendMethod(String defDividendMethod) {
        this.defDividendMethod = defDividendMethod;
    }

    public BigDecimal getInstAppSubsAmnt() {
        return instAppSubsAmnt;
    }

    public void setInstAppSubsAmnt(BigDecimal instAppSubsAmnt) {
        this.instAppSubsAmnt = instAppSubsAmnt;
    }

    public BigDecimal getInstAppSubsVol() {
        return instAppSubsVol;
    }

    public void setInstAppSubsVol(BigDecimal instAppSubsVol) {
        this.instAppSubsVol = instAppSubsVol;
    }

    public BigDecimal getMinAmountByInst() {
        return minAmountByInst;
    }

    public void setMinAmountByInst(BigDecimal minAmountByInst) {
        this.minAmountByInst = minAmountByInst;
    }

    public BigDecimal getMinVolByInst() {
        return minVolByInst;
    }

    public void setMinVolByInst(BigDecimal minVolByInst) {
        this.minVolByInst = minVolByInst;
    }

    public String getCustodianCode() {
        return custodianCode;
    }

    public void setCustodianCode(String custodianCode) {
        this.custodianCode = custodianCode;
    }

    public BigDecimal getAmountOfPeriodicSubs() {
        return amountOfPeriodicSubs;
    }

    public void setAmountOfPeriodicSubs(BigDecimal amountOfPeriodicSubs) {
        this.amountOfPeriodicSubs = amountOfPeriodicSubs;
    }

    public String getDateOfPeriodicSubs() {
        return dateOfPeriodicSubs;
    }

    public void setDateOfPeriodicSubs(String dateOfPeriodicSubs) {
        this.dateOfPeriodicSubs = dateOfPeriodicSubs;
    }

    public BigDecimal getMaxRedemptionVol() {
        return maxRedemptionVol;
    }

    public void setMaxRedemptionVol(BigDecimal maxRedemptionVol) {
        this.maxRedemptionVol = maxRedemptionVol;
    }

    public BigDecimal getMinAccountBalance() {
        return minAccountBalance;
    }

    public void setMinAccountBalance(BigDecimal minAccountBalance) {
        this.minAccountBalance = minAccountBalance;
    }

    public String getiPOStartDate() {
        return iPOStartDate;
    }

    public void setiPOStartDate(String iPOStartDate) {
        this.iPOStartDate = iPOStartDate;
    }

    public String getiPOEndDate() {
        return iPOEndDate;
    }

    public void setiPOEndDate(String iPOEndDate) {
        this.iPOEndDate = iPOEndDate;
    }

    public String getFundManagerCode() {
        return fundManagerCode;
    }

    public void setFundManagerCode(String fundManagerCode) {
        this.fundManagerCode = fundManagerCode;
    }

    public BigDecimal getIndiAppSubsVol() {
        return indiAppSubsVol;
    }

    public void setIndiAppSubsVol(BigDecimal indiAppSubsVol) {
        this.indiAppSubsVol = indiAppSubsVol;
    }

    public BigDecimal getIndiAppSubsAmount() {
        return indiAppSubsAmount;
    }

    public void setIndiAppSubsAmount(BigDecimal indiAppSubsAmount) {
        this.indiAppSubsAmount = indiAppSubsAmount;
    }

    public BigDecimal getMinSubsVolByIndi() {
        return minSubsVolByIndi;
    }

    public void setMinSubsVolByIndi(BigDecimal minSubsVolByIndi) {
        this.minSubsVolByIndi = minSubsVolByIndi;
    }

    public BigDecimal getMinSubsAmountByIndi() {
        return minSubsAmountByIndi;
    }

    public void setMinSubsAmountByIndi(BigDecimal minSubsAmountByIndi) {
        this.minSubsAmountByIndi = minSubsAmountByIndi;
    }

    public String getRegistrarCode() {
        return registrarCode;
    }

    public void setRegistrarCode(String registrarCode) {
        this.registrarCode = registrarCode;
    }

    public String getFundSponsor() {
        return fundSponsor;
    }

    public void setFundSponsor(String fundSponsor) {
        this.fundSponsor = fundSponsor;
    }

    public BigDecimal getTradingPrice() {
        return tradingPrice;
    }

    public void setTradingPrice(BigDecimal tradingPrice) {
        this.tradingPrice = tradingPrice;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public String getDividentDate() {
        return dividentDate;
    }

    public void setDividentDate(String dividentDate) {
        this.dividentDate = dividentDate;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getxRDate() {
        return xRDate;
    }

    public void setxRDate(String xRDate) {
        this.xRDate = xRDate;
    }

    public BigDecimal getMaxSubsVolByIndi() {
        return maxSubsVolByIndi;
    }

    public void setMaxSubsVolByIndi(BigDecimal maxSubsVolByIndi) {
        this.maxSubsVolByIndi = maxSubsVolByIndi;
    }

    public BigDecimal getMaxSubsAmountByIndi() {
        return maxSubsAmountByIndi;
    }

    public void setMaxSubsAmountByIndi(BigDecimal maxSubsAmountByIndi) {
        this.maxSubsAmountByIndi = maxSubsAmountByIndi;
    }

    public BigDecimal getMaxSubsVolByInst() {
        return maxSubsVolByInst;
    }

    public void setMaxSubsVolByInst(BigDecimal maxSubsVolByInst) {
        this.maxSubsVolByInst = maxSubsVolByInst;
    }

    public BigDecimal getMaxSubsAmountByInst() {
        return maxSubsAmountByInst;
    }

    public void setMaxSubsAmountByInst(BigDecimal maxSubsAmountByInst) {
        this.maxSubsAmountByInst = maxSubsAmountByInst;
    }

    public BigDecimal getUnitSubsVolByIndi() {
        return unitSubsVolByIndi;
    }

    public void setUnitSubsVolByIndi(BigDecimal unitSubsVolByIndi) {
        this.unitSubsVolByIndi = unitSubsVolByIndi;
    }

    public BigDecimal getUnitSubsAmountByIndi() {
        return unitSubsAmountByIndi;
    }

    public void setUnitSubsAmountByIndi(BigDecimal unitSubsAmountByIndi) {
        this.unitSubsAmountByIndi = unitSubsAmountByIndi;
    }

    public BigDecimal getUnitSubsVolByInst() {
        return unitSubsVolByInst;
    }

    public void setUnitSubsVolByInst(BigDecimal unitSubsVolByInst) {
        this.unitSubsVolByInst = unitSubsVolByInst;
    }

    public BigDecimal getUnitSubsAmountByInst() {
        return unitSubsAmountByInst;
    }

    public void setUnitSubsAmountByInst(BigDecimal unitSubsAmountByInst) {
        this.unitSubsAmountByInst = unitSubsAmountByInst;
    }

    public BigDecimal getMinBidsAmountByIndi() {
        return minBidsAmountByIndi;
    }

    public void setMinBidsAmountByIndi(BigDecimal minBidsAmountByIndi) {
        this.minBidsAmountByIndi = minBidsAmountByIndi;
    }

    public BigDecimal getMinBidsAmountByInst() {
        return minBidsAmountByInst;
    }

    public void setMinBidsAmountByInst(BigDecimal minBidsAmountByInst) {
        this.minBidsAmountByInst = minBidsAmountByInst;
    }

    public BigDecimal getMinAppBidsAmountByIndi() {
        return minAppBidsAmountByIndi;
    }

    public void setMinAppBidsAmountByIndi(BigDecimal minAppBidsAmountByIndi) {
        this.minAppBidsAmountByIndi = minAppBidsAmountByIndi;
    }

    public BigDecimal getMinAppBidsAmountByInst() {
        return minAppBidsAmountByInst;
    }

    public void setMinAppBidsAmountByInst(BigDecimal minAppBidsAmountByInst) {
        this.minAppBidsAmountByInst = minAppBidsAmountByInst;
    }

    public BigDecimal getMinRedemptionVol() {
        return minRedemptionVol;
    }

    public void setMinRedemptionVol(BigDecimal minRedemptionVol) {
        this.minRedemptionVol = minRedemptionVol;
    }

    public BigDecimal getMinInterconvertVol() {
        return minInterconvertVol;
    }

    public void setMinInterconvertVol(BigDecimal minInterconvertVol) {
        this.minInterconvertVol = minInterconvertVol;
    }

    public String getIssueTypeByIndi() {
        return issueTypeByIndi;
    }

    public void setIssueTypeByIndi(String issueTypeByIndi) {
        this.issueTypeByIndi = issueTypeByIndi;
    }

    public String getIssueTypeByInst() {
        return issueTypeByInst;
    }

    public void setIssueTypeByInst(String issueTypeByInst) {
        this.issueTypeByInst = issueTypeByInst;
    }

    public String getSubsType() {
        return subsType;
    }

    public void setSubsType(String subsType) {
        this.subsType = subsType;
    }

    public String getCollectFeeType() {
        return collectFeeType;
    }

    public void setCollectFeeType(String collectFeeType) {
        this.collectFeeType = collectFeeType;
    }

    public String getNextTradeDate() {
        return nextTradeDate;
    }

    public void setNextTradeDate(String nextTradeDate) {
        this.nextTradeDate = nextTradeDate;
    }

    public BigDecimal getValueLine() {
        return valueLine;
    }

    public void setValueLine(BigDecimal valueLine) {
        this.valueLine = valueLine;
    }

    public BigDecimal getTotalDivident() {
        return totalDivident;
    }

    public void setTotalDivident(BigDecimal totalDivident) {
        this.totalDivident = totalDivident;
    }

    public BigDecimal getFundIncome() {
        return fundIncome;
    }

    public void setFundIncome(BigDecimal fundIncome) {
        this.fundIncome = fundIncome;
    }

    public String getFundIncomeFlag() {
        return fundIncomeFlag;
    }

    public void setFundIncomeFlag(String fundIncomeFlag) {
        this.fundIncomeFlag = fundIncomeFlag;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public String getYieldFlag() {
        return yieldFlag;
    }

    public void setYieldFlag(String yieldFlag) {
        this.yieldFlag = yieldFlag;
    }

    public BigDecimal getGuaranteedNAV() {
        return guaranteedNAV;
    }

    public void setGuaranteedNAV(BigDecimal guaranteedNAV) {
        this.guaranteedNAV = guaranteedNAV;
    }

    public BigDecimal getFundYearIncomeRate() {
        return fundYearIncomeRate;
    }

    public void setFundYearIncomeRate(BigDecimal fundYearIncomeRate) {
        this.fundYearIncomeRate = fundYearIncomeRate;
    }

    public String getFundYearIncomeRateFlag() {
        return fundYearIncomeRateFlag;
    }

    public void setFundYearIncomeRateFlag(String fundYearIncomeRateFlag) {
        this.fundYearIncomeRateFlag = fundYearIncomeRateFlag;
    }

    public BigDecimal getIndiMaxPurchase() {
        return indiMaxPurchase;
    }

    public void setIndiMaxPurchase(BigDecimal indiMaxPurchase) {
        this.indiMaxPurchase = indiMaxPurchase;
    }

    public BigDecimal getInstMaxPurchase() {
        return instMaxPurchase;
    }

    public void setInstMaxPurchase(BigDecimal instMaxPurchase) {
        this.instMaxPurchase = instMaxPurchase;
    }

    public BigDecimal getIndiDayMaxSumBuy() {
        return indiDayMaxSumBuy;
    }

    public void setIndiDayMaxSumBuy(BigDecimal indiDayMaxSumBuy) {
        this.indiDayMaxSumBuy = indiDayMaxSumBuy;
    }

    public BigDecimal getInstDayMaxSumBuy() {
        return instDayMaxSumBuy;
    }

    public void setInstDayMaxSumBuy(BigDecimal instDayMaxSumBuy) {
        this.instDayMaxSumBuy = instDayMaxSumBuy;
    }

    public BigDecimal getIndiDayMaxSumRedeem() {
        return indiDayMaxSumRedeem;
    }

    public void setIndiDayMaxSumRedeem(BigDecimal indiDayMaxSumRedeem) {
        this.indiDayMaxSumRedeem = indiDayMaxSumRedeem;
    }

    public BigDecimal getInstDayMaxSumRedeem() {
        return instDayMaxSumRedeem;
    }

    public void setInstDayMaxSumRedeem(BigDecimal instDayMaxSumRedeem) {
        this.instDayMaxSumRedeem = instDayMaxSumRedeem;
    }

    public BigDecimal getIndiMaxRedeem() {
        return indiMaxRedeem;
    }

    public void setIndiMaxRedeem(BigDecimal indiMaxRedeem) {
        this.indiMaxRedeem = indiMaxRedeem;
    }

    public BigDecimal getInstMaxRedeem() {
        return instMaxRedeem;
    }

    public void setInstMaxRedeem(BigDecimal instMaxRedeem) {
        this.instMaxRedeem = instMaxRedeem;
    }

    public String getFundDayIncomeFlag() {
        return fundDayIncomeFlag;
    }

    public void setFundDayIncomeFlag(String fundDayIncomeFlag) {
        this.fundDayIncomeFlag = fundDayIncomeFlag;
    }

    public BigDecimal getFundDayIncome() {
        return fundDayIncome;
    }

    public void setFundDayIncome(BigDecimal fundDayIncome) {
        this.fundDayIncome = fundDayIncome;
    }

    public String getAllowBreachRedempt() {
        return allowBreachRedempt;
    }

    public void setAllowBreachRedempt(String allowBreachRedempt) {
        this.allowBreachRedempt = allowBreachRedempt;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getFundTypeName() {
        return fundTypeName;
    }

    public void setFundTypeName(String fundTypeName) {
        this.fundTypeName = fundTypeName;
    }

    public String getRegistrarName() {
        return registrarName;
    }

    public void setRegistrarName(String registrarName) {
        this.registrarName = registrarName;
    }

    public String getFundManagerName() {
        return fundManagerName;
    }

    public void setFundManagerName(String fundManagerName) {
        this.fundManagerName = fundManagerName;
    }

    public String getFundServerTel() {
        return fundServerTel;
    }

    public void setFundServerTel(String fundServerTel) {
        this.fundServerTel = fundServerTel;
    }

    public String getFundInternetAddress() {
        return fundInternetAddress;
    }

    public void setFundInternetAddress(String fundInternetAddress) {
        this.fundInternetAddress = fundInternetAddress;
    }

    public String toString() {
        return
                "id: " + id + ", " +
                        "fundName: " + fundName + ", " +
                        "totalFundVol: " + totalFundVol + ", " +
                        "fundCode: " + fundCode + ", " +
                        "fundStatus: " + fundStatus + ", " +
                        "nAV: " + nAV + ", " +
                        "updateDate: " + updateDate + ", " +
                        "netValueType: " + netValueType + ", " +
                        "accumulativeNAV: " + accumulativeNAV + ", " +
                        "convertStatus: " + convertStatus + ", " +
                        "periodicStatus: " + periodicStatus + ", " +
                        "transferAgencyStatus: " + transferAgencyStatus + ", " +
                        "fundSize: " + fundSize + ", " +
                        "currencyType: " + currencyType + ", " +
                        "announcFlag: " + announcFlag + ", " +
                        "defDividendMethod: " + defDividendMethod + ", " +
                        "instAppSubsAmnt: " + instAppSubsAmnt + ", " +
                        "instAppSubsVol: " + instAppSubsVol + ", " +
                        "minAmountByInst: " + minAmountByInst + ", " +
                        "minVolByInst: " + minVolByInst + ", " +
                        "custodianCode: " + custodianCode + ", " +
                        "amountOfPeriodicSubs: " + amountOfPeriodicSubs + ", " +
                        "dateOfPeriodicSubs: " + dateOfPeriodicSubs + ", " +
                        "maxRedemptionVol: " + maxRedemptionVol + ", " +
                        "minAccountBalance: " + minAccountBalance + ", " +
                        "iPOStartDate: " + iPOStartDate + ", " +
                        "iPOEndDate: " + iPOEndDate + ", " +
                        "fundManagerCode: " + fundManagerCode + ", " +
                        "indiAppSubsVol: " + indiAppSubsVol + ", " +
                        "indiAppSubsAmount: " + indiAppSubsAmount + ", " +
                        "minSubsVolByIndi: " + minSubsVolByIndi + ", " +
                        "minSubsAmountByIndi: " + minSubsAmountByIndi + ", " +
                        "registrarCode: " + registrarCode + ", " +
                        "fundSponsor: " + fundSponsor + ", " +
                        "tradingPrice: " + tradingPrice + ", " +
                        "faceValue: " + faceValue + ", " +
                        "dividentDate: " + dividentDate + ", " +
                        "registrationDate: " + registrationDate + ", " +
                        "xRDate: " + xRDate + ", " +
                        "maxSubsVolByIndi: " + maxSubsVolByIndi + ", " +
                        "maxSubsAmountByIndi: " + maxSubsAmountByIndi + ", " +
                        "maxSubsVolByInst: " + maxSubsVolByInst + ", " +
                        "maxSubsAmountByInst: " + maxSubsAmountByInst + ", " +
                        "unitSubsVolByIndi: " + unitSubsVolByIndi + ", " +
                        "unitSubsAmountByIndi: " + unitSubsAmountByIndi + ", " +
                        "unitSubsVolByInst: " + unitSubsVolByInst + ", " +
                        "unitSubsAmountByInst: " + unitSubsAmountByInst + ", " +
                        "minBidsAmountByIndi: " + minBidsAmountByIndi + ", " +
                        "minBidsAmountByInst: " + minBidsAmountByInst + ", " +
                        "minAppBidsAmountByIndi: " + minAppBidsAmountByIndi + ", " +
                        "minAppBidsAmountByInst: " + minAppBidsAmountByInst + ", " +
                        "minRedemptionVol: " + minRedemptionVol + ", " +
                        "minInterconvertVol: " + minInterconvertVol + ", " +
                        "issueTypeByIndi: " + issueTypeByIndi + ", " +
                        "issueTypeByInst: " + issueTypeByInst + ", " +
                        "subsType: " + subsType + ", " +
                        "collectFeeType: " + collectFeeType + ", " +
                        "nextTradeDate: " + nextTradeDate + ", " +
                        "valueLine: " + valueLine + ", " +
                        "totalDivident: " + totalDivident + ", " +
                        "fundIncome: " + fundIncome + ", " +
                        "fundIncomeFlag: " + fundIncomeFlag + ", " +
                        "yield: " + yield + ", " +
                        "yieldFlag: " + yieldFlag + ", " +
                        "guaranteedNAV: " + guaranteedNAV + ", " +
                        "fundYearIncomeRate: " + fundYearIncomeRate + ", " +
                        "fundYearIncomeRateFlag: " + fundYearIncomeRateFlag + ", " +
                        "indiMaxPurchase: " + indiMaxPurchase + ", " +
                        "instMaxPurchase: " + instMaxPurchase + ", " +
                        "indiDayMaxSumBuy: " + indiDayMaxSumBuy + ", " +
                        "instDayMaxSumBuy: " + instDayMaxSumBuy + ", " +
                        "indiDayMaxSumRedeem: " + indiDayMaxSumRedeem + ", " +
                        "instDayMaxSumRedeem: " + instDayMaxSumRedeem + ", " +
                        "indiMaxRedeem: " + indiMaxRedeem + ", " +
                        "instMaxRedeem: " + instMaxRedeem + ", " +
                        "fundDayIncomeFlag: " + fundDayIncomeFlag + ", " +
                        "fundDayIncome: " + fundDayIncome + ", " +
                        "allowBreachRedempt: " + allowBreachRedempt + ", " +
                        "fundType: " + fundType + ", " +
                        "fundTypeName: " + fundTypeName + ", " +
                        "registrarName: " + registrarName + ", " +
                        "fundManagerName: " + fundManagerName + ", " +
                        "fundServerTel: " + fundServerTel + ", " +
                        "fundInternetAddress: " + fundInternetAddress ;
    }

}



