package com.example.quartzdemo.schedule;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.quartz.JobExecutionContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.example.quartzdemo.models.SysScheduleJob;


public class CreateFileSchedule extends QuartzJobFactory {
	@Override
	protected void executeInternal(JobExecutionContext context, String log) {
		// 這段一定要加，不然autowierd不到
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		// 任務執行內容
		System.out.println("Executor: " + context.getJobDetail().getJobDataMap().get(SysScheduleJob.SYS_SCHEDULE_JOB_EXECUTOR));
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss");
		String formattedDateTime = currentTime.format(formatter);
		try {
			Thread.sleep(1000);
			File file = new File("D:/test/" + formattedDateTime + ".txt");
	        file.createNewFile();
		} catch (Exception e) {
			System.out.println("Fail");
		}
	}
}
