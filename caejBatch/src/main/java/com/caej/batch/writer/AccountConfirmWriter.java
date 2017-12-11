package com.caej.batch.writer;

import com.caej.batch.model.BufAccountConfirm;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Writes products to a database
 */
public class AccountConfirmWriter implements ItemWriter<BufAccountConfirm>
{
    private static final String GET_PRODUCT = "select * from PRODUCT where id = ?";
    private static final String INSERT_PRODUCT = "insert into buf_account_confirm(appSheetSerialNo,transactionCfmDate,returnCode,transactionAccountID,distributorCode,businessCode,tAAccountID,multiAcctFlag,tASerialNO,transactionDate,transactionTime,branchCode,fromTAFlag,certificateType,certificateNo,investorName,individualOrInstitution,accountAbbr,accountCardID,regionCode,targetTransactionAccountID,netNo,specification,customerNo,frozenCause,freezingDeadline,errorDetail) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "update PRODUCT set name = ?, description = ?,quantity = ? where id = ?";

    // insert SQL:

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends BufAccountConfirm> bufAccountConfirms) throws Exception
    {
        int i = 100;
        for( BufAccountConfirm record : bufAccountConfirms )
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
                        record.getAppSheetSerialNo(),record.getTransactionCfmDate(),record.getReturnCode(),record.getTransactionAccountID(),record.getDistributorCode(),record.getBusinessCode(),record.getTAAccountID(),record.getMultiAcctFlag(),record.getTASerialNO(),record.getTransactionDate(),record.getTransactionTime(),record.getBranchCode(),record.getFromTAFlag(),record.getCertificateType(),record.getCertificateNo(),record.getInvestorName(),record.getIndividualOrInstitution(),record.getAccountAbbr(),record.getAccountCardID(),record.getRegionCode(),record.getTargetTransactionAccountID(),record.getNetNo(),record.getSpecification(),record.getCustomerNo(),record.getFrozenCause(),record.getFreezingDeadline(),record.getErrorDetail() );
 //           }
        }
    }
}
