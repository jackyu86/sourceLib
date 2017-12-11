package com.caej.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;

/**
 * A tasklet that logs product information
 */
public class SyncAccountTasklet implements Tasklet
{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception
    {
        jdbcTemplate.update("update buf_account_confirm buf,t_assets_synchro t "
                + "set t.transaction_Account_Id=buf.taaccountId,t.fund_Code=buf.AccountCardID "
                + "where t.App_Sheet_Serial_No=buf.AppSheetSerialNo", (Object) null);
        // insert SQL:

        // We're done...
        return RepeatStatus.FINISHED;
    }




    public static void main(String[] args) {
        try {
            SyncAccountTasklet task =  new SyncAccountTasklet();
            task.execute(null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

