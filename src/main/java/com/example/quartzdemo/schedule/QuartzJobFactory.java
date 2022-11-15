package com.example.quartzdemo.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


public abstract class QuartzJobFactory extends QuartzJobBean {

	protected ApplicationContext applicationContext;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		try {
			this.executeInternal(context, "TEST");
		} catch (Exception e) {
			System.out.println("Fail");
		}
	}
	

	abstract protected void executeInternal(JobExecutionContext context, String log)
			throws Exception;

}
