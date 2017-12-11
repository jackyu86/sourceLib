package com.caej.batch.model;

import java.math.BigDecimal;

/**
 * @author lj on 2017/12/7.
 * @version 1.0
 * @company caej
 */

public class BufTransactionConfirm {

    private String appSheetSerialNo;		  // 申请单编号
    private String transactionCfmDate;		  // 交易确认日期
    private String currencyType;		  // 结算币种
    private BigDecimal confirmedVol;		  // 基金账户交易确认份数
    private BigDecimal confirmedAmount;		  // 每笔交易确认金额 同一销售机构不能重复
    private String fundCode;		  // 基金代码
    private String largeRedemptionFlag;		  // 巨额赎回处理标志
    private String transactionDate;		  // 交易发生日期
    private String transactionTime;		  // 交易发生时间 格式为：YYYYMMDD
    private String returnCode;		  // 交易处理返回代码 0-机构，1-个人
    private String transactionAccountID;		  // 投资人基金交易帐号 0-保险机构，1-基金公司，2-上市公司，3-信托公司，4-证券公司，5-理财产品，6-企业年金，7-社保基金
    private String distributorCode;		  // 销售人代码
    private BigDecimal applicationVol;		  // 申请基金份数 对机构必填
    private BigDecimal applicationAmount;		  // 申请金额
    private String businessCode;		  // 业务代码 对机构必填
    private String tAAccountID;		  // 投资人基金帐号 投资人在销售机构内开设的用于交易的账号
    private String tASerialNO;		  // TA确认交易流水号
    private String businessFinishFlag;		  // 业务过程完全结束标识 编码见表4
    private BigDecimal discountRateOfCommission;		  // 销售佣金折扣率
    private String depositAcct;		  // 投资人在销售人处用于交易的资金帐号
    private String regionCode;		  // 交易所在地区编号
    private String downLoaddate;		  // 交易数据下传日期 格式为：YYYYMMDD
    private BigDecimal charge;		  // 手续费
    private BigDecimal agencyFee;		  // 代理费
    private BigDecimal nAV;		  // 基金单位净值 01-研究生02-大学本科03- 大学专科04-中专或技校05-技工学校06-高中07-初中08-小学09-文盲或半文盲
    private String branchCode;		  // 网点号码
    private String originalAppSheetNo;		  // 原申请单编号
    private String originalSubsDate;		  // 原申购日期 01-党政机关、事业单位 02-企业单位03-自由业主04- 学生05-军人06-其他
    private BigDecimal otherFee1;		  // 其他费用1
    private String individualOrInstitution;		  // 个人/机构标志
    private String redemptionDateInAdvance;		  // 预约赎回日期
    private BigDecimal stampDuty;		  // 印花税 0-首次开设基金账户
    private BigDecimal validPeriod;		  // 交易申请有效天数 托管网点号码。对大集中方式的销售人，此字段与销售人代码相同。
    private BigDecimal rateFee;		  // 费率
    private BigDecimal totalBackendLoad;		  // 交易后端收费总额 格式为：HHMMSS
    private String originalSerialNo;		  // TA的原确认流水号
    private String specification;		  // 摘要/说明
    private String dateOfPeriodicSubs;		  // 定期定额申购日期 1-男，2-女
    private String targetDistributorCode;		  // 对方销售人代码
    private String targetBranchCode;		  // 对方网点号
    private String targetTransactionAccountID;		  // 对方销售人处投资人基金交易帐号 账户信息修改申请时为必选项。
    private String targetRegionCode;		  // 对方所在地区编号
    private String transferDirection;		  // 转入/转出标识
    private String defDividendMethod;		  // 默认分红方式 0-否，1-是
    private BigDecimal dividendRatio;		  // 红利比例
    private BigDecimal interest;		  // 基金账户利息金额
    private BigDecimal volumeByInterest;		  // 利息产生的基金份数
    private BigDecimal interestTax;		  // 利息税
    private BigDecimal tradingPrice;		  // 交易价格 采用GB/T 2659-2000
    private String freezingDeadline;		  // 冻结截止日期
    private String frozenCause;		  // 冻结原因 客户所属的经纪人
    private BigDecimal tax;		  // 税金
    private BigDecimal targetNAV;		  // 目标基金的单位净值
    private BigDecimal targetFundPrice;		  // 目标基金的价格
    private BigDecimal cfmVolOfTargetFund;		  // 目标基金的确认份数
    private BigDecimal minFee;		  // 最少收费
    private BigDecimal otherFee2;		  // 其他费用2
    private String originalAppDate;		  // 原申请日期
    private BigDecimal transferFee;		  // 过户费
    private String fromTAFlag;		  // 是否注册登记人发起业务标志
    private String shareClass;		  // 收费类别
    private String detailFlag;		  // 数据明细标志
    private String redemptionInAdvanceFlag;		  // 预约赎回标志
    private String frozenMethod;		  // 冻结方式
    private String originalCfmDate;		  // TA的原确认日期
    private String redemptionReason;		  // 强行赎回原因 采用国标GB/T4754-2011
    private String codeOfTargetFund;		  // 转换时的目标基金代码
    private BigDecimal totalTransFee;		  // 交易确认费用合计
    private String varietyCodeOfPeriodicSubs;		  // 定时定额品种代码
    private String serialNoOfPeriodicSubs;		  // 定时定额申购序号 采用国标GB/T 2260-2007中6位数字代码
    private String rationType;		  // 定期定额种类 采用国标GB/T 2260-2007中6位数字代码
    private String targetTAAccountID;		  // 对方基金账号 采用国标GB/T 2260-2007中6位数字代码
    private String targetRegistrarCode;		  // 对方TA代码
    private String netNo;		  // 操作（清算）网点编号 1:内部员工
    private String customerNo;		  // TA客户编号
    private String targetShareType;		  // 对方基金份额类别
    private String rationProtocolNo;		  // 定期定额协议号
    private String beginDateOfPeriodicSubs;		  // 定时定额申购起始日期
    private String endDateOfPeriodicSubs;		  // 定时定额申购终止日期
    private BigDecimal sendDayOfPeriodicSubs;		  // 定时定额申购每月发送日
    private String broker;		  // 经纪人
    private String salesPromotion;		  // 促销活动代码
    private String acceptMethod;		  // 受理方式
    private String forceRedemptionType;		  // 强制赎回类型
    private String alternationDate;		  // 最后更新日
    private String takeIncomeFlag;		  // 带走收益标志
    private String purposeOfPeSubs;		  // 定投目的
    private BigDecimal frequencyOfPeSubs;		  // 定投频率
    private String periodSubTimeUnit;		  // 定投周期单位
    private BigDecimal batchNumOfPeSubs;		  // 定投期数
    private String capitalMode;		  // 资金方式
    private String detailCapticalMode;		  // 明细资金方式
    private BigDecimal backenloadDiscount;		  // 补差费折扣率
    private String combineNum;		  // 组合编号
    private BigDecimal refundAmount;		  // 退款金额
    private BigDecimal salePercent;		  // 配售比例
    private BigDecimal managerRealRatio;		  // 实际计算折扣
    private BigDecimal changeFee;		  // 转换费
    private BigDecimal recuperateFee;		  // 补差费
    private BigDecimal achievementPay;		  // 业绩报酬
    private BigDecimal achievementCompen;		  // 业绩补偿
    private String sharesAdjustmentFlag;		  // 份额强制调整标志
    private String generalTASerialNO;		  // 总TA确认流水号
    private BigDecimal undistributeMonetaryIncome;		  // 货币基金未付收益金额
    private String undistributeMonetaryIncomeFlag;		  // 货币基金未付收益金额正负
    private BigDecimal breachFee;		  // 违约金
    private BigDecimal breachFeeBackToFund;		  // 违约金归基金资产金额
    private BigDecimal punishFee;		  // 惩罚性费用
    private String tradingMethod;		  // 使用的交易手段
    private BigDecimal changeAgencyFee;		  // 转换代理费
    private BigDecimal recuperateAgencyFee;		  // 补差代理费
    private String errorDetail;		  // 出错详细信息
    private String largeBuyFlag;		  // 巨额购买处理标志
    private BigDecimal raiseInterest;		  // 认购期间利息
    private String feeCalculator;		  // 计费人
    private String shareRegisterDate;		  // 份额注册日期

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

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getConfirmedVol() {
        return confirmedVol;
    }

