package com.caej.batch.writer;

import com.caej.batch.model.BufAbonus;
import com.caej.batch.model.BufAccountConfirm;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Writes products to a database
 */
public class AbonusWriter implements ItemWriter<BufAbonus>
{
    private static final String GET_PRODUCT = "select * from PRODUCT where id = ?";
    private static final String INSERT_PRODUCT = "insert into buf_abonus(basisforCalculatingDividend,transactionCfmDate,currencyType,volOfDividendforReinvestment,dividentDate,dividendAmount,xRDate,confirmedAmount,fundCode,registrationDate,returnCode,transactionAccountID,distributorCode,businessCode,tAAccountID,dividendPerUnit,defDividendMethod,depositAcct,regionCode,downLoaddate,charge,agencyFee,totalFrozenVol,nAV,branchCode,otherFee1,otherFee2,individualOrInstitution,dividendRatio,tASerialNO,stampDuty,frozenBalance,transferFee,shareClass,feeCalculator,drawBonusUnit,frozenSharesforReinvest,dividendType,originalAppSheetNo,achievementPay,achievementCompen) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "update PRODUCT set name = ?, description = ?,quantity = ? where id = ?";

    // insert SQL:

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends BufAbonus> bufAbonus) throws Exception
    {
        for( BufAbonus record : bufAbonus )
        {

                jdbcTemplate.update( INSERT_PRODUCT, record.getBasisforCalculatingDividend(),
                        record.getTransactionCfmDate(),record.getCurrencyType(),record.getVolOfDividendforReinvestment(),record.getDividentDate(),record.getDividendAmount(),record.getXRDate(),record.getConfirmedAmount(),record.getFundCode(),record.getRegistrationDate(),record.getReturnCode(),record.getTransactionAccountID(),record.getDistributorCode(),record.getBusinessCode(),record.gettAAccountID(),record.getDividendPerUnit(),record.getDefDividendMethod(),record.getDepositAcct(),record.getRegionCode(),record.getDownLoaddate(),record.getCharge(),record.getAgencyFee(),record.getTotalFrozenVol(),record.getnAV(),record.getBranchCode(),record.getOtherFee1(),record.getOtherFee2(),record.getIndividualOrInstitution(),record.getDividendRatio(),record.gettASerialNO(),record.getStampDuty(),record.getFrozenBalance(),record.getTransferFee(),record.getShareClass(),record.getFeeCalculator(),record.getDrawBonusUnit(),record.getFrozenSharesforReinvest(),record.getDividendType(),record.getOriginalAppSheetNo(),record.getAchievementPay(),record.getAchievementCompen());
        }
    }
}
