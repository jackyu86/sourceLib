package com.caej.batch.writer;

import com.caej.batch.model.BufAbonus;
import com.caej.batch.model.BufAccountRecon;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Writes products to a database
 */
public class AccountReconWriter implements ItemWriter<BufAccountRecon>
{
    private static final String GET_PRODUCT = "select * from PRODUCT where id = ?";
    private static final String INSERT_PRODUCT = "insert into buf_account_recon(availableVol,totalVolOfDistributorInTA,transactionCfmDate,fundCode,transactionAccountID,distributorCode,tAAccountID,totalFrozenVol,branchCode,tASerialNO,totalBackendLoad,shareClass,detailFlag,accountStatus,shareRegisterDate,undistributeMonetaryIncome,undistributeMonetaryIncomeFlag,guaranteedAmount,sourceType,defDividendMethod) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "update PRODUCT set name = ?, description = ?,quantity = ? where id = ?";

    // insert SQL:

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends BufAccountRecon> bufAccountRecon) throws Exception
    {
        for( BufAccountRecon record : bufAccountRecon )
        {

                jdbcTemplate.update( INSERT_PRODUCT, record.getAvailableVol(),record.getTotalVolOfDistributorInTA(),record.getTransactionCfmDate(),record.getFundCode(),record.getTransactionAccountID(),record.getDistributorCode(),record.gettAAccountID(),record.getTotalFrozenVol(),record.getBranchCode(),record.gettASerialNO(),record.getTotalBackendLoad(),record.getShareClass(),record.getDetailFlag(),record.getAccountStatus(),record.getShareRegisterDate(),record.getUndistributeMonetaryIncome(),record.getUndistributeMonetaryIncomeFlag(),record.getGuaranteedAmount(),record.getSourceType(),record.getDefDividendMethod());
        }
    }
}
