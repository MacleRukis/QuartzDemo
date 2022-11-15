package com.example.quartzdemo.services;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quartzdemo.models.SysScheduleJob;
import com.example.quartzdemo.schedule.CreateDirSchedule;
import com.example.quartzdemo.schedule.CreateFileSchedule;

@Service
public class ScheduleJobService {

    @Autowired(required = false) private Scheduler scheduler;
    
    // 手動執行
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void executeJob(String jobName) {
		try {
			// 用來識別Trigger
	        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, "manual");
			// 用來識別Job
	        JobKey jobKey = JobKey.jobKey(jobName, "manual");
	        
	        // 透過JobKey從調度器中取得符合Job
	        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
	        
	        // 若Job存在，將Job從調度器中刪除
	        if (jobDetail != null) {
	            scheduler.deleteJob(jobKey);
	        }

	        
            // 可將數據帶入Job中
	        JobDataMap data = new JobDataMap();
	        // data.put(key, value)
	        data.put(SysScheduleJob.SYS_SCHEDULE_JOB_EXECUTOR, "Employee A");

	        // 透過字串獲得class
	        Class<Job> clazz = (Class<Job>) Class.forName("com.example.quartzdemo.schedule." + jobName);
	        // 建立Job並且綁定JobKey
	        jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).usingJobData(data).storeDurably().build();
	        
	        // 將Job加入調度器中
	        scheduler.addJob(jobDetail, true);

	        // 定義調度器觸發規則
	        ScheduleBuilder builder = null;
	        // 共有四種 SimpleTrigger / DailyTimeIntervalTrigger / CalendarIntervalTrigger / CronTrigger
	        if (Boolean.TRUE.equals(true)) {
	        	// 觸發後，根據給定的次數或頻率觸發
	        	builder = SimpleScheduleBuilder.simpleSchedule()
		        //.withIntervalInSeconds(0) // 頻率
		        .withRepeatCount(0) // 次數
		        //.withMisfireHandlingInstructionNowWithExistingCount(); // 理論時間點未執行，任務新增/重啟後，馬上執行未執行的任務
		        .withMisfireHandlingInstructionNextWithRemainingCount(); // 錯過後不處理
	        } else {
	        	// 根據設定的時間，固定觸發
	        	builder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
	        			.withIntervalInSeconds(10) // 頻率
	        			.withMisfireHandlingInstructionDoNothing(); // 等待下次觸發時間點到達後照頻率執行
	        }

	        // 建立Trigger並綁定TriggerKey/Job，並將觸發規則帶入
	        // 可加入startAt(Date) 或 endAt(Date)定義啟動時間及失效時間
	        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(builder).forJob(jobDetail).build();


	        // 調度器中存在Trigger，更新trigger。反之新增
            if (scheduler.getTrigger(triggerKey) != null) {
                scheduler.rescheduleJob(triggerKey, trigger);
            } else {
                scheduler.scheduleJob(trigger);
            }
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
    
    // 排程任務
	public void startJob() throws SchedulerException {
		// 掛上所有任務至調度器
        startJob1(scheduler);
        startJob2(scheduler);
        // 啟動調度器中所有Job
        scheduler.start();
	}

    private void startJob1(Scheduler scheduler) throws SchedulerException {
        // 透過class建立Job
        JobDetail jobDetail = JobBuilder.newJob(CreateDirSchedule.class)
        		.withIdentity("CreateDirSchedule", "batch")
        		.usingJobData(SysScheduleJob.SYS_SCHEDULE_JOB_EXECUTOR, "System")
        		.build();
        // 使用Cron表達式建立Trigger (秒 分 時 日 月 周 [年])
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?").withMisfireHandlingInstructionDoNothing();
        // 建立Trigger並綁定TriggerKey/Job，並將觸發規則帶入
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CreateDirSchedule", "batch").withSchedule(builder).build();
        // 註冊至調度器
        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void startJob2(Scheduler scheduler) throws SchedulerException {
        // 透過class建立Job
        JobDetail jobDetail = JobBuilder.newJob(CreateFileSchedule.class)
        		.withIdentity("CreateFileSchedule", "batch")
        		.usingJobData(SysScheduleJob.SYS_SCHEDULE_JOB_EXECUTOR, "System")
        		.build();
        // 使用Cron表達式建立Trigger (秒 分 時 日 月 周 [年])
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?").withMisfireHandlingInstructionDoNothing();
        // 建立Trigger並綁定TriggerKey/Job，並將觸發規則帶入
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CreateFileSchedule", "batch").withSchedule(builder).build();
        // 註冊至調度器
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void pauseJob(String jobName) throws SchedulerException {
    	JobKey jobKey = JobKey.jobKey(jobName, "batch");
    	System.out.println("pause job [" + jobName + "]");
    	scheduler.pauseJob(jobKey);
    }

    public void resumeJob(String jobName) throws SchedulerException {
    	JobKey jobKey = JobKey.jobKey(jobName, "batch");
    	System.out.println("resume job [" + jobName + "]");
    	scheduler.resumeJob(jobKey);
    }
    
    public void pauseJob() throws SchedulerException {
    	System.out.println("pauseAll");
    	scheduler.pauseAll();
    }

    public void resumeJob() throws SchedulerException {
    	System.out.println("resumeAll");
    	scheduler.resumeAll();
    }
}
