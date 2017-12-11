/**
 * 
 */
package com.batch.sample.batchSample001;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerBean;
/**
 * @author kunrey
 * 
 */
public class Parallel {

	public static final String RUN_MONTH_KEY = "run.month";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
	 Logger logger = LoggerFactory.getLogger(Parallel.class);
	 logger.info("aaaaaaaaaaaaaaa");
		
		System.setProperty("ENVIRONMENT", "mysql");
		
		ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("parallel.xml");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SimpleJobLauncher launcher = new SimpleJobLauncher();

		launcher.setJobRepository((JobRepository) c.getBean("jobRepository"));

		launcher.setTaskExecutor(new SyncTaskExecutor());

		try {
			Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
			parameters.put(RUN_MONTH_KEY, new JobParameter("2011-2"));

			Long startTime=System.currentTimeMillis();

			JobExecution je = launcher.run((Job) c.getBean("billingJob"),
					new JobParameters(parameters));
			System.out.println("使用时间："+(System.currentTimeMillis()-startTime));

		//	System.out.println(je);
			//System.out.println(je.getJobInstance());
			//System.out.println(je.getStepExecutions());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
