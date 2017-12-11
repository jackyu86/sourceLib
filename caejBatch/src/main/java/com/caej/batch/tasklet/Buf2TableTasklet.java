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
public class Buf2TableTasklet implements Tasklet
{

    private String fromTable;
    private String toTable;

    public String getFromTable() {
        return fromTable;
    }

    public void setFromTable(String fromTable) {
        this.fromTable = fromTable;
    }

    public String getToTable() {
        return toTable;
    }

    public void setToTable(String toTable) {
        this.toTable = toTable;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception
    {
        jdbcTemplate.update("insert into t_account_confirm SELECT * from buf_account_confirm");
        return RepeatStatus.FINISHED;
    }




    public static void main(String[] args) {
        try {
            Buf2TableTasklet task =  new Buf2TableTasklet();
            task.execute(null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

