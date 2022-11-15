package com.example.quartzdemo.schedule;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.example.quartzdemo.services.ScheduleJobService;

@Configuration
public class QuartzListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired ScheduleJobService scheduleJobService;

    // 初始啟動quartz
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
        	scheduleJobService.startJob();
            System.out.println("job start");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