    public void setConfirmedVol(BigDecimal confirmedVol) {
        this.confirmedVol = confirmedVol;
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

    public String getLargeRedemptionFlag() {
        return largeRedemptionFlag;
    }

    public void setLargeRedemptionFlag(String largeRedemptionFlag) {
        this.largeRedemptionFlag = largeRedemptionFlag;
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

    public BigDecimal getApplicationVol() {
        return applicationVol;
    }

    public void setApplicationVol(BigDecimal applicationVol) {
        this.applicationVol = applicationVol;
    }

    public BigDecimal getApplicationAmount() {
        return applicationAmount;
    }

    public void setApplicationAmount(BigDecimal applicationAmount) {
        this.applicationAmount = applicationAmount;
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

    public String gettASerialNO() {
        return tASerialNO;
    }

    public void settASerialNO(String tASerialNO) {
        this.tASerialNO = tASerialNO;
    }

    public String getBusinessFinishFlag() {
        return businessFinishFlag;
    }

    public void setBusinessFinishFlag(String businessFinishFlag) {
        this.businessFinishFlag = businessFinishFlag;
    }

    public BigDecimal getDiscountRateOfCommission() {
        return discountRateOfCommission;
    }

    public void setDiscountRateOfCommission(BigDecimal discountRateOfCommission) {
        this.discountRateOfCommission = discountRateOfCommission;
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

    public String getOriginalAppSheetNo() {
        return originalAppSheetNo;
    }

    public void setOriginalAppSheetNo(String originalAppSheetNo) {
        this.originalAppSheetNo = originalAppSheetNo;
    }

    public String getOriginalSubsDate() {
        return originalSubsDate;
    }

    public void setOriginalSubsDate(String originalSubsDate) {
        this.originalSubsDate = originalSubsDate;
    }

    public BigDecimal getOtherFee1() {
        return otherFee1;
    }

    public void setOtherFee1(BigDecimal otherFee1) {
        this.otherFee1 = otherFee1;
    }

    public String getIndividualOrInstitution() {
        return individualOrInstitution;
    }

    public void setIndividualOrInstitution(String individualOrInstitution) {
        this.individualOrInstitution = individualOrInstitution;
    }

    public String getRedemptionDateInAdvance() {
        return redemptionDateInAdvance;
    }

    public void setRedemptionDateInAdvance(String redemptionDateInAdvance) {
        this.redemptionDateInAdvance = redemptionDateInAdvance;
    }

    public BigDecimal getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(BigDecimal stampDuty) {
        this.stampDuty = stampDuty;
    }

    public BigDecimal getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(BigDecimal validPeriod) {
        this.validPeriod = validPeriod;
    }

    public BigDecimal getRateFee() {
        return rateFee;
    }

    public void setRateFee(BigDecimal rateFee) {
        this.rateFee = rateFee;
    }

    public BigDecimal getTotalBackendLoad() {
        return totalBackendLoad;
    }

    public void setTotalBackendLoad(BigDecimal totalBackendLoad) {
        this.totalBackendLoad = totalBackendLoad;
    }

    public String getOriginalSerialNo() {
        return originalSerialNo;
    }

    public void setOriginalSerialNo(String originalSerialNo) {
        this.originalSerialNo = originalSerialNo;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDateOfPeriodicSubs() {
        return dateOfPeriodicSubs;
    }

    public void setDateOfPeriodicSubs(String dateOfPeriodicSubs) {
        this.dateOfPeriodicSubs = dateOfPeriodicSubs;
    }

    public String getTargetDistributorCode() {
        return targetDistributorCode;
    }

    public void setTargetDistributorCode(String targetDistributorCode) {
        this.targetDistributorCode = targetDistributorCode;
    }

    public String getTargetBranchCode() {
        return targetBranchCode;
    }

    public void setTargetBranchCode(String targetBranchCode) {
        this.targetBranchCode = targetBranchCode;
    }

    public String getTargetTransactionAccountID() {
        return targetTransactionAccountID;
    }

    public void setTargetTransactionAccountID(String targetTransactionAccountID) {
        this.targetTransactionAccountID = targetTransactionAccountID;
    }

    public String getTargetRegionCode() {
        return targetRegionCode;
    }

    public void setTargetRegionCode(String targetRegionCode) {
        this.targetRegionCode = targetRegionCode;
    }

    public String getTransferDirection() {
        return transferDirection;
    }

    public void setTransferDirection(String transferDirection) {
        this.transferDirection = transferDirection;
    }

    public String getDefDividendMethod() {
        return defDividendMethod;
    }

    public void setDefDividendMethod(String defDividendMethod) {
        this.defDividendMethod = defDividendMethod;
    }

    public BigDecimal getDividendRatio() {
        return dividendRatio;
    }

    public void setDividendRatio(BigDecimal dividendRatio) {
        this.dividendRatio = dividendRatio;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getVolumeByInterest() {
        return volumeByInterest;
    }

    public void setVolumeByInterest(BigDecimal volumeByInterest) {
        this.volumeByInterest = volumeByInterest;
    }

    public BigDecimal getInterestTax() {
        return interestTax;
    }

    public void setInterestTax(BigDecimal interestTax) {
        this.interestTax = interestTax;
    }

    public BigDecimal getTradingPrice() {
        return tradingPrice;
    }

    public void setTradingPrice(BigDecimal tradingPrice) {
        this.tradingPrice = tradingPrice;
    }

    public String getFreezingDeadline() {
        return freezingDeadline;
    }

    public void setFreezingDeadline(String freezingDeadline) {
        this.freezingDeadline = freezingDeadline;
    }

    public String getFrozenCause() {
        return frozenCause;
    }

    public void setFrozenCause(String frozenCause) {
        this.frozenCause = frozenCause;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTargetNAV() {
        return targetNAV;
    }

    public void setTargetNAV(BigDecimal targetNAV) {
        this.targetNAV = targetNAV;
    }

    public BigDecimal getTargetFundPrice() {
        return targetFundPrice;
    }

    public void setTargetFundPrice(BigDecimal targetFundPrice) {
        this.targetFundPrice = targetFundPrice;
    }

    public BigDecimal getCfmVolOfTargetFund() {
        return cfmVolOfTargetFund;
    }

    public void setCfmVolOfTargetFund(BigDecimal cfmVolOfTargetFund) {
        this.cfmVolOfTargetFund = cfmVolOfTargetFund;
    }

    public BigDecimal getMinFee() {
        return minFee;
    }

    public void setMinFee(BigDecimal minFee) {
        this.minFee = minFee;
    }

    public BigDecimal getOtherFee2() {
        return otherFee2;
    }

    public void setOtherFee2(BigDecimal otherFee2) {
        this.otherFee2 = otherFee2;
    }

    public String getOriginalAppDate() {
        return originalAppDate;
    }

    public void setOriginalAppDate(String originalAppDate) {
        this.originalAppDate = originalAppDate;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public String getFromTAFlag() {
        return fromTAFlag;
    }

    public void setFromTAFlag(String fromTAFlag) {
        this.fromTAFlag = fromTAFlag;
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

    public String getRedemptionInAdvanceFlag() {
        return redemptionInAdvanceFlag;
    }

    public void setRedemptionInAdvanceFlag(String redemptionInAdvanceFlag) {
        this.redemptionInAdvanceFlag = redemptionInAdvanceFlag;
    }

    public String getFrozenMethod() {
        return frozenMethod;
    }

    public void setFrozenMethod(String frozenMethod) {
        this.frozenMethod = frozenMethod;
    }

    public String getOriginalCfmDate() {
        return originalCfmDate;
    }

    public void setOriginalCfmDate(String originalCfmDate) {
        this.originalCfmDate = originalCfmDate;
    }

    public String getRedemptionReason() {
        return redemptionReason;
    }

    public void setRedemptionReason(String redemptionReason) {
        this.redemptionReason = redemptionReason;
    }

    public String getCodeOfTargetFund() {
        return codeOfTargetFund;
    }

    public void setCodeOfTargetFund(String codeOfTargetFund) {
        this.codeOfTargetFund = codeOfTargetFund;
    }

    public BigDecimal getTotalTransFee() {
        return totalTransFee;
    }

    public void setTotalTransFee(BigDecimal totalTransFee) {
        this.totalTransFee = totalTransFee;
    }

    public String getVarietyCodeOfPeriodicSubs() {
        return varietyCodeOfPeriodicSubs;
    }

    public void setVarietyCodeOfPeriodicSubs(String varietyCodeOfPeriodicSubs) {
        this.varietyCodeOfPeriodicSubs = varietyCodeOfPeriodicSubs;
    }

    public String getSerialNoOfPeriodicSubs() {
        return serialNoOfPeriodicSubs;
    }

    public void setSerialNoOfPeriodicSubs(String serialNoOfPeriodicSubs) {
        this.serialNoOfPeriodicSubs = serialNoOfPeriodicSubs;
    }

    public String getRationType() {
        return rationType;
    }

    public void setRationType(String rationType) {
        this.rationType = rationType;
    }

    public String getTargetTAAccountID() {
        return targetTAAccountID;
    }

    public void setTargetTAAccountID(String targetTAAccountID) {
        this.targetTAAccountID = targetTAAccountID;
    }

    public String getTargetRegistrarCode() {
        return targetRegistrarCode;
    }

    public void setTargetRegistrarCode(String targetRegistrarCode) {
        this.targetRegistrarCode = targetRegistrarCode;
    }

    public String getNetNo() {
        return netNo;
    }

    public void setNetNo(String netNo) {
        this.netNo = netNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getTargetShareType() {
        return targetShareType;
    }

    public void setTargetShareType(String targetShareType) {
        this.targetShareType = targetShareType;
    }

    public String getRationProtocolNo() {
        return rationProtocolNo;
    }

    public void setRationProtocolNo(String rationProtocolNo) {
        this.rationProtocolNo = rationProtocolNo;
    }

    public String getBeginDateOfPeriodicSubs() {
        return beginDateOfPeriodicSubs;
    }

    public void setBeginDateOfPeriodicSubs(String beginDateOfPeriodicSubs) {
        this.beginDateOfPeriodicSubs = beginDateOfPeriodicSubs;
    }

    public String getEndDateOfPeriodicSubs() {
        return endDateOfPeriodicSubs;
    }

    public void setEndDateOfPeriodicSubs(String endDateOfPeriodicSubs) {
        this.endDateOfPeriodicSubs = endDateOfPeriodicSubs;
    }

    public BigDecimal getSendDayOfPeriodicSubs() {
        return sendDayOfPeriodicSubs;
    }

    public void setSendDayOfPeriodicSubs(BigDecimal sendDayOfPeriodicSubs) {
        this.sendDayOfPeriodicSubs = sendDayOfPeriodicSubs;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getSalesPromotion() {
        return salesPromotion;
    }

    public void setSalesPromotion(String salesPromotion) {
        this.salesPromotion = salesPromotion;
    }

    public String getAcceptMethod() {
        return acceptMethod;
    }

    public void setAcceptMethod(String acceptMethod) {
        this.acceptMethod = acceptMethod;
    }

    public String getForceRedemptionType() {
        return forceRedemptionType;
    }

    public void setForceRedemptionType(String forceRedemptionType) {
        this.forceRedemptionType = forceRedemptionType;
    }

    public String getAlternationDate() {
        return alternationDate;
    }

    public void setAlternationDate(String alternationDate) {
        this.alternationDate = alternationDate;
    }

    public String getTakeIncomeFlag() {
        return takeIncomeFlag;
    }

    public void setTakeIncomeFlag(String takeIncomeFlag) {
        this.takeIncomeFlag = takeIncomeFlag;
    }

    public String getPurposeOfPeSubs() {
        return purposeOfPeSubs;
    }

    public void setPurposeOfPeSubs(String purposeOfPeSubs) {
        this.purposeOfPeSubs = purposeOfPeSubs;
    }

    public BigDecimal getFrequencyOfPeSubs() {
        return frequencyOfPeSubs;
    }

    public void setFrequencyOfPeSubs(BigDecimal frequencyOfPeSubs) {
        this.frequencyOfPeSubs = frequencyOfPeSubs;
    }

    public String getPeriodSubTimeUnit() {
        return periodSubTimeUnit;
    }

    public void setPeriodSubTimeUnit(String periodSubTimeUnit) {
        this.periodSubTimeUnit = periodSubTimeUnit;
    }

    public BigDecimal getBatchNumOfPeSubs() {
        return batchNumOfPeSubs;
    }

    public void setBatchNumOfPeSubs(BigDecimal batchNumOfPeSubs) {
        this.batchNumOfPeSubs = batchNumOfPeSubs;
    }

    public String getCapitalMode() {
        return capitalMode;
    }

    public void setCapitalMode(String capitalMode) {
        this.capitalMode = capitalMode;
    }

    public String getDetailCapticalMode() {
        return detailCapticalMode;
    }

    public void setDetailCapticalMode(String detailCapticalMode) {
        this.detailCapticalMode = detailCapticalMode;
    }

    public BigDecimal getBackenloadDiscount() {
        return backenloadDiscount;
    }

    public void setBackenloadDiscount(BigDecimal backenloadDiscount) {
        this.backenloadDiscount = backenloadDiscount;
    }

    public String getCombineNum() {
        return combineNum;
    }

    public void setCombineNum(String combineNum) {
        this.combineNum = combineNum;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(BigDecimal salePercent) {
        this.salePercent = salePercent;
    }

    public BigDecimal getManagerRealRatio() {
        return managerRealRatio;
    }

    public void setManagerRealRatio(BigDecimal managerRealRatio) {
        this.managerRealRatio = managerRealRatio;
    }

    public BigDecimal getChangeFee() {
        return changeFee;
    }

    public void setChangeFee(BigDecimal changeFee) {
        this.changeFee = changeFee;
    }

    public BigDecimal getRecuperateFee() {
        return recuperateFee;
    }

    public void setRecuperateFee(BigDecimal recuperateFee) {
        this.recuperateFee = recuperateFee;
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

    public String getSharesAdjustmentFlag() {
        return sharesAdjustmentFlag;
    }

    public void setSharesAdjustmentFlag(String sharesAdjustmentFlag) {
        this.sharesAdjustmentFlag = sharesAdjustmentFlag;
    }

    public String getGeneralTASerialNO() {
        return generalTASerialNO;
    }

    public void setGeneralTASerialNO(String generalTASerialNO) {
        this.generalTASerialNO = generalTASerialNO;
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

    public BigDecimal getBreachFee() {
        return breachFee;
    }

    public void setBreachFee(BigDecimal breachFee) {
        this.breachFee = breachFee;
    }

    public BigDecimal getBreachFeeBackToFund() {
        return breachFeeBackToFund;
    }

    public void setBreachFeeBackToFund(BigDecimal breachFeeBackToFund) {
        this.breachFeeBackToFund = breachFeeBackToFund;
    }

    public BigDecimal getPunishFee() {
        return punishFee;
    }

    public void setPunishFee(BigDecimal punishFee) {
        this.punishFee = punishFee;
    }

    public String getTradingMethod() {
        return tradingMethod;
    }

    public void setTradingMethod(String tradingMethod) {
        this.tradingMethod = tradingMethod;
    }

    public BigDecimal getChangeAgencyFee() {
        return changeAgencyFee;
    }

    public void setChangeAgencyFee(BigDecimal changeAgencyFee) {
        this.changeAgencyFee = changeAgencyFee;
    }

    public BigDecimal getRecuperateAgencyFee() {
        return recuperateAgencyFee;
    }

    public void setRecuperateAgencyFee(BigDecimal recuperateAgencyFee) {
        this.recuperateAgencyFee = recuperateAgencyFee;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getLargeBuyFlag() {
        return largeBuyFlag;
    }

    public void setLargeBuyFlag(String largeBuyFlag) {
        this.largeBuyFlag = largeBuyFlag;
    }

    public BigDecimal getRaiseInterest() {
        return raiseInterest;
    }

    public void setRaiseInterest(BigDecimal raiseInterest) {
        this.raiseInterest = raiseInterest;
    }

    public String getFeeCalculator() {
        return feeCalculator;
    }

    public void setFeeCalculator(String feeCalculator) {
        this.feeCalculator = feeCalculator;
    }

    public String getShareRegisterDate() {
        return shareRegisterDate;
    }

    public void setShareRegisterDate(String shareRegisterDate) {
        this.shareRegisterDate = shareRegisterDate;
    }

    public String toString() {
        return
                        "appSheetSerialNo: " + appSheetSerialNo + ", " +
                        "transactionCfmDate: " + transactionCfmDate + ", " +
                        "currencyType: " + currencyType + ", " +
                        "confirmedVol: " + confirmedVol + ", " +
                        "confirmedAmount: " + confirmedAmount + ", " +
                        "fundCode: " + fundCode + ", " +
                        "largeRedemptionFlag: " + largeRedemptionFlag + ", " +
                        "transactionDate: " + transactionDate + ", " +
                        "transactionTime: " + transactionTime + ", " +
                        "returnCode: " + returnCode + ", " +
                        "transactionAccountID: " + transactionAccountID + ", " +
                        "distributorCode: " + distributorCode + ", " +
                        "applicationVol: " + applicationVol + ", " +
                        "applicationAmount: " + applicationAmount + ", " +
                        "businessCode: " + businessCode + ", " +
                        "tAAccountID: " + tAAccountID + ", " +
                        "tASerialNO: " + tASerialNO + ", " +
                        "businessFinishFlag: " + businessFinishFlag + ", " +
                        "discountRateOfCommission: " + discountRateOfCommission + ", " +
                        "depositAcct: " + depositAcct + ", " +
                        "regionCode: " + regionCode + ", " +
                        "downLoaddate: " + downLoaddate + ", " +
                        "charge: " + charge + ", " +
                        "agencyFee: " + agencyFee + ", " +
                        "nAV: " + nAV + ", " +
                        "branchCode: " + branchCode + ", " +
                        "originalAppSheetNo: " + originalAppSheetNo + ", " +
                        "originalSubsDate: " + originalSubsDate + ", " +
                        "otherFee1: " + otherFee1 + ", " +
                        "individualOrInstitution: " + individualOrInstitution + ", " +
                        "redemptionDateInAdvance: " + redemptionDateInAdvance + ", " +
                        "stampDuty: " + stampDuty + ", " +
                        "validPeriod: " + validPeriod + ", " +
                        "rateFee: " + rateFee + ", " +
                        "totalBackendLoad: " + totalBackendLoad + ", " +
                        "originalSerialNo: " + originalSerialNo + ", " +
                        "specification: " + specification + ", " +
                        "dateOfPeriodicSubs: " + dateOfPeriodicSubs + ", " +
                        "targetDistributorCode: " + targetDistributorCode + ", " +
                        "targetBranchCode: " + targetBranchCode + ", " +
                        "targetTransactionAccountID: " + targetTransactionAccountID + ", " +
                        "targetRegionCode: " + targetRegionCode + ", " +
                        "transferDirection: " + transferDirection + ", " +
                        "defDividendMethod: " + defDividendMethod + ", " +
                        "dividendRatio: " + dividendRatio + ", " +
                        "interest: " + interest + ", " +
                        "volumeByInterest: " + volumeByInterest + ", " +
                        "interestTax: " + interestTax + ", " +
                        "tradingPrice: " + tradingPrice + ", " +
                        "freezingDeadline: " + freezingDeadline + ", " +
                        "frozenCause: " + frozenCause + ", " +
                        "tax: " + tax + ", " +
                        "targetNAV: " + targetNAV + ", " +
                        "targetFundPrice: " + targetFundPrice + ", " +
                        "cfmVolOfTargetFund: " + cfmVolOfTargetFund + ", " +
                        "minFee: " + minFee + ", " +
                        "otherFee2: " + otherFee2 + ", " +
                        "originalAppDate: " + originalAppDate + ", " +
                        "transferFee: " + transferFee + ", " +
                        "fromTAFlag: " + fromTAFlag + ", " +
                        "shareClass: " + shareClass + ", " +
                        "detailFlag: " + detailFlag + ", " +
                        "redemptionInAdvanceFlag: " + redemptionInAdvanceFlag + ", " +
                        "frozenMethod: " + frozenMethod + ", " +
                        "originalCfmDate: " + originalCfmDate + ", " +
                        "redemptionReason: " + redemptionReason + ", " +
                        "codeOfTargetFund: " + codeOfTargetFund + ", " +
                        "totalTransFee: " + totalTransFee + ", " +
                        "varietyCodeOfPeriodicSubs: " + varietyCodeOfPeriodicSubs + ", " +
                        "serialNoOfPeriodicSubs: " + serialNoOfPeriodicSubs + ", " +
                        "rationType: " + rationType + ", " +
                        "targetTAAccountID: " + targetTAAccountID + ", " +
                        "targetRegistrarCode: " + targetRegistrarCode + ", " +
                        "netNo: " + netNo + ", " +
                        "customerNo: " + customerNo + ", " +
                        "targetShareType: " + targetShareType + ", " +
                        "rationProtocolNo: " + rationProtocolNo + ", " +
                        "beginDateOfPeriodicSubs: " + beginDateOfPeriodicSubs + ", " +
                        "endDateOfPeriodicSubs: " + endDateOfPeriodicSubs + ", " +
                        "sendDayOfPeriodicSubs: " + sendDayOfPeriodicSubs + ", " +
                        "broker: " + broker + ", " +
                        "salesPromotion: " + salesPromotion + ", " +
                        "acceptMethod: " + acceptMethod + ", " +
                        "forceRedemptionType: " + forceRedemptionType + ", " +
                        "alternationDate: " + alternationDate + ", " +
                        "takeIncomeFlag: " + takeIncomeFlag + ", " +
                        "purposeOfPeSubs: " + purposeOfPeSubs + ", " +
                        "frequencyOfPeSubs: " + frequencyOfPeSubs + ", " +
                        "periodSubTimeUnit: " + periodSubTimeUnit + ", " +
                        "batchNumOfPeSubs: " + batchNumOfPeSubs + ", " +
                        "capitalMode: " + capitalMode + ", " +
                        "detailCapticalMode: " + detailCapticalMode + ", " +
                        "backenloadDiscount: " + backenloadDiscount + ", " +
                        "combineNum: " + combineNum + ", " +
                        "refundAmount: " + refundAmount + ", " +
                        "salePercent: " + salePercent + ", " +
                        "managerRealRatio: " + managerRealRatio + ", " +
                        "changeFee: " + changeFee + ", " +
                        "recuperateFee: " + recuperateFee + ", " +
                        "achievementPay: " + achievementPay + ", " +
                        "achievementCompen: " + achievementCompen + ", " +
                        "sharesAdjustmentFlag: " + sharesAdjustmentFlag + ", " +
                        "generalTASerialNO: " + generalTASerialNO + ", " +
                        "undistributeMonetaryIncome: " + undistributeMonetaryIncome + ", " +
                        "undistributeMonetaryIncomeFlag: " + undistributeMonetaryIncomeFlag + ", " +
                        "breachFee: " + breachFee + ", " +
                        "breachFeeBackToFund: " + breachFeeBackToFund + ", " +
                        "punishFee: " + punishFee + ", " +
                        "tradingMethod: " + tradingMethod + ", " +
                        "changeAgencyFee: " + changeAgencyFee + ", " +
                        "recuperateAgencyFee: " + recuperateAgencyFee + ", " +
                        "errorDetail: " + errorDetail + ", " +
                        "largeBuyFlag: " + largeBuyFlag + ", " +
                        "raiseInterest: " + raiseInterest + ", " +
                        "feeCalculator: " + feeCalculator + ", " +
                        "shareRegisterDate: " + shareRegisterDate ;
    }

}


