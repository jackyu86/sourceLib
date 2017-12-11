package com.caej.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * A tasklet that logs product information
 */
public class SyncAssetTasklet implements Tasklet
{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception
    {
        jdbcTemplate.update("update buf_account_recon buf,t_assets_synchro t "
                + "set t.account_income=buf.GuaranteedAmount,t.account_value=buf.TotalVolOfDistributorInTA,"
                + "t.accumulated_Income=(ifnull(t.accumulated_Income,0)+buf.TotalVolOfDistributorInTA), "
                +"t.modify_date = ? ,t.update_time= ? "
                + "where t.App_Sheet_Serial_No=buf.TotalFrozenVol",new Date(),new Date());
        // insert SQL:

        // We're done...
        return RepeatStatus.FINISHED;
    }




    public static void main(String[] args) {
        try {
            SyncAssetTasklet task =  new SyncAssetTasklet();
            task.execute(null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

