package com.caej.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SyncLauncher {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "batch.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("caejWallet3Job");

        try {
            // JOB实行
            JobExecution result = launcher.run(
                    job,
                    new JobParametersBuilder()
                            .addString("02FilePath",
                                    "C:\\temp\\fundfile\\2PA\\Confirm\\OFD_PA_2PA_20171115_02.TXT")
                            .addString("04FilePath",
                                    "C:\\temp\\fundfile\\2PA\\Confirm\\OFD_PA_2PA_20171115_04.TXT")
                            .addString("05FilePath",
                                    "C:\\temp\\fundfile\\2PA\\Confirm\\OFD_PA_2PA_20171115_05.TXT")
                            .addString("06FilePath",
                                    "C:\\temp\\fundfile\\2PA\\Confirm\\OFD_PA_2PA_20171115_06.TXT")
                            .addString("07FilePath",
                                    "C:\\temp\\fundfile\\2PA\\FundDay\\OFD_PA_2PA_20171115_07.TXT")
                            .addString("outFilePath",
                                    "C:\\temp\\testData")
                            .addString("accountConfirmFilePath",
                                    "C:\\temp\\testData\\02.txt")
                            .addString("transactionConfirmFilePath",
                                    "C:\\temp\\testData\\04.txt")
                            .addString("abonusFilePath",
                                    "C:\\temp\\testData\\06.txt")
                            .addString("accountReconFilePath",
                                    "C:\\temp\\testData\\05.txt")
                            .addString("fundInfoFilePath",
                                    "C:\\temp\\testData\\07.txt")
                            .toJobParameters());
            // 运行结果输出
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}