package com.caej.batch.writer;

import com.caej.batch.model.BufAbonus;
import com.caej.batch.model.BufFundDynamicInfo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Writes products to a database
 */
public class FundDynamicInfoWriter implements ItemWriter<BufFundDynamicInfo>
{
    private static final String GET_PRODUCT = "select * from PRODUCT where id = ?";
    private static final String INSERT_PRODUCT = "insert into buf_fund_dynamic_info(fundName,totalFundVol,fundCode,fundStatus,nAV,updateDate,netValueType,accumulativeNAV,convertStatus,periodicStatus,transferAgencyStatus,fundSize,currencyType,announcFlag,defDividendMethod,instAppSubsAmnt,instAppSubsVol,minAmountByInst,minVolByInst,custodianCode,amountOfPeriodicSubs,dateOfPeriodicSubs,maxRedemptionVol,minAccountBalance,iPOStartDate,iPOEndDate,fundManagerCode,indiAppSubsVol,indiAppSubsAmount,minSubsVolByIndi,minSubsAmountByIndi,registrarCode,fundSponsor,tradingPrice,faceValue,dividentDate,registrationDate,xRDate,maxSubsVolByIndi,maxSubsAmountByIndi,maxSubsVolByInst,maxSubsAmountByInst,unitSubsVolByIndi,unitSubsAmountByIndi,unitSubsVolByInst,unitSubsAmountByInst,minBidsAmountByIndi,minBidsAmountByInst,minAppBidsAmountByIndi,minAppBidsAmountByInst,minRedemptionVol,minInterconvertVol,issueTypeByIndi,issueTypeByInst,subsType,collectFeeType,nextTradeDate,valueLine,totalDivident,fundIncome,fundIncomeFlag,yield,yieldFlag,guaranteedNAV,fundYearIncomeRate,fundYearIncomeRateFlag,indiMaxPurchase,instMaxPurchase,indiDayMaxSumBuy,instDayMaxSumBuy,indiDayMaxSumRedeem,instDayMaxSumRedeem,indiMaxRedeem,instMaxRedeem,fundDayIncomeFlag,fundDayIncome,allowBreachRedempt,fundType,fundTypeName,registrarName,fundManagerName,fundServerTel,fundInternetAddress) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "update PRODUCT set name = ?, description = ?,quantity = ? where id = ?";

    // insert SQL:

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends BufFundDynamicInfo> fundDynamicInfo) throws Exception
    {
        for( BufFundDynamicInfo record : fundDynamicInfo )
        {
                jdbcTemplate.update( INSERT_PRODUCT, record.getFundName(),record.getTotalFundVol(),record.getFundCode(),record.getFundStatus(),record.getnAV(),record.getUpdateDate(),record.getNetValueType(),record.getAccumulativeNAV(),record.getConvertStatus(),record.getPeriodicStatus(),record.getTransferAgencyStatus(),record.getFundSize(),record.getCurrencyType(),record.getAnnouncFlag(),record.getDefDividendMethod(),record.getInstAppSubsAmnt(),record.getInstAppSubsVol(),record.getMinAmountByInst(),record.getMinVolByInst(),record.getCustodianCode(),record.getAmountOfPeriodicSubs(),record.getDateOfPeriodicSubs(),record.getMaxRedemptionVol(),record.getMinAccountBalance(),record.getiPOStartDate(),record.getiPOEndDate(),record.getFundManagerCode(),record.getIndiAppSubsVol(),record.getIndiAppSubsAmount(),record.getMinSubsVolByIndi(),record.getMinSubsAmountByIndi(),record.getRegistrarCode(),record.getFundSponsor(),record.getTradingPrice(),record.getFaceValue(),record.getDividentDate(),record.getRegistrationDate(),record.getxRDate(),record.getMaxSubsVolByIndi(),record.getMaxSubsAmountByIndi(),record.getMaxSubsVolByInst(),record.getMaxSubsAmountByInst(),record.getUnitSubsVolByIndi(),record.getUnitSubsAmountByIndi(),record.getUnitSubsVolByInst(),record.getUnitSubsAmountByInst(),record.getMinBidsAmountByIndi(),record.getMinBidsAmountByInst(),record.getMinAppBidsAmountByIndi(),record.getMinAppBidsAmountByInst(),record.getMinRedemptionVol(),record.getMinInterconvertVol(),record.getIssueTypeByIndi(),record.getIssueTypeByInst(),record.getSubsType(),record.getCollectFeeType(),record.getNextTradeDate(),record.getValueLine(),record.getTotalDivident(),record.getFundIncome(),record.getFundIncomeFlag(),record.getYield(),record.getYieldFlag(),record.getGuaranteedNAV(),record.getFundYearIncomeRate(),record.getFundYearIncomeRateFlag(),record.getIndiMaxPurchase(),record.getInstMaxPurchase(),record.getIndiDayMaxSumBuy(),record.getInstDayMaxSumBuy(),record.getIndiDayMaxSumRedeem(),record.getInstDayMaxSumRedeem(),record.getIndiMaxRedeem(),record.getInstMaxRedeem(),record.getFundDayIncomeFlag(),record.getFundDayIncome(),record.getAllowBreachRedempt(),record.getFundType(),record.getFundTypeName(),record.getRegistrarName(),record.getFundManagerName(),record.getFundServerTel(),record.getFundInternetAddress());
        }
    }
}
