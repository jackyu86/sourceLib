package com.caej.batch.writer;

import com.caej.batch.model.BufAccountConfirm;
import com.caej.batch.model.BufTransactionConfirm;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Writes products to a database
 */
public class TransactionConfirmWriter implements ItemWriter<BufTransactionConfirm>
{
    private static final String GET_PRODUCT = "select * from PRODUCT where id = ?";
    private static final String INSERT_PRODUCT = "insert into buf_transaction_confirm(appSheetSerialNo,transactionCfmDate,currencyType,confirmedVol,confirmedAmount,fundCode,largeRedemptionFlag,transactionDate,transactionTime,returnCode,transactionAccountID,distributorCode,applicationVol,applicationAmount,businessCode,tAAccountID,tASerialNO,businessFinishFlag,discountRateOfCommission,depositAcct,regionCode,downLoaddate,charge,agencyFee,nAV,branchCode,originalAppSheetNo,originalSubsDate,otherFee1,individualOrInstitution,redemptionDateInAdvance,stampDuty,validPeriod,rateFee,totalBackendLoad,originalSerialNo,specification,dateOfPeriodicSubs,targetDistributorCode,targetBranchCode,targetTransactionAccountID,targetRegionCode,transferDirection,defDividendMethod,dividendRatio,interest,volumeByInterest,interestTax,tradingPrice,freezingDeadline,frozenCause,tax,targetNAV,targetFundPrice,cfmVolOfTargetFund,minFee,otherFee2,originalAppDate,transferFee,fromTAFlag,shareClass,detailFlag,redemptionInAdvanceFlag,frozenMethod,originalCfmDate,redemptionReason,codeOfTargetFund,totalTransFee,varietyCodeOfPeriodicSubs,serialNoOfPeriodicSubs,rationType,targetTAAccountID,targetRegistrarCode,netNo,customerNo,targetShareType,rationProtocolNo,beginDateOfPeriodicSubs,endDateOfPeriodicSubs,sendDayOfPeriodicSubs,broker,salesPromotion,acceptMethod,forceRedemptionType,alternationDate,takeIncomeFlag,purposeOfPeSubs,frequencyOfPeSubs,periodSubTimeUnit,batchNumOfPeSubs,capitalMode,detailCapticalMode,backenloadDiscount,combineNum,refundAmount,salePercent,managerRealRatio,changeFee,recuperateFee,achievementPay,achievementCompen,sharesAdjustmentFlag,generalTASerialNO,undistributeMonetaryIncome,undistributeMonetaryIncomeFlag,breachFee,breachFeeBackToFund,punishFee,tradingMethod,changeAgencyFee,recuperateAgencyFee,errorDetail,largeBuyFlag,raiseInterest,feeCalculator,shareRegisterDate) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "update PRODUCT set name = ?, description = ?,quantity = ? where id = ?";

    // insert SQL:

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends BufTransactionConfirm> bufTransactionConfirm) throws Exception
    {
        int i = 100;
        for( BufTransactionConfirm record : bufTransactionConfirm )
        {
            i++;
//            List<Product> productList = jdbcTemplate.query(GET_PRODUCT, new Object[] {product.getId()}, new RowMapper<Product>() {
//                @Override
//                public Product mapRow( ResultSet resultSet, int rowNum ) throws SQLException {
//                    Product p = new Product();
//                    p.setId( resultSet.getInt( 1 ) );
//                    p.setName( resultSet.getString( 2 ) );
//                    p.setDescription( resultSet.getString( 3 ) );
//                    p.setQuantity( resultSet.getInt( 4 ) );
//                    return p;
//                }
//            });
//
//            if( productList.size() > 0 )
//            {
//                jdbcTemplate.update( UPDATE_PRODUCT, product.getName(), product.getDescription(), product.getQuantity(), product.getId() );
//            }
//            else
//            {
                jdbcTemplate.update( INSERT_PRODUCT,
                        record.getAppSheetSerialNo(),record.getTransactionCfmDate(),record.getCurrencyType(),record.getConfirmedVol(),record.getConfirmedAmount(),record.getFundCode(),record.getLargeRedemptionFlag(),record.getTransactionDate(),record.getTransactionTime(),record.getReturnCode(),record.getTransactionAccountID(),record.getDistributorCode(),record.getApplicationVol(),record.getApplicationAmount(),record.getBusinessCode(),record.gettAAccountID(),record.gettASerialNO(),record.getBusinessFinishFlag(),record.getDiscountRateOfCommission(),record.getDepositAcct(),record.getRegionCode(),record.getDownLoaddate(),record.getCharge(),record.getAgencyFee(),record.getnAV(),record.getBranchCode(),record.getOriginalAppSheetNo(),record.getOriginalSubsDate(),record.getOtherFee1(),record.getIndividualOrInstitution(),record.getRedemptionDateInAdvance(),record.getStampDuty(),record.getValidPeriod(),record.getRateFee(),record.getTotalBackendLoad(),record.getOriginalSerialNo(),record.getSpecification(),record.getDateOfPeriodicSubs(),record.getTargetDistributorCode(),record.getTargetBranchCode(),record.getTargetTransactionAccountID(),record.getTargetRegionCode(),record.getTransferDirection(),record.getDefDividendMethod(),record.getDividendRatio(),record.getInterest(),record.getVolumeByInterest(),record.getInterestTax(),record.getTradingPrice(),record.getFreezingDeadline(),record.getFrozenCause(),record.getTax(),record.getTargetNAV(),record.getTargetFundPrice(),record.getCfmVolOfTargetFund(),record.getMinFee(),record.getOtherFee2(),record.getOriginalAppDate(),record.getTransferFee(),record.getFromTAFlag(),record.getShareClass(),record.getDetailFlag(),record.getRedemptionInAdvanceFlag(),record.getFrozenMethod(),record.getOriginalCfmDate(),record.getRedemptionReason(),record.getCodeOfTargetFund(),record.getTotalTransFee(),record.getVarietyCodeOfPeriodicSubs(),record.getSerialNoOfPeriodicSubs(),record.getRationType(),record.getTargetTAAccountID(),record.getTargetRegistrarCode(),record.getNetNo(),record.getCustomerNo(),record.getTargetShareType(),record.getRationProtocolNo(),record.getBeginDateOfPeriodicSubs(),record.getEndDateOfPeriodicSubs(),record.getSendDayOfPeriodicSubs(),record.getBroker(),record.getSalesPromotion(),record.getAcceptMethod(),record.getForceRedemptionType(),record.getAlternationDate(),record.getTakeIncomeFlag(),record.getPurposeOfPeSubs(),record.getFrequencyOfPeSubs(),record.getPeriodSubTimeUnit(),record.getBatchNumOfPeSubs(),record.getCapitalMode(),record.getDetailCapticalMode(),record.getBackenloadDiscount(),record.getCombineNum(),record.getRefundAmount(),record.getSalePercent(),record.getManagerRealRatio(),record.getChangeFee(),record.getRecuperateFee(),record.getAchievementPay(),record.getAchievementCompen(),record.getSharesAdjustmentFlag(),record.getGeneralTASerialNO(),record.getUndistributeMonetaryIncome(),record.getUndistributeMonetaryIncomeFlag(),record.getBreachFee(),record.getBreachFeeBackToFund(),record.getPunishFee(),record.getTradingMethod(),record.getChangeAgencyFee(),record.getRecuperateAgencyFee(),record.getErrorDetail(),record.getLargeBuyFlag(),record.getRaiseInterest(),record.getFeeCalculator(),record.getShareRegisterDate());
 //           }
        }
    }
}
